package com.ahmedapps.mystatusdownloader.ui;

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedapps.mystatusdownloader.Adapter.BigTextAdapter
import com.ahmedapps.mystatusdownloader.R
import com.ahmedapps.mystatusdownloader.databinding.ActivityFancyTextBinding
import com.ahmedapps.mystatusdownloader.utils.stylesBig

class BigTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFancyTextBinding

    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFancyTextBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.text.text = getString(R.string.big_text)
        binding.textInfo.text = getString(R.string.big_text_info)

       
        

        buildRecycleView()
    }

    private fun buildRecycleView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.editText.setText("Hi")

       val adapter = BigTextAdapter(this, stylesBig)
        adapter.text = binding.editText.text.toString()
        binding.recyclerView.adapter = adapter

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                adapter.text = s.toString()
                adapter.notifyDataSetChanged()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.text = s.toString()
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable) {
                adapter.text = s.toString()
                adapter.notifyDataSetChanged()
            }
        })


    }

   

    
}








