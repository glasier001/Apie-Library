package com.example.apielib

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.apielib.model.ApiePackageInfo
import com.example.apielib.model.ApieSubmission
import com.example.apielib.ui.AdmissionFragment
import com.example.apielib.ui.ApieActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

object APIEPackage {
    const val PATIENT_VIDEO_DURATION: Long = 5 * 60 * 1000
//    const val PATIENT_VIDEO_DURATION: Long = 5 * 1000
    const val STATION_DURATION: Long = 15 * 60 * 1000
    const val STATION_E_VIDEO_DURATION: Long = 5 * 60 * 1000

    lateinit var apiePackageInfo: ApiePackageInfo
}

class FetchApieSubmission : ActivityResultContract<ApiePackageInfo, ApieSubmission?>() {

    override fun createIntent(context: Context, apiePackageInfo: ApiePackageInfo): Intent {
//        var apie = Gson().fromJson(json,ApiePackageInfo::class.java)
//        APIEPackage.apiePackageInfo = apie
        APIEPackage.apiePackageInfo = apiePackageInfo
        return Intent(context, ApieActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ApieSubmission? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra<ApieSubmission>("data")
    }

}

//val json = "{\"id\":\"1\",\"admissionSheet\":\"sdfds\",\"discount\":\"20\",\"apieTitle\":\"sdfds\",\"chartType\":\"news\",\"drugVideo\":\"https:\\/\\/player.vimeo.com\\/video\\/384230977\",\"expressPrice\":\"1\",\"feedback\":\"done 1\",\"feedbackStatus\":\"done\",\"notesInfo\":\"sadsa\",\"patientDetails\":\"sadsa\",\"patientVideo\":\"asdsa\",\"scenarioA\":\"asd\",\"scenarioE\":\"asdsa\",\"scenarioI\":\"asdsa\",\"scenarioP\":\"asdsa\",\"standardPrice\":\"80\",\"userId\":\"12\",\"drugChartInfo\":{\"dob\":\"25\\/02\\/197\",\"name\":\"MIA KHATAR\",\"ward\":\"ENDOSCOPY UNIT\",\"gender\":\"FEMALE\",\"height\":\"170 cm\",\"weight\":\"65 kg\",\"address\":\"41 ALMOND CLOSE\\nTATTERELL, LL12 TBU\",\"wardType\":\"MEDICAL\",\"onceDose1\":[\"TODAY\",\"10:00\",\"MIDAZOLAM\",\"2 mg\",\"IV\",\"Dr P Smith, 3459\",\"Karen Tang RN\",\"Siju Thomas RN\",\"10:00\"],\"onceDose2\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"onceDose3\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"dietaryReq\":\"\",\"hospitalNo\":\"0145692498\",\"allergyDate\":\"TODAY\",\"drugAllergy1\":[\"NONE KNOWN\",\"\"],\"drugAllergy2\":[\"\",\"\"],\"drugAllergy3\":[\"\",\"\"],\"hasAllergies\":\"NO\",\"fluidTherapy1\":[\"TODAY\",\"0.9% NORMAL SALINE\",\"500ml\",\"250 ml \\/ hour\",\"Dr P Smith,3459\",\"099987\",\"11.10\",\"K Tang RN\",\"S Cook RN\",\"13:10\"],\"fluidTherapy2\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"fluidTherapy3\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"hasDietaryReq\":\"NO\",\"antiMicrobial1\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"antiMicrobial2\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"antiMicrobial3\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"oxygenTherapy1\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"oxygenTherapy2\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"prnMedication1\":[\"TODAY\",\"PARACETAMOL\",\"1 g\",\"PO\",\"6 HOURLY PAIN\",\"Dr P Smith,3459\",\"\",\"\"],\"prnMedication2\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"wardConsultant\":\"DR DANIELS\",\"allergySignature\":\"Dr A.Kitridge\",\"admissionDateTime\":\"TODAY 07:00\",\"regularMedication1\":[\"OMEPRAZOLE\",\"TODAY\",\"TOMORROW\",\"TODAY\",\"20 mg\",\"ONCE DAILY\",\"PO\",\"1 DAY\",\"\",\"\",\"\",\"Today\",\"16:00\",\"\",\"\",\"Tomorrow\",\"\",\"\",\"\",\"Dr P Smith, 3459\",\"\",\"\",\"\"],\"regularMedication2\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"regularMedication3\":[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"],\"drugNotAdministered1\":[\"\",\"\",\"\",\"\",\"\"],\"drugNotAdministered2\":[\"\",\"\",\"\",\"\",\"\"],\"drugNotAdministered3\":[\"\",\"\",\"\",\"\",\"\"],\"drugNotAdministered4\":[\"\",\"\",\"\",\"\",\"\"],\"drugNotAdministered5\":[\"\",\"\",\"\",\"\",\"\"],\"drugNotAdministered6\":[\"\",\"\",\"\",\"\",\"\"]},\"created_date\":\"2020-05-04\",\"api_image\":\"\"}"