package com.benallouch.vivy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Doctor(
    val id: String,
    val name: String,
    val photoId: String,
    val rating: Double,
    val address: String,
    val lat: Double,
    val lng: Double,
    val highlighted: Boolean,
    val reviewCount: Int,
    val specialityIds: List<Int>,
    val source: String,
    val phoneNumber: String,
    val email: String,
    val website: String,
    val openingHours: List<String>,
    val integration: String,
    val translation: String
) : Parcelable