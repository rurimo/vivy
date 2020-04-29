package com.benallouch.vivy.view.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.benallouch.vivy.R
import com.benallouch.vivy.extensions.*
import com.benallouch.vivy.model.Doctor
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.activity_doctor_detail.*

const val DOCTOR = "Doctor"
const val REQUEST_CODE=999

class DoctorDetailActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_detail)

        val actionbar = supportActionBar
        actionbar!!.title = "Doctor Details"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val intentDoctor = intent.getParcelableExtra<Doctor>(DOCTOR)
        intentDoctor?.let { renderReview(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun renderReview(doctor: Doctor) {
        doctor.run {
            doctor_tv_name.text = name
            doctor_rating_iv.rating = rating.toFloat()
            doctor_rating_number.text = "($reviewCount)"

            if (phoneNumber != null) {
                doctor_phone_tv.text = phoneNumber.replace(" ", "").underLineText()
                doctor_phone_tv.visibility = View.VISIBLE
                doctor_phone_tv.setOnClickListener {
                    it.context.startPhoneCall(phoneNumber)
                }

            } else {
                doctor_phone_tv.visibility = View.GONE
            }

            if (address != null) {
                doctor_address_tv.text = address.underLineText()
                doctor_address_tv.visibility = View.VISIBLE
                doctor_address_tv.setOnClickListener {
                    it.context.openAddress(address)
                }

            } else {
                doctor_address_tv.visibility = View.GONE
            }

            if (email != null) {
                doctor_email_tv.text = email.underLineText()
                doctor_email_tv.visibility = View.VISIBLE
                doctor_email_tv.setOnClickListener {
                    it.context.sendEmail(email)
                }

            } else {
                doctor_email_tv.visibility = View.GONE
            }
            if (website != null) {
                doctor_website_tv.text = website
                doctor_website_tv.visibility = View.VISIBLE
                doctor_website_tv.setOnClickListener {
                    it.context.openWebPage(website)
                }

            } else {
                doctor_website_tv.visibility = View.GONE
            }

            (photoId).run {
                if (this != null) {
                    Glide.with(this@DoctorDetailActivity)
                        .load(this)
                        .listener(
                            GlidePalette.with(this)
                                .use(BitmapPalette.Profile.VIBRANT)
                                .crossfade(true)
                        )
                        .into(doctor_iv_photo)
                } else {
                    doctor_iv_photo.setImageDrawable(
                        name[0].toString().toTextDrawable(
                            120,
                            this@DoctorDetailActivity
                        )
                    )
                }
            }
        }
    }

}


