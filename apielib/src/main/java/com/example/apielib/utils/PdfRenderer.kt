package com.example.apielib.utils

import android.app.Activity
import android.os.Build
import android.print.PdfPrint
import android.print.PrintAttributes
import android.print.PrintAttributes.Resolution
import android.webkit.WebView

object PdfRenderer {
    fun printToPdf(
        webView: WebView,
        directory: String?,
        callback: Callback
    ) {
        val jobName = " Document"
        lateinit var attributes: PrintAttributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            attributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()
        }
        val pdfPrint = PdfPrint(attributes)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pdfPrint.print(
                webView.createPrintDocumentAdapter(jobName),
                directory,
                object : PdfPrint.CallbackPrint {
                    override fun success(path: String?) {
                        callback.success(path)
                    }

                    override fun onFailure() {
                        callback.failure()
                    }
                })
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                pdfPrint.print(
                    webView.createPrintDocumentAdapter(),
                    directory,
                    object : PdfPrint.CallbackPrint {
                        override fun success(path: String?) {
                            callback.success(path)
                        }

                        override fun onFailure() {
                            callback.failure()
                        }
                    })
            }
        }
    }

    /**
     * callback interface to get the result back after created pdf file
     */
    interface Callback {
        fun success(path: String?)
        fun failure()
    }
}