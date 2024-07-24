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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.helper.isCorrectDestinationNow

import com.ka.courierka.login.LoginFragment

import com.ka.courierka.order.neworder.NewOrderFragment

private const val EMAIL = "email"
private const val PASS = "pass"

class RegisterFragment : Fragment() {

    private var requiredDestinationId = 0
    private lateinit var viewModel: RegistrationViewModel
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
    ): View {
        requiredDestinationId = R.id.registerFragment
        val view = ComposeView(requireContext()).apply {
            setContent {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val name = remember { mutableStateOf("") }
                val lastName = remember { mutableStateOf("") }
                val age = remember { mutableStateOf(0) }
                val city = remember { mutableStateOf("") }
                val typeCourier = remember { mutableStateOf(false) }
                var expanded = remember { mutableStateOf(false) }
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
                    }, placeholder = { Text("Email") }, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                    )
                    TextField(value = password.value, onValueChange = { pas ->
                        password.value = pas
                        pass = password.value
                    }, placeholder = { Text("Password") }, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                    )
                    TextField(
                        value = name.value,
                        onValueChange = { nam ->
                            name.value = nam
//                        pass = name.value
                        },
                        placeholder = { Text(stringResource(id = R.string.name)) },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = lastName.value,
                        onValueChange = { pas ->
                            lastName.value = pas
//                        pass = lastName.value
                        },
                        placeholder = { Text(stringResource(id = R.string.last_name)) },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = age.value.toString(),
                        onValueChange = { pas ->
                            age.value = pas.toInt()
//                        pass = age.value
                        },
                        placeholder = { Text(stringResource(id = R.string.age)) },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = city.value,
                        onValueChange = { pas ->
                            city.value = pas
                            pass = city.value
                        },
                        placeholder = { Text(stringResource(id = R.string.city)) },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                    Box {
                        IconButton(onClick = { expanded.value = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
                        }
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            DropdownMenuItem(
                                onClick = { typeCourier.value = false },
                                text = { Text(stringResource(id = R.string.courier)) }
                            )
                            DropdownMenuItem(
                                onClick = { typeCourier.value = true },
                                text = { Text(stringResource(id = R.string.customer)) }
                            )
                        }
                    }
                    Button(
                        onClick = {
                            viewModel.signUp(
                                email.value,
                                password.value,
                                name.value,
                                lastName.value,
                                age.value,
                                city.value,
                                typeCourier.value
                            )
                            goToLogin(email.value, password.value)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) { Text(stringResource(id = R.string.sign_up), fontSize = 28.sp) }


                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        observeViewModel()
    }

    private fun goToLogin(email: String, password: String) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = LoginFragment.newInstance(
                email,
                password
            )
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment,
                args.arguments
            )
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
                goToLogin(email1, pass)
            }
        }
    }
}