package com.benallouch.vivy.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.benallouch.vivy.model.DoctorsResponse
import com.benallouch.vivy.view.adapter.DoctorsAdapter

/**
 * using android Binding lib we bind the fetched data with the recyclerView in the xml files
 */
@BindingAdapter("adapterDoctors")
fun bindAdapterDoctorsList(view: RecyclerView, doctorsResponse: DoctorsResponse?) {
    if (!doctorsResponse?.doctors.isNullOrEmpty()) {
        (view.adapter as DoctorsAdapter).updateData(doctorsResponse!!)
    }
}

