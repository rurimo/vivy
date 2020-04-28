package com.benallouch.vivy.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.benallouch.vivy.R
import java.util.*


fun Context.startPhoneCall(phoneNumber: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_DIAL
    intent.data = Uri.parse("tel: $phoneNumber")
    ContextCompat.startActivity(this, intent, null)
}

fun Context.openAddress(address: String) {
    val uri: String =
        java.lang.String.format(Locale.ENGLISH, "geo:0,0?q=%s", address)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    this.startActivity(intent)
}

fun Context.sendEmail(address: String) {
    val i = Intent(Intent.ACTION_SEND)
    i.type = "message/rfc822"
    i.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
    try {
        ContextCompat.startActivity(this, Intent.createChooser(i, getString(R.string.mailTo)), null)
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(this, getString(R.string.error_no_mail), Toast.LENGTH_SHORT)
            .show()
    }
}

fun Context.openWebPage(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}