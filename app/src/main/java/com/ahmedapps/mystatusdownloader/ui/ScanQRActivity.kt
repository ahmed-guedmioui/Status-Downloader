package com.ahmedapps.mystatusdownloader.ui

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.media.ToneGenerator
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahmedapps.mystatusdownloader.R
import com.ahmedapps.mystatusdownloader.databinding.ActivityQrScannerBinding
import com.ahmedapps.mystatusdownloader.utils.qrcode_reader.ZXingScannerView
import com.ahmedapps.mystatusdownloader.utils.qrcode_reader.ZXingScannerView.ResultHandler
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import org.bouncycastle.i18n.TextBundle

class ScanQRActivity : AppCompatActivity(), ResultHandler {


    private lateinit var binding: ActivityQrScannerBinding

    private var barcode_result: String = ""
    private var camera_id = -1
    private var selected_indices: ArrayList<Int>? = null
    private lateinit var viewGroup: ViewGroup
    private var zXingScannerView: ZXingScannerView? = null

    public override fun onCreate(bundle: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(bundle)

        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        init()
    }

    private fun init() {
        viewGroup = binding.flCamera
        zXingScannerView = ZXingScannerView(this)
        viewGroup.addView(zXingScannerView)
        findViewById<View>(R.id.iv_back).setOnClickListener { onBackPressed() }
    }

    private fun setupBarcodeFormats() {
        val arrayList: ArrayList<BarcodeFormat> = ArrayList()
        if (selected_indices == null || selected_indices!!.isEmpty()) {
            selected_indices = ArrayList()
            for (i in ZXingScannerView.ALL_FORMATS.indices) {
                selected_indices!!.add(Integer.valueOf(i))
            }
        }
        val it: Iterator<Int> = selected_indices!!.iterator()
        while (it.hasNext()) {
            arrayList.add(ZXingScannerView.ALL_FORMATS[it.next()])
        }
        if (zXingScannerView != null) {
            zXingScannerView!!.setFormats(arrayList)
        }
    }

    override fun handleResult(result: Result) {
        barcode_result = result.text

        Log.e(TAG, result.text)
        Log.e(TAG, result.barcodeFormat.toString())

        ToneGenerator(5, 100).startTone(24)

        val dialog = Dialog(this, R.style.ThemeWithRoundShape)
        dialog.requestWindowFeature(1)
        dialog.setContentView(R.layout.dialog_barcode_result)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        dialog.window!!.setLayout(-1, -2)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        val tv_search = dialog.findViewById<TextView>(R.id.tv_search)
        val tv_result = dialog.findViewById<TextView>(R.id.tv_result)

        if (barcode_result.startsWith("tel")) {
            tv_search.text = "Call"
        }

        tv_result.text = barcode_result

        (dialog.findViewById<View>(R.id.tv_share) as TextView).setOnClickListener {
            (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText(TextBundle.TEXT_ENTRY, barcode_result)
            )

            val intent = Intent("android.intent.action.SEND")
            intent.type = "text/*"
            intent.putExtra("android.intent.extra.SUBJECT", "")
            intent.putExtra("android.intent.extra.TEXT", barcode_result)
            startActivity(Intent.createChooser(intent, "Share text using"))
            dialog.dismiss()
        }

        (dialog.findViewById<View>(R.id.tv_search) as TextView).setOnClickListener {
            val intent: Intent
            if (barcode_result.startsWith("tel")) {
                intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(barcode_result)
                startActivity(intent)
            } else {
                open(barcode_result)
            }
            dialog.dismiss()
        }
        (dialog.findViewById<View>(R.id.iv_close) as ImageView).setOnClickListener {
            (getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText(TextBundle.TEXT_ENTRY, barcode_result)
            )
            Toast.makeText(this@ScanQRActivity, "Text copied to clipboard", Toast.LENGTH_SHORT)
                .show()
            if (zXingScannerView == null) {
                zXingScannerView = ZXingScannerView(this@ScanQRActivity)
                viewGroup!!.addView(zXingScannerView)
            }
            zXingScannerView!!.setResultHandler(this@ScanQRActivity)
            zXingScannerView!!.startCamera(camera_id)
            setupBarcodeFormats()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun open(url: String?) {
        if (url!!.contains("https://")) {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
        } else {
            Toast.makeText(
                this, "This Link Can't be opened", Toast.LENGTH_SHORT
            ).show()
        }
    }

    public override fun onResume() {
        zXingScannerView!!.setResultHandler(this)
        zXingScannerView!!.startCamera(camera_id)
        setupBarcodeFormats()
        super.onResume()
    }

    public override fun onDestroy() {
        zXingScannerView!!.stopCamera()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "QRReaderActivity"
    }
}