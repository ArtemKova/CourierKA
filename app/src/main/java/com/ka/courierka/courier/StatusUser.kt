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

private const val ID = "id"

class StatusUser : Fragment() {
    private var id: String? = null
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
            id = it.getString(ID)
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
            id?.let { it1 ->
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
            id?.let { it1 -> goToUser(it1) }
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

        @JvmStatic
        fun newInstance(id: String) =
            StatusUser().apply {
                arguments = Bundle().apply {
                    putString(ID, id)

                }
            }
    }
}