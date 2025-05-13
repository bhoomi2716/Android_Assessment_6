package com.example.assessment_6.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment_6.Adapter.AdapterQuotes
import com.example.assessment_6.Model.ModelQuotes
import com.example.assessment_6.databinding.ActivityQuotesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.File
import java.io.FileOutputStream

class QuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuotesBinding
    private lateinit var adapter: AdapterQuotes
    private lateinit var database: DatabaseReference
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category") ?: ""
        database = FirebaseDatabase.getInstance().getReference("quotes/$category")
        
        binding.toolbarQuotes.setTitle(category)
        setSupportActionBar(binding.toolbarQuotes)

        adapter = AdapterQuotes(mutableListOf(),
            onLike = {
                Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show()
            },
            onShare = { quote ->
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, quote.text)
                startActivity(Intent.createChooser(intent, "Share Quote"))
            },
            onCopy = {
                Toast.makeText(this, "Copy!", Toast.LENGTH_SHORT).show()
            },
            onDownload = { _, _ ->
                Toast.makeText(this, "Downloaded!", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewQuotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewQuotes.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ModelQuotes>()
                for (q in snapshot.children) {
                    q.getValue(ModelQuotes::class.java)?.let { list.add(it) }
                }
                adapter.updateList(list)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}