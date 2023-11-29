package com.ahmedapps.mystatusdownloader.ui;


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahmedapps.mystatusdownloader.databinding.ActivityDirectMessageBinding


class DirectMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDirectMessageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectMessageBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)




        onClickButton()
    }


    private fun onClickButton() {

        val phone = binding.numberEditText.text

        binding.btnSend.setOnClickListener {
            if (phone.isEmpty()) {

                Toast.makeText(
                    this@DirectMessageActivity,
                    "Please enter a phone number",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val code = binding.ccp.selectedCountryCode

                try {
                    val link = "https://wa.me/$code$phone/?text="
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
                } catch (e: Exception) {
                }


            }
        }
    }
}








