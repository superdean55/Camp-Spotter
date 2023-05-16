package hr.ferit.dejanmihic.campspotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var logOut: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var userEmail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userEmail = findViewById(R.id.textViewAuth)

        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        if (user == null) {
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }else{
            userEmail.text = Firebase.auth.currentUser?.email
        }
        logOut = findViewById(R.id.btnLogOut)
        logOut.setOnClickListener{
            Firebase.auth.signOut()
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}