package com.benallouch.vivy.api.doctors

import com.benallouch.vivy.model.DoctorsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface DoctorsService {
    @GET
    fun fetchDoctors(@Url url: String): Call<DoctorsResponse>
}