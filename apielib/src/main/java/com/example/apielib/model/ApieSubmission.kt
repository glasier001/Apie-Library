package com.example.apielib.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import okhttp3.RequestBody

@Parcelize
data class ApieSubmission(
    @Expose val apiePackageId: String = "",
    @Expose var userId: String = "",
    @Expose var apiePackageType: String? = "standard",
    @Expose var stationAChart: String = "",
    @Expose var stationPChart: String = "",
    @Expose var stationIChart: String = "",
    @Expose var drugRecording: String = "",
    @Expose var videoRecording: String = "",
    var isReadyToSubmit: Boolean = false

) : Parcelable