package com.benallouch.vivy.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.gygreviews.extension.notifyNewData
import com.benallouch.vivy.R
import com.benallouch.vivy.extensions.toTextDrawable
import com.benallouch.vivy.model.Doctor
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.item_doctor.view.*
import java.util.*
import kotlin.properties.Delegates


class DoctorsHolder(val callbacks: Callbacks) : RecyclerView.Adapter<DoctorsHolder.ViewHolder>() {

    private var items: List<Doctor> by Delegates.observable(emptyList()) { _, old, new ->
        notifyNewData(old, new) { o, n -> o.id == n.id }
    }

    interface Callbacks {
        fun onDoctorItemSelected(doctor: Doctor)
        fun onDataAvailable()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_doctor, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    fun updateData(newData: List<Doctor>) {
        items = newData
        callbacks.onDataAvailable()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(doctor: Doctor) {
            itemView.run {
                doctor_tv_name.text = doctor.name
                doctor_website_tv.text = doctor.website
                doctor_rating_iv.rating = doctor.rating.toFloat()
                doctor_rating_count_tv.text = doctor.reviewCount.toString()

                if (doctor.phoneNumber != null) {
                    doctor_phone_btn.visibility = View.VISIBLE
                    doctor_phone_btn.setOnClickListener {
                        it.context.startPhoneCall(doctor.phoneNumber)
                    }

                } else {
                    doctor_phone_btn.visibility = View.GONE
                }

                if (doctor.address != null) {
                    doctor_address_btn.visibility = View.VISIBLE
                    doctor_address_btn.setOnClickListener {
                        it.context.openAddress(doctor.address)
                    }

                } else {
                    doctor_address_btn.visibility = View.GONE
                }

                (doctor.photoId).run {
                    if (this != null) {
                        Glide.with(context)
                            .load(this)
                            .listener(
                                GlidePalette.with(this)
                                    .use(BitmapPalette.Profile.VIBRANT)
                                    .crossfade(true)
                            )
                            .into(doctor_iv_photo)
                    } else {
                        doctor_iv_photo.setImageDrawable(
                            doctor.name[0].toString().toTextDrawable(
                                80,
                                context
                            )
                        )
                    }
                }
                row_doctor_cv.setOnClickListener {
                    callbacks.onDoctorItemSelected(doctor)
                }
            }
        }
    }

    private fun Context.startPhoneCall(phoneNumber: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel: $phoneNumber")
        ContextCompat.startActivity(this, intent, null)
    }

    private fun Context.openAddress(address: String) {
        val uri: String =
            java.lang.String.format(Locale.ENGLISH, "geo:0,0?q=%s", address)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        this.startActivity(intent)
    }
}
