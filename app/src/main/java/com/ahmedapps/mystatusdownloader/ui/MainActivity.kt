package com.ahmedapps.mystatusdownloader.ui;


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahmedapps.mystatusdownloader.databinding.ActivityMainBinding
import com.ahmedapps.mystatusdownloader.ui.sticker.StickerActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val REQUEST_PERMISSIONS = 1234
    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data!!
            Log.d("HEY: ", data.data.toString())
            this.contentResolver.takePersistableUriPermission(
                data.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)


        onClickButton()

    }

    private fun onClickButton() {
        binding.downloadStatue.setOnClickListener {
            if (!arePermissionDenied()) {
                next()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    requestPermissionQ()
                }
                requestPermissions(
                    PERMISSIONS,
                    REQUEST_PERMISSIONS
                )
            }
        }

        binding.stickers.setOnClickListener {
            startActivity(Intent(this, StickerActivity::class.java))
        }

        binding.fancyText.setOnClickListener {
            startActivity(Intent(this, FancyTextActivity::class.java))
        }

        binding.bigText.setOnClickListener {
            startActivity(Intent(this, BigTextActivity::class.java))
        }

        binding.sendMsg.setOnClickListener {
            startActivity(Intent(this, DirectMessageActivity::class.java))
        }

        binding.repeatText.setOnClickListener {
            startActivity(Intent(this, TextRepeaterActivity::class.java))
        }

        binding.scan.setOnClickListener {
            cameraPermission()
        }

    }

    private fun cameraPermission() {

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission.launch(Manifest.permission.CAMERA)
        } else {
            startActivity(Intent(this@MainActivity, ScanQRActivity::class.java))
        }

    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            startActivity(Intent(this@MainActivity, ScanQRActivity::class.java))
        }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private fun requestPermissionQ() {
        val sm = this.getSystemService(STORAGE_SERVICE) as StorageManager
        val intent = sm.primaryStorageVolume.createOpenDocumentTreeIntent()
        val startDir = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
        var uri = intent.getParcelableExtra<Uri>("android.provider.extra.INITIAL_URI")
        var scheme = uri.toString()
        scheme = scheme.replace("/root/", "/document/")
        scheme += "%3A$startDir"
        uri = Uri.parse(scheme)
        Log.d("URI", uri.toString())
        intent.putExtra("android.provider.extra.INITIAL_URI", uri)
        intent.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        activityResultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS && grantResults.isNotEmpty()) {
            if (arePermissionDenied()) {
                Toast.makeText(
                    this@MainActivity,
                    "You need to provide permission to download whatsapp status.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                next()
            }
        }
    }

    private fun next() {

        startActivity(Intent(this, WhatsappActivity::class.java))
    }

    private fun arePermissionDenied(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return contentResolver.persistedUriPermissions.size <= 0
        }
        for (permissions in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    permissions
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
        return false

    }


}








