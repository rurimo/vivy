package com.benallouch.vivy.repository

import androidx.lifecycle.MutableLiveData
import com.benallouch.vivy.api.ApiResponse
import com.benallouch.vivy.api.doctors.DoctorsClient
import com.benallouch.vivy.api.message
import com.benallouch.vivy.model.Doctor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DoctorsRepository constructor(private val doctorsClient: DoctorsClient) {

    init {
        Timber.d("Injection ReviewsRepository")
    }

    suspend fun getDoctors(lastKey:String?, error: (String) -> Unit) =
        withContext(Dispatchers.IO) {
            val liveData = MutableLiveData<List<Doctor>>()
            var doctors = arrayListOf<Doctor>()

            doctorsClient.getDoctors(lastKey) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let {
                            doctors.addAll(it.doctors)
                            liveData.postValue(doctors)
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
            liveData.apply { postValue(doctors) }

        }
}