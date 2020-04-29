package com.benallouch.vivy.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.view.adapter.DoctorsAdapter

@BindingAdapter("adapterDoctors")
fun bindAdapterDoctorsList(view: RecyclerView, reviews: Pair<String, List<Doctor>>?) {
    if (!reviews?.second.isNullOrEmpty()) {
        (view.adapter as DoctorsAdapter).updateData(reviews!!)
    }
}

