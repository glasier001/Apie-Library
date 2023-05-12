package android.print

import android.os.Build
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PrintDocumentAdapter.LayoutResultCallback
import android.print.PrintDocumentAdapter.WriteResultCallback
import android.util.Log
import java.io.File

class PdfPrint(private val printAttributes: PrintAttributes) {
    private var mFullPath: String? = null
    fun print(
        printAdapter: PrintDocumentAdapter,
        fullPath: String?,
        callback: CallbackPrint
    ) {
        mFullPath = fullPath
        if (mFullPath == null || mFullPath == "") {
            Log.e(TAG, "Please check the given path of file!")
            callback.onFailure()
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            printAdapter.onLayout(null, printAttributes, null, object : LayoutResultCallback() {
                override fun onLayoutFinished(
                    info: PrintDocumentInfo,
                    changed: Boolean
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        printAdapter.onWrite(
                            arrayOf(PageRange.ALL_PAGES),
                            getOutputFile(mFullPath!!),
                            CancellationSignal(),
                            object : WriteResultCallback() {
                                override fun onWriteFinished(pages: Array<PageRange>) {
                                    super.onWriteFinished(pages)
                                    if (pages.isNotEmpty()) {
                                        callback.success(mFullPath)
                                    } else {
                                        callback.onFailure()
                                    }
                                }
                            })
                    }
                }
            }, null)
        }
    }

    private fun getOutputFile(fullPath: String): ParcelFileDescriptor? {
        val file = File(fullPath)
        try {
            Log.e("pdf print file path",file.absolutePath)
            file.createNewFile()
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor", e)
        }
        return null
    }

    interface CallbackPrint {
        fun success(path: String?)
        fun onFailure()
    }

    companion object {
        private val TAG = PdfPrint::class.java.simpleName
    }

}