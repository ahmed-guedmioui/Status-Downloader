package com.ahmedapps.mystatusdownloader.tools

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.ahmedapps.mystatusdownloader.R

class InternetManager {
    companion object {

        fun isNetworkAvailable(context: Activity): Boolean {

            return true
        }

        fun showNoInternetDialog(context: Activity) {
            val alertDialog = AlertDialog.Builder(context)

            alertDialog.setTitle(R.string.no_internet_connection)
            alertDialog.setMessage(R.string.connect_msg)
            alertDialog.setIcon(R.drawable.ic_no_internet)
            alertDialog.setPositiveButton(android.R.string.ok, null)
            alertDialog.show();
        }

        fun showErrorDialog(context: Activity) {
            val alertDialog = AlertDialog.Builder(context)

            alertDialog.setTitle(R.string.error)
            alertDialog.setMessage(R.string.errorInfo)
            alertDialog.setIcon(R.drawable.ic_error)
            alertDialog.setPositiveButton(android.R.string.ok, null)
            alertDialog.show();
        }
    }
}