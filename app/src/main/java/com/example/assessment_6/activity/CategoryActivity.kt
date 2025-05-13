package com.example.assessment_6.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment_6.Adapter.AdapterCategory
import com.example.assessment_6.Model.ModelCategory
import com.example.assessment_6.R
import com.example.assessment_6.databinding.ActivityCategoryBinding
import com.google.firebase.database.*

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter: AdapterCategory
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCategory.setTitle("Best Quotes & Status")
        setSupportActionBar(binding.toolbarCategory)

        database = FirebaseDatabase.getInstance().getReference("categories")
        adapter = AdapterCategory(mutableListOf()) { category ->
            val intent = Intent(this, QuotesActivity::class.java)
                intent.putExtra("category", category.name)
            startActivity(intent)
        }

        binding.recyclerViewCategory.layoutManager = GridLayoutManager(this,2)
        binding.recyclerViewCategory.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ModelCategory>()
                for (cat in snapshot.children) {
                    cat.getValue(ModelCategory::class.java)?.let { list.add(it) }
                }
                adapter.updateList(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }


}
