package com.example.apielib.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.apielib.APIEPackage
import com.example.apielib.model.ApiePackageInfo
import com.example.apielib.utils.ApieService
import com.example.apielib.utils.Resource
import com.example.apielib.utils.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmissionVM(application: Application) : AndroidViewModel(application) {
    private val repository = RetrofitBuilder.buildService(ApieService::class.java)

    val file = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository.downloadFile(APIEPackage.apiePackageInfo.admissionSheet)
                        .body()?.byteStream()
                )
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }


}