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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.courier.UserFragment
import com.ka.courierka.forgot.ForgotenPassFragment
import com.ka.courierka.helper.isCorrectDestinationNow

private const val EMAIL = "email"
private const val PASS = "pass"

class LoginFragment : Fragment() {
    private var requiredDestinationId = 0
    private lateinit var viewModel: LoginViewModel
    private var email1 = ""
    private var pass = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email1 = it.getString(EMAIL).toString()
            pass = it.getString(PASS).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view = ComposeView(requireContext()).apply {
            setContent {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
//        val view = inflater.inflate(R.layout.fragment_login, container, false).apply {
//            findViewById<ComposeView>(R.id.composeView).setContent {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.courier),
                        fontSize = 40.sp,
                        color = colorResource(id = R.color.purple_500),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    TextField(value = email.value, onValueChange = { email2 ->
                        email.value = email2
                        email1 = email.value
                    }, placeholder = { Text("Email") }, modifier = Modifier.padding(10.dp).fillMaxWidth())
                    TextField(value = password.value, onValueChange = { pas ->
                        password.value = pas
                        pass = password.value
                    }, placeholder = { Text("Password") }, modifier = Modifier.padding(10.dp).fillMaxWidth())
                    Button(
                        onClick = { viewModel.login(email1, pass) },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(10.dp).fillMaxWidth()
                    ) { Text(stringResource(id = R.string.login), fontSize = 28.sp) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            modifier = Modifier
                                .clickable(onClick = { goToForgotPass() })
                                .padding(10.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.register),
                            modifier = Modifier
                                .clickable(onClick = { goToRegister() })
                                .padding(10.dp)
                        )


                    }
                }
            }
        }
        requiredDestinationId = R.id.loginFragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        observeViewModel()
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
            val args = ForgotenPassFragment.newInstance(email1)
            findNavController().navigate(
                R.id.action_loginFragment_to_forgotenPassFragment,
                args.arguments
            )
        }
    }

    private fun goToUser(id: String) {
        Log.d("iduid2", "${id}  ")
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id, "")
            findNavController().navigate(R.id.action_loginFragment_to_userFragment, args.arguments)
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
                goToUser(it.uid)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(email: String, pass: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email)
                    putString(PASS, pass)
                }
            }
    }
}