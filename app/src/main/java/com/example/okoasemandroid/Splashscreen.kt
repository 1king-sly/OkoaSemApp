package com.example.okoasemandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class Splashscreen : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        },2000)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null){
            startActivity(Intent(this,SignInActivity::class.java))
        }
        else{
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}