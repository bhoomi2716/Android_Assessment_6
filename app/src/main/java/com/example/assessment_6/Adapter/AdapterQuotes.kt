package com.example.assessment_6.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment_6.Model.ModelQuotes
import com.example.assessment_6.databinding.DesignQuotesBinding

class AdapterQuotes(
    private var quotes: MutableList<ModelQuotes>,
    private val onLike: (ModelQuotes) -> Unit,
    private val onShare: (ModelQuotes) -> Unit,
    private val onCopy : (ModelQuotes) -> Unit,
    private val onDownload: (ModelQuotes, View) -> Unit) : RecyclerView.Adapter<AdapterQuotes.QuoteViewHolder>() {

    inner class QuoteViewHolder(val binding: DesignQuotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(DesignQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.binding.tvQuoteDesc.text = quote.text
        holder.binding.lytLike.setOnClickListener { onLike(quote) }
        holder.binding.lytShare.setOnClickListener { onShare(quote) }
        holder.binding.lytCopy.setOnClickListener { onCopy(quote) }
        holder.binding.lytSave.setOnClickListener { onDownload(quote, holder.binding.root) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ModelQuotes>) {
        quotes.clear()
        quotes.addAll(newList)
        notifyDataSetChanged()
    }
}