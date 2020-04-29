package com.benallouch.vivy.view.adapter

import com.benallouch.vivy.model.Doctor

interface AdapterCallbacks {
        fun onDoctorItemSelected(doctor: Doctor)
        fun onDataAvailable(lastKey: String?)
    }