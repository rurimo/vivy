package com.benallouch.vivy.api.doctors

import com.benallouch.vivy.api.ApiResponse
import com.benallouch.vivy.api.async
import com.benallouch.vivy.model.DoctorsResponse

class DoctorsClient(private val doctorsService: DoctorsService) {
    fun getDoctors(
        url: String,
        onResult: (response: ApiResponse<DoctorsResponse>) -> Unit
    ) {
        this.doctorsService.fetchDoctors(url).async(onResult)
    }
}