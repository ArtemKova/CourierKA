package com.ka.courierka.forgot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.tools.Constants.Companion.round
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes
import com.ka.courierka.tools.Constants.Companion.bigFontSize
import com.ka.courierka.tools.Constants.Companion.buttonFontSize


@Composable
internal fun ForgotScreen(
    navController: NavHostController,
    email:String?,
    viewModel: ResetPasswordViewModel = hiltViewModel()

) {
    val email1 = remember { mutableStateOf("") }
    if (email != null) {
        email1.value = email
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.forgot_password_title),
            fontSize = bigFontSize.sp,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        TextField(value = email1.value, onValueChange = { email2 ->
            email1.value = email2

        }, placeholder = { Text("Email") }, modifier = Modifier.padding(10.dp).fillMaxWidth())
        Button(
            onClick = {
                viewModel.ResetPassword(email1.value)
                navController.navigate(route = Routes.Login.routes)
                      },
            shape = RoundedCornerShape(round.dp),
            modifier = Modifier.padding(padding.dp).fillMaxWidth()
        ) { Text(stringResource(id = R.string.reset_password), fontSize = buttonFontSize.sp) }
    }
}