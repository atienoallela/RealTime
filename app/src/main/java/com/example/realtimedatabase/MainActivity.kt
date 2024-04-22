package com.example.realtimedatabase

//import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.realtimedatabase.ui.theme.RealTimeDatabaseTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealTimeDatabaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize() ,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(LocalContext.current)
                }
            }
        }
    }
}

//@SuppressLint("RememberReturnType")
@Composable
fun Greeting(context: Context) {
val appinformation = remember {
    mutableStateOf("")
}
    //instantiate db
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("Info")

    databaseReference.addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot : DataSnapshot){
            val value = snapshot.getValue(String::class.java)
            appinformation.value = value!!
    }

        override fun onCancelled(error : DatabaseError) {
            Toast.makeText(context,"Unable to retrieve data",Toast.LENGTH_LONG).show()
        }
})
            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Text(
                    text = appinformation.value,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Italic
                )
            }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RealTimeDatabaseTheme {
        Greeting(LocalContext.current)
    }
}