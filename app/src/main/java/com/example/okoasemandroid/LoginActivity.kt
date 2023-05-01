package com.example.okoasemandroid

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.okoasemandroid.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var progressDialog:ProgressDialog
    lateinit var auth: FirebaseAuth
     lateinit var binding:ActivityLoginBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



       binding.btnLogin.setOnClickListener {
            val emailText = binding.tieEmail.text.toString().trim()
            val passwordText = binding.TiePass.text.toString().trim()

            progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Please wait")
            progressDialog.setCanceledOnTouchOutside(false)

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                loginUser(emailText, passwordText)
            } else {
                Toast.makeText(this, "Please fill in the credentials", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvClickSignin.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }


    }

    private fun loginUser(email: String, password: String) {
        progressDialog.setMessage("Logging in user...")
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)

            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"Logging in Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
}