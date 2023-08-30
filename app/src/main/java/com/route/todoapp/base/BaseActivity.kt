package com.route.todoapp.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity

class BaseActivity:AppCompatActivity() {
    var progressDialoge:ProgressDialog?=null
    fun showLoadingDialoge(){
        progressDialoge=ProgressDialog(this)
        progressDialoge?.setMessage("loading....")
        progressDialoge?.show()
    }
    fun hidenDiolage(){
        progressDialoge?.dismiss()
    }
    var alartDialoge:AlertDialog?=null
    fun showMesssage(
        message:String,
        postActionTitle:String?=null,
        postAction:DialogInterface.OnClickListener?=null,
        negActionTitle:String?=null,
        negAction:DialogInterface.OnClickListener?=null,
        cancelDialog:Boolean=true
    ){
        var messageDialogBulider=AlertDialog.Builder(this)
        messageDialogBulider.setMessage(message)
        if(postActionTitle!=null){
            messageDialogBulider.setPositiveButton(postActionTitle,postAction?:DialogInterface.OnClickListener{
                dialog,which-> dialog.dismiss()
            })
        }
        if(negActionTitle!=null){
            messageDialogBulider.setNegativeButton(negActionTitle,negAction?:DialogInterface.OnClickListener{
                    dialog,which-> dialog.dismiss()
            })
        }
        messageDialogBulider.setCancelable(cancelDialog)
        alartDialoge=messageDialogBulider.show()
    }
}