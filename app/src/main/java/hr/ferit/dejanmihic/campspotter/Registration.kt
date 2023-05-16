package hr.ferit.dejanmihic.campspotter

import android.app.Activity
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

class Registration : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail : TextInputEditText
    private lateinit var editTextPassword : TextInputEditText
    private lateinit var buttonReg : Button
    private lateinit var progressBar: ProgressBar
    private lateinit var logIn : TextView
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
        setContentView(R.layout.activity_registration)
        auth = Firebase.auth
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_continue)
        progressBar = findViewById(R.id.progressBar)
        logIn = findViewById(R.id.textView2)

        logIn.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        buttonReg.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            var email: String = editTextEmail.text.toString()
            var password: String = editTextPassword.text.toString()
            println("email= $email")
            println("password= $password")
            if(email.isEmpty()){
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Account created",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Registration failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }
}