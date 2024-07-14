package com.ka.courierka.courier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.helper.isCorrectDestinationNow

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatusUser.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatusUser : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editTextName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextCity: EditText
    private lateinit var courier: Spinner
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private var requiredDestinationId = 0
    private lateinit var viewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
    }

    private fun getTrimmedValue(editText: EditText): String {
        return editText.text.toString().trim()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
        buttonSave.setOnClickListener {
            var name = getTrimmedValue(editTextName)
            var lastName = getTrimmedValue(editTextLastName)
            var age = getTrimmedValue(editTextAge).toInt()
            var city = getTrimmedValue(editTextCity)
            var typeCourier = false
            if (courier.selectedItemPosition != 0) {
                typeCourier = true
            }
            param1?.let { it1 ->
                viewModel.setChangeUserData(
                    it1,
                    name,
                    lastName,
                    age,
                    city,
                    typeCourier
                )
                goToUser(it1)
            }


        }
        buttonCancel.setOnClickListener {
            param1?.let { it1 -> goToUser(it1) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_status_user, container, false)
        requiredDestinationId = R.id.statusUser

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitViews(view)
    }

    fun observeViewModel() {
        viewModel.getCourier().observe(viewLifecycleOwner) {
            editTextName.setText(it[0].name)
            editTextLastName.setText(it[0].lastName)
            editTextAge.setText((it[0].age).toString())
            editTextCity.setText(it[0].city)
            if (it[0].courier) {
                courier.setSelection(1)
            } else {
                courier.setSelection(0)

            }


        }
    }

    private fun goToUser(id: String) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id)
            findNavController().navigate(R.id.action_statusUser_to_userFragment, args.arguments)
        }

    }

    private fun InitViews(view: View) {
        editTextName = view.findViewById(R.id.editTextNameInUser)
        editTextLastName = view.findViewById(R.id.editTextLastNameInUser)
        editTextAge = view.findViewById(R.id.editTextAgeInUser)
        editTextCity = view.findViewById(R.id.editTextCityInUser)
        courier = view.findViewById(R.id.spinnerChangeTypeUser)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonCancel = view.findViewById(R.id.buttonCancel)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatusUser.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatusUser().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}