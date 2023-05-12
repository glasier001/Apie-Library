package com.example.apielib.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.apielib.APIEPackage
import com.example.apielib.model.ApiePackageInfo
import com.example.apielib.model.ApieSubmission
import com.example.apielib.utils.Event
import com.example.apielib.utils.Station
import kotlinx.coroutines.launch

class ApieVM(application: Application) : AndroidViewModel(application) {
    val apie = MutableLiveData(
        ApieSubmission(
            APIEPackage.apiePackageInfo.id,
            APIEPackage.apiePackageInfo.userId,
            apiePackageType = APIEPackage.apiePackageInfo.apiePackageType
        )
    )
    private val _nextStation = MutableLiveData(Station.STATION_A)
    var currentStation = Station.STATION_A
    val nextStation: LiveData<Station>
        get() = _nextStation

    var drugRecording = ""
    var videoRecording = ""

    fun insertNewsChart(filePath: String) {
        apie.value = apie.value?.copy(
            stationAChart = filePath,
            isReadyToSubmit = _nextStation.value == Station.STATION_SUBMIT
        )
    }

    fun insertGcsChart(filePath: String) {
        apie.value = apie.value?.copy(
            stationAChart = filePath,
            isReadyToSubmit = _nextStation.value == Station.STATION_SUBMIT
        )
    }

    fun insertPlanChart(filePath: String) {
        apie.value = apie.value?.copy(
            stationPChart = filePath,
            isReadyToSubmit = _nextStation.value == Station.STATION_SUBMIT
        )
    }

    fun insertDrugChart(filePath: String) {
        apie.value = apie.value?.copy(
            stationIChart = filePath,
            drugRecording = drugRecording,
            isReadyToSubmit = _nextStation.value == Station.STATION_SUBMIT
        )
        apie.value = apie.value?.copy(drugRecording = drugRecording)
    }


    fun insertVideoRecording() {
        apie.value = apie.value?.copy(videoRecording = videoRecording, isReadyToSubmit = true)
    }

    fun setNextStation() {

        _nextStation.value?.let {
            currentStation = Station.values()[it.ordinal + 1]
            _nextStation.value = Station.values()[it.ordinal + 1]


        }

    }

    fun endApie() {
        _nextStation.value = Station.STATION_SUBMIT
    }


}