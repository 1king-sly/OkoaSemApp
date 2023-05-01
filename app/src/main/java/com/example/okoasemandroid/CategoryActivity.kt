package com.example.okoasemandroid

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.okoasemandroid.databinding.ActivityCategoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityCategoryBinding

private lateinit var auth: FirebaseAuth

private lateinit var progressDialog: ProgressDialog

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            var textinput = binding.categoryTie


           validateData()
            textinput.text?.clear()
        }
    }

    var input = ""

    private fun validateData() {
        input = binding.categoryTie.text.toString().trim()
        if (input.isEmpty()) {
            Toast.makeText(this, "Please add a category", Toast.LENGTH_SHORT).show()

        }
        else{
            addCategory()
        }
    }

    private fun addCategory() {
        val timestamp = System.currentTimeMillis()
        val uid = auth.uid

        val hashMap:HashMap<String,Any?> = HashMap()
        hashMap["timestamp"] =" $timestamp"
        hashMap["uid"] = uid
        hashMap["category"] = input

        progressDialog.setMessage("Adding Category")
        progressDialog.show()

        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.child("$timestamp").setValue(hashMap).addOnFailureListener {e->
            progressDialog.dismiss()
            Toast.makeText(this, "Failed adding category due to ${e.message}", Toast.LENGTH_SHORT).show()

        }
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show()
            }


    }
}