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
import com.example.apielib.R
import kotlinx.android.synthetic.main.dialog_pdfview.*
import java.io.File

class PdfViewDialog: DialogFragment(R.layout.dialog_pdfview) {
    private val args: PdfViewDialogArgs by navArgs()

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
        pdfView.fromFile(File(args.filePath)).load()

    }

}