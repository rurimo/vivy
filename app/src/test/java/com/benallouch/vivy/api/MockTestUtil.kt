package com.benallouch.vivy.api

import com.benallouch.vivy.model.Doctor

class MockTestUtil {

    companion object {

        const val lastKey = "CvQD7gEAAKjcb"

        const val firstPageUrl = "https://vivy.com/interviews/challenges/android/doctors.json"
        const val secondPageUrl =
            "https://vivy.com/interviews/challenges/android/doctors-$lastKey.json"

        fun mocKDoctor() = Doctor(
            id = "ChIJyZz_b-lRqEcRI7WMafDasLg",
            name = "Dr. med. Mario Voss",
            photoId = null,
            rating = 2.5,
            address = "Friedrichstraße 115, 10117 Berlin, Germany",
            lat = 52.526779,
            lng = 13.387201,
            highlighted = false,
            reviewCount = 5,
            specialityIds = listOf(),
            source = "google",
            phoneNumber = "+29 30 28391555",
            email = null,
            website = "http=//www.vivy-doc.de/",
            integration = null,
            translation = null, openingHours = listOf()
        )

        fun mockDoctorsList(): Pair<String, List<Doctor>> {
            val doctors = ArrayList<Doctor>()
            doctors.add(mocKDoctor())
            return Pair(lastKey, doctors)
        }
    }

}