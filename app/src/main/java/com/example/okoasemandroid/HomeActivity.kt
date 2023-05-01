package com.example.okoasemandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.okoasemandroid.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var database:FirebaseDatabase
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        binding.userEmailTv.text = auth.currentUser?.email.toString()
        binding.IvBack.setOnClickListener {
            onBackPressed()
        }
        binding.IvLogout.setOnClickListener {
           auth.signOut()
        }

        binding.btnAddCategory.setOnClickListener {
            startActivity(Intent(this,CategoryActivity::class.java))
        }

    }
}