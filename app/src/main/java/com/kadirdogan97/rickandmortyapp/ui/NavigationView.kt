package com.kadirdogan97.rickandmortyapp.ui

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.RadioButton
import com.kadirdogan97.rickandmortyapp.R
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import kotlinx.android.synthetic.main.filter_dialog.view.*

class NavigationView{
    lateinit var dialog:Dialog
    lateinit var inflater: View
    lateinit var mDialogResult: FilterDialogListener

    fun showFilterDialog(context: Context,filter: Filter){
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
        var index = 0
        for (i in context?.resources?.getStringArray(R.array.gender)!!) {
            val rb = RadioButton(context)
            rb.text = i
            rb.id = index++
            rb.isChecked = checkRadio(i,filter.gender!!)
            radiogroupG?.addView(rb)
        }
        for (i in context?.resources?.getStringArray(R.array.status)!!) {
            val rb = RadioButton(context)
            rb.text = i
            rb.id = index++
            rb.isChecked = checkRadio(i,filter.status!!)
            radiogroupS?.addView(rb)
        }
        dialog.show()
        inflater.cancel_button.setOnClickListener {
            close()
        }
        inflater.apply_button.setOnClickListener {
            val radioButtonS: RadioButton = inflater.findViewById(radiogroupS.checkedRadioButtonId)
            val radioButtonG: RadioButton = inflater.findViewById(radiogroupG.checkedRadioButtonId)
            val status = radioButtonS.text?:""
            val gender = radioButtonG.text?:""
            mDialogResult.applyFilters(status.toString(), gender.toString())
            close()
        }
    }

    fun checkRadio(item: String, filter: String): Boolean{
        return if(item==filter){
            true
        }else filter == "" && item == "All"
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