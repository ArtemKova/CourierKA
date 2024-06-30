package com.ka.courierka.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.ka.courierka.R

private const val EMAIL = "email"

class ForgotenPassFragment : Fragment() {
    private var email: String? = null
    private lateinit var viewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(EMAIL).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {


            setContent {
                val email1 = remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.forgot_password_title),
                        fontSize = 40.sp,
                        color = colorResource(id = R.color.purple_500),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    TextField(value = email1.value, onValueChange = { email2 ->
                        email1.value = email2
                        email = email1.value
                    }, placeholder = { Text("Email") }, modifier = Modifier.padding(10.dp).fillMaxWidth())
                    Button(
                        onClick = { viewModel.ResetPassword(email1.value) },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(10.dp).fillMaxWidth()
                    ) { Text(stringResource(id = R.string.reset_password), fontSize = 28.sp) }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.getError().observe(viewLifecycleOwner) {

            if (it != null) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.isSuccess().observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(activity, getString(R.string.reset_link), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        fun newInstance(email: String) =
            ForgotenPassFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email)

                }
            }
    }
}