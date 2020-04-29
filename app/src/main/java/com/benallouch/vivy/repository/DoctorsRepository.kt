package com.benallouch.vivy.repository

import androidx.lifecycle.MutableLiveData
import com.benallouch.vivy.api.ApiResponse
import com.benallouch.vivy.api.doctors.DoctorsClient
import com.benallouch.vivy.api.message
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.model.DoctorsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val DOCTORS_URL = "https://vivy.com/interviews/challenges/android/doctors"

class DoctorsRepository constructor(private val doctorsClient: DoctorsClient) {


    init {
        Timber.d("Injection ReviewsRepository")
    }

    suspend fun getDoctors(lastKey: String?, error: (String) -> Unit) =
        withContext(Dispatchers.IO) {
            val liveData = MutableLiveData<DoctorsResponse>()
            var doctors = arrayListOf<Doctor>()

            var url = getUrl(lastKey)

            doctorsClient.getDoctors(url) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let {
                            doctors.addAll(it.doctors)
                            liveData.postValue(DoctorsResponse(doctors, lastKey))
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
            liveData.apply { postValue(DoctorsResponse(doctors, lastKey)) }

        }

    private fun getUrl(lastKey: String?): String {
        var url = if (lastKey != null) {
            "$DOCTORS_URL-$lastKey.json"
        } else {
            "$DOCTORS_URL.json"
        }
        return url
    }

}