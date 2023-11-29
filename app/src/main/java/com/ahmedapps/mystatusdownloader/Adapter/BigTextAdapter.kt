package com.ahmedapps.mystatusdownloader.Adapter

import com.ahmedapps.mystatusdownloader.R
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BigTextAdapter(
    private var activity: Activity,
    private val items: List<List<String>>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   lateinit var text: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_big_text, parent, false)

        return TextViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TextViewHolder
        holder.bind(position)
    }

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val textView = itemView.findViewById<TextView>(R.id.text)

            textView.text = buildStyledText(text, items[position])

            val clipboard = activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            itemView.findViewById<ImageView>(R.id.copy).setOnClickListener { v: View? ->
                val clip = ClipData.newPlainText("Copied Text", textView.text)
                clipboard.setPrimaryClip(clip)
            }

        }
    }

    fun buildStyledText(input: String, symbols: List<String>): String {
        val text = input.lowercase()
        val alphabet = 'a'..'z'
        val symbolMap = alphabet.zip(symbols).toMap()

        val convertedText = StringBuilder()
        for (char in text) {
            val symbol = symbolMap[char]
            convertedText.append(symbol ?: char)
        }
        return convertedText.toString()
    }

}











