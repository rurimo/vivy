package com.benallouch.vivy.view.adapter

import com.benallouch.vivy.model.Doctor

/**
 * Dispatch callbacks from the adapter to the fragment
 */
interface AdapterCallbacks {
        fun onDoctorItemSelected(doctor: Doctor)
        fun onDataAvailable(lastKey: String?)
    }