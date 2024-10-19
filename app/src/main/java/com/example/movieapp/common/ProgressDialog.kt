package com.example.movieapp.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.example.movieapp.databinding.DialogProgressLayoutBinding

class ProgressDialog(context: Context) : BaseDialog<DialogProgressLayoutBinding>(context) {

    override fun initBinding(): DialogProgressLayoutBinding {
        return DialogProgressLayoutBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        setCancelable(false)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }
}

abstract class BaseDialog<V : ViewBinding>(context: Context) : Dialog(context) {

    lateinit var binding: V

    abstract fun initBinding(): V

    abstract fun onDialogCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = initBinding()
        setContentView(binding.root)
        window!!.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 40))
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(true)

        onDialogCreated()
    }
}