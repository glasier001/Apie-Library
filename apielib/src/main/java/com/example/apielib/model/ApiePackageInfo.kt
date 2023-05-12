package com.example.apielib.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiePackageInfo(
    @Expose val id: String = "",
    @Expose val userId: String = "",
    @SerializedName("api_image")
    @Expose val apieImage: String = "",
    @Expose val apieTitle: String = "",
    @Expose val expressPrice: String = "0",
    @Expose val standardPrice: String = "0",
    @Expose val discount: String = "0",
    @Expose val feedback: String = "",
    @Expose val feedbackStatus: String = "",
    @Expose var chart_url: String = "",
    @Expose var gcs_chart_url: String = "",
    @Expose val scenarioA: String = "",
    @Expose val scenarioP: String = "",
    @Expose val scenarioI: String = "",
    @Expose val scenarioE: String = "",
    @Expose val admissionSheet: String = "",
    @Expose val notesInfo: String = "",
    @Expose val patientVideo: String = "",
    @Expose val chartType: String = "news",
    @Expose val patientDetails: String = "",
    @Expose val drugAudio: String = "",
    @Expose val drugVideo: String = "",
    @Expose val drugVideo2: String = "",
    @Expose val drug_chart_url: String = "",
    @Expose var hostUrl: String = "",
    @Expose private val _apiePackageType: String = "standard",
    @Expose var userSelectedPrice: String = standardPrice,
    @Expose var standardDscPrice: String = "0",
    @Expose var expressDscPrice: String = "0",
    @Expose var userSelectedDscPrice: String = standardDscPrice
) : Parcelable {
    @IgnoredOnParcel
    @Expose
    var apiePackageType: String = _apiePackageType
        set(value) {
            field = value
            userSelectedPrice = if (value == "standard") standardPrice else expressPrice
            userSelectedDscPrice = if (value == "standard") standardDscPrice else expressDscPrice
        }

}


