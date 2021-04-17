package com.remyrobotics.graphql.activity

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.remyrobotics.graphql.R

/**
 * Base activity for all activities
 */

open class BaseActivity : AppCompatActivity() {

    var loadingDialog: AppCompatDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Show Loading Dialog
     *
     * @param activity
     */
    protected open fun showLoadDialog(activity: Activity?) {
        if (activity == null) return

        if (loadingDialog != null && loadingDialog!!.isShowing) {
            //loadingDialog?.dismiss();
        } else {
            loadingDialog = AppCompatDialog(activity, R.style.LoadingDialog)
            loadingDialog?.setCancelable(false)
            loadingDialog?.setCanceledOnTouchOutside(false)
            loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog?.setContentView(R.layout.view_dialog_loading)
            if (!activity.isFinishing) loadingDialog?.show()
        }
    }

    /**
     * Hide Loading Dialog
     */
    protected open fun hideLoadDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

}