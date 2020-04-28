package com.benallouch.vivy.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.view.adapter.DoctorsHolder

@BindingAdapter("adapterDoctors")
fun bindAdapterDoctorsList(view: RecyclerView, reviews: Pair<String, List<Doctor>>?) {
    if (!reviews?.second.isNullOrEmpty()) {
        (view.adapter as DoctorsHolder).updateData(reviews!!)
    }
}

