package com.benallouch.vivy.api.doctors

import com.benallouch.vivy.model.DoctorsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DoctorsService {
    //https://vivy.com/interviews/challenges/android/doctors-lastkey.json
    @GET("interviews/challenges/android/doctors.json")
    fun fetchDoctors(@Query("lastKey") lastKey: String): Call<DoctorsResponse>
}