package com.example.okoasemandroid

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.okoasemandroid.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySigninBinding
    lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySigninBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        binding.tvClickLogin
            .setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnSignin
            .setOnClickListener {

                validateData()
            }


    }


    private var name = ""
    private var email = ""
    private var password = ""


    private fun validateData() {
        name = binding.etName.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()


        if (name.isEmpty()) {
            Toast.makeText(this, "Please provide user Name", Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please provide user Email", Toast.LENGTH_SHORT).show()

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address pattern", Toast.LENGTH_SHORT).show()

        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please provide Password", Toast.LENGTH_SHORT).show()
        } else {
            createUser()
        }
    }

    private fun createUser() {
        progressDialog.setMessage("Creating account....")
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                saveUserInfo()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Failed creating account due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveUserInfo() {
        progressDialog.setMessage("Saving user info....")
        val timestamp = System.currentTimeMillis()
        val uid = FirebaseAuth.getInstance().uid
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["userType"] = "User"
        hashMap["profileImage"] = ""
        hashMap["timeStamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("User")
        ref.child(uid!!).setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Creating account failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }


}