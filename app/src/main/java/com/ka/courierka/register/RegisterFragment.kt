package com.ka.courierka.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.helper.isCorrectDestinationNow
import com.ka.courierka.order.NewOrderFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextCity: EditText
    private lateinit var courier: Spinner
    private lateinit var buttonSignUp: Button
    private var requiredDestinationId = 0
    private lateinit var viewModel: RegistrationViewModel
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        requiredDestinationId = R.id.registerFragment
        // Inflate the layout for this fragment
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        InitViews(view)
    }

    private fun InitViews(view: View) {
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextName = view.findViewById(R.id.editTextName)
        editTextLastName = view.findViewById(R.id.editTextLastName)
        editTextAge = view.findViewById(R.id.editTextAge)
        editTextCity  = view.findViewById(R.id.editTextCity)
        courier  = view.findViewById(R.id.spinner)
        buttonSignUp = view.findViewById(R.id.buttonSignUp)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonSignUp.setOnClickListener {
            var email = getTrimmedValue(editTextEmail)
            var password = getTrimmedValue(editTextPassword)
            var name = getTrimmedValue(editTextName)
            var lastName = getTrimmedValue(editTextLastName)
            var age = getTrimmedValue(editTextAge).toInt()
            var city = getTrimmedValue(editTextCity)
            var typeCourier = false
            if (courier.selectedItemPosition!=0){typeCourier=true}
            viewModel.signUp(email, password, name, lastName, age, city, typeCourier)
            observeViewModel()
        }

    }

    private fun getTrimmedValue(editText: EditText): String {

        return editText.text.toString().trim()
    }

    private fun goToLogin() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = NewOrderFragment.newInstance(getTrimmedValue(editTextEmail),getTrimmedValue(editTextPassword))
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment, args.arguments)
        }
    }

    private fun goToUser() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            findNavController().navigate(R.id.action_registerFragment_to_userFragment)
        }
    }

    fun observeViewModel() {
        viewModel.getError().observe(viewLifecycleOwner) {

            if (it != null) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getUser().observe(viewLifecycleOwner) {
            if (it != null) {
                goToLogin()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}