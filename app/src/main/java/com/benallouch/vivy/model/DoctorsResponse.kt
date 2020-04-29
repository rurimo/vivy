package com.benallouch.vivy.model

data class DoctorsResponse (
	val doctors : List<Doctor>,
	val lastKey : String?
)