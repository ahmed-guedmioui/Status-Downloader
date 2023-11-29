package com.ahmedapps.mystatusdownloader.ui;

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ahmedapps.mystatusdownloader.databinding.ActivitySplashBinding
import com.ahmedapps.mystatusdownloader.utils.Test

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        val view: View = splashBinding.root
        setContentView(view)


//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 2000)

        Test().main()

    }

}




























