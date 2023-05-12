package com.example.apielib.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.github.gcacace.signaturepad.views.SignaturePad


import com.example.apielib.R
import kotlinx.android.synthetic.main.fragment_sign.*

class SignDialog : DialogFragment(R.layout.fragment_sign) {
    lateinit var onSignedListener: OnSignedListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgCancel.setOnClickListener { dismiss() }
        btnClear.setOnClickListener { viewSign.clear() }
        btnOk.setOnClickListener {
            onSignedListener.onSigned(viewSign.signatureBitmap)
            dismiss()
        }
    }

    interface OnSignedListener {
        fun onSigned(bmp: Bitmap)
    }
}
