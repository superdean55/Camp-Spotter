package hr.ferit.dejanmihic.campspotter

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var editTextEmail : TextInputEditText
    private lateinit var editTextPassword : TextInputEditText
    private lateinit var buttonLog : Button
    private lateinit var progressBar: ProgressBar
    private lateinit var signUp : TextView
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLog = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progressBar)
        signUp = findViewById(R.id.signUp)

        signUp.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish()
        }

        buttonLog.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            var email: String = editTextEmail.text.toString()
            var password: String = editTextPassword.text.toString()
            if(email.isEmpty()){
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Login Successful",
                            Toast.LENGTH_SHORT,
                        ).show()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }
        }

    }
}