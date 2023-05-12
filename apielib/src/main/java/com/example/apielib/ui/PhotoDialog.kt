package com.example.apielib.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.apielib.R
import kotlinx.android.synthetic.main.dialog_photo.*

class PhotoDialog: DialogFragment(R.layout.dialog_photo) {
    private val args: PhotoDialogArgs by navArgs()

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
        imgPhoto.setImage(ImageSource.uri(args.filePath))
    }

}