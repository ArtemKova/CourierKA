package com.ka.courierka.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.courier.UserFragment
import com.ka.courierka.forgot.ForgotenPassFragment
import com.ka.courierka.helper.isCorrectDestinationNow

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var requiredDestinationId = 0
    private lateinit var textViewTitle: TextView
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var textViewRegister: TextView
    private lateinit var viewModel:LoginViewModel


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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        // Inflate the layout for this fragment
        requiredDestinationId = R.id.loginFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        InitViews(view)
    }

    private fun InitViews(view: View) {
        textViewTitle = view.findViewById(R.id.textViewTitle)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        textViewForgotPassword = view.findViewById(R.id.textViewForgotPassword)
        textViewRegister = view.findViewById(R.id.textViewRegister)
        if (param1!=null){editTextEmail.setText(param1)}
        if (param2!=null){editTextPassword.setText(param2)}

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        setupClickListners()


    }

    private fun setupClickListners(){
        buttonLogin.setOnClickListener {
            var email = editTextEmail.text.toString().trim()
            var password = editTextPassword.text.toString().trim()
            viewModel.login(email,password)

        }
        textViewForgotPassword.setOnClickListener {
            goToForgotPass()
        }
        textViewRegister.setOnClickListener {
            goToRegister()
        }
    }
    private fun goToRegister() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun goToForgotPass() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = ForgotenPassFragment.newInstance(editTextEmail.text.toString(),"")
            findNavController().navigate(R.id.action_loginFragment_to_forgotenPassFragment, args.arguments)
        }
    }
    private fun goToUser(id:String) {
        Log.d("iduid2","${id}  " )
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id,"")
            findNavController().navigate(R.id.action_loginFragment_to_userFragment,args.arguments)
        }

    }
    fun observeViewModel(){
        viewModel.getError().observe(viewLifecycleOwner){

            if (it!=null) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getUser().observe(viewLifecycleOwner){
            if (it!=null) {
                goToUser(it.uid)
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}