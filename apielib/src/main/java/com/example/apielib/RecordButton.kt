package com.example.apielib


import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import com.example.apielib.utils.convertDipToPixels


class RecordButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageButton(context, attrs, defStyleAttr) {


    private val startRecordDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.start_video_record_button)
    private val stopRecordDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.stop_video_record_button)
    private val iconPadding = 8
    private val iconPaddingStop = 18

    constructor(context: Context) : this(context, null, 0) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    private fun setIconPadding(paddingDP: Int) {
        val padding: Int = convertDipToPixels(context, paddingDP)
        setPadding(padding, padding, padding, padding)
    }

    init {
        background = ContextCompat.getDrawable(context, R.drawable.circle_frame_background)
        displayVideoRecordStateReady()
    }

    fun displayVideoRecordStateReady() {
        setImageDrawable(startRecordDrawable)
        setIconPadding(iconPadding)
    }

    fun displayVideoRecordStateInProgress() {
        setImageDrawable(stopRecordDrawable)
        setIconPadding(iconPaddingStop)
    }
}