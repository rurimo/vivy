package com.benallouch.vivy.api

import com.benallouch.vivy.api.doctors.DoctorsService
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import java.io.IOException


class DoctorsRepositoryTest : ApiAbstract<DoctorsService>() {
    private lateinit var service: DoctorsService


    @Before
    fun initService() {
        this.service = createService(DoctorsService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getDoctorsFirstPage() {
        enqueueResponse("doctors_response_1.json")
        val response = this.service.fetchDoctors(MockTestUtil.firstPageUrl).execute()
        assert(response.isSuccessful)
        MatcherAssert.assertThat(
            response.body()!!.doctors.first().id,
            CoreMatchers.`is`("ChIJyZz_b-lRqEcRI7WMafDasLg")
        )
        MatcherAssert.assertThat(
            response.body()!!.lastKey,
            CoreMatchers.`is`(MockTestUtil.lastKey)
        )
    }

    @Throws(IOException::class)
    @Test
    fun getDoctorsSecondPage() {
        enqueueResponse("doctors_response_2.json")
        val response = this.service.fetchDoctors(MockTestUtil.secondPageUrl).execute()
        assert(response.isSuccessful)

        MatcherAssert.assertThat(
            response.body()!!.doctors.first().id,
            CoreMatchers.`is`("ChIJ0_CBTCNSqEcRheFgjqqpndk")
        )
    }
}