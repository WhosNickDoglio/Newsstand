package dev.whosnickdoglio.feedly.ui.login.new

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (false) { // is complete or error?
//            Snackbar()
        }

        if (false) { // isLoading
            CircularProgressIndicator()
        } else {
            Button(onClick = { }) {
                Text(text = "Login")
            }
        }
    }
}


@Preview
@Composable
private fun PreviewLoginContent() {
    LoginContent()
}


//internal val newLoginViewFactory = composeViewFactory()
