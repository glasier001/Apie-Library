package com.example.apielib.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.view.drawToBitmap
import com.example.apielib.APIEPackage
import com.example.apielib.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import com.otaliastudios.zoom.ZoomLayout


enum class Station(
    val scenario: String = "",
    val scenarioText: String = "",
    val message: String = "Are you sure you want to go to the next station?",
    val timeoutMessage: String = "Please continue to the next station"
) {
    STATION_A(
        APIEPackage.apiePackageInfo.scenarioA, "Scenario - Station A"
    ),
    STATION_P(
        APIEPackage.apiePackageInfo.scenarioP, "Scenario - Station P"
    ),
    STATION_I(
        APIEPackage.apiePackageInfo.scenarioI, "Scenario - Station I"
    ),
    STATION_E(
        APIEPackage.apiePackageInfo.scenarioE, "Scenario - Station E",
        "Are you sure you want to submit your APIE package?",
        "Press OK to submit your APIE package"
    ),
    STATION_SUBMIT
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun convertDipToPixels(context: Context, dip: Int): Int {
    val resources: Resources = context.resources
    val px = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip.toFloat(),
        resources.displayMetrics
    )
    return px.toInt()
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

fun View.saveBitmapToFile(filePath: String) {
    try {
        val r = drawToBitmap()
        // Compress the bitmap and save in jpg format
        val stream: OutputStream = FileOutputStream(File(filePath))
        r.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}