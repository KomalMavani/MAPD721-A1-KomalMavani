package com.example.mapd721_a1_komalmavani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapd721_a1_komalmavani.ui.theme.MAPD721A1KomalMavaniTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MAPD721A1KomalMavaniTheme {
                MyApp()
            }
        }
    }
}

@Preview
@Composable
fun MyApp() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val store = StudentStore(context)

    var idValue by remember { mutableStateOf(TextFieldValue("922")) }
    var studentNameValue by remember { mutableStateOf(TextFieldValue()) }
    var courseNameValue by remember { mutableStateOf(TextFieldValue()) }

    val storedId = store.getID.collectAsState(initial = "")
    val storedStudentname = store.getStudentName.collectAsState(initial = "")
    val storedCourseName = store.getCourseName.collectAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = idValue,
            onValueChange = { idValue = it },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = studentNameValue,
            onValueChange = { studentNameValue = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = courseNameValue,
            onValueChange = { courseNameValue = it },
            label = { Text("Course Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    idValue = TextFieldValue(storedId.value)
                    studentNameValue = TextFieldValue(storedStudentname.value)
                    courseNameValue = TextFieldValue(storedCourseName.value)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(255, 255, 204), // Light Yellow Background
                    contentColor = Color(204, 153, 0), // Dark Yellow Text
                ),
                border = BorderStroke(1.dp, Color(204, 153, 0)) // Dark Yellow Border
            ) {
                Text("Load")
            }
            OutlinedButton(
                onClick = {
                    scope.launch {
                        store.saveStudentDetails(idValue.text, studentNameValue.text, courseNameValue.text)
                    }
                    keyboardController?.hide()
                },
                modifier = Modifier
                        .weight(1f)
                    .padding(start = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(76, 175, 80), // Solid Green
                    contentColor = Color.White, // White text
                ),
                border = BorderStroke(1.dp, Color(56, 142, 60)) // Darker Green Border
            ) {
                Text("Store")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                idValue = TextFieldValue("")
                studentNameValue = TextFieldValue("")
                courseNameValue = TextFieldValue("")
                scope.launch {
                    store.saveStudentDetails("", "", "")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(229, 57, 53), // Solid Red
                contentColor = Color.White, // White text
            ),
            border = BorderStroke(1.dp, Color(200, 40, 40)) // Darker Red Border
        ) {
            Text("Reset")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Komal Mavani")
        Text("301472922")
    }
}