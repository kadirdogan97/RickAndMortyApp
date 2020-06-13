package com.kadirdogan97.rickandmortyapp.ui

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.RadioButton
import com.kadirdogan97.rickandmortyapp.R
import kotlinx.android.synthetic.main.filter_dialog.view.*

class NavigationView{
    lateinit var dialog:Dialog
    lateinit var inflater: View
    lateinit var mDialogResult: FilterDialogListener

    fun showFilterDialog(context: Context){
        inflater = LayoutInflater.from(context).inflate(R.layout.filter_dialog, null)
        dialog= Dialog(context,R.style.Dialog)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(inflater)
        var window: Window = dialog.window!!
        var wlp: WindowManager.LayoutParams = window!!.attributes
        wlp.gravity = Gravity.CENTER_HORIZONTAL
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.attributes = wlp
        var radiogroupG = inflater.radiogroupGender
        var radiogroupS = inflater.radiogroupStatus
        for (i in context?.resources?.getStringArray(R.array.gender)!!) {
            val rb = RadioButton(context)
            rb.text = i
            radiogroupG?.addView(rb)
        }
        for (i in context?.resources?.getStringArray(R.array.status)!!) {
            val rb = RadioButton(context)
            rb.text = i
            radiogroupS?.addView(rb)
        }
        dialog.show()
        inflater.cancel_button.setOnClickListener {
            close()
        }
        inflater.apply_button.setOnClickListener {
            val radioButtonS: RadioButton = inflater.findViewById(radiogroupS.checkedRadioButtonId)
            val radioButtonG: RadioButton = inflater.findViewById(radiogroupG.checkedRadioButtonId)
            val status = radioButtonS.text.toString()
            val gender = radioButtonG.text.toString()
            mDialogResult.applyFilters(status, gender)
            close()
        }
    }

    fun close(){
        if(dialog!=null) {
            dialog.dismiss()
        }
    }
    fun setDialogResult(dialogResult: FilterDialogListener) {
        mDialogResult = dialogResult
    }
    interface FilterDialogListener {
        fun applyFilters(status: String, gender: String)
    }
}