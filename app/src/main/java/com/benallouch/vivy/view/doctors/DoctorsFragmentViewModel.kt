package com.benallouch.vivy.view.doctors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.benallouch.vivy.compose.DispatchViewModel
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.model.DoctorsResponse
import com.benallouch.vivy.repository.DoctorsRepository
import timber.log.Timber

class DoctorsFragmentViewModel constructor(private val doctorsRepository: DoctorsRepository) :
    DispatchViewModel() {

    private var doctorsPageLiveData: MutableLiveData<String?> = MutableLiveData()
    val doctorsListLiveData: LiveData<DoctorsResponse>

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("injection DoctorsFragmentViewModel")

        this.doctorsListLiveData = doctorsPageLiveData.switchMap {lastKey->
            launchOnViewModelScope {
                doctorsRepository.getDoctors(lastKey = lastKey) {
                    toastLiveData.postValue(it)

                }
            }
        }
    }

    fun postDoctorsWithPagination(lastKey: String?) {
        doctorsPageLiveData.postValue(lastKey)
    }
}

