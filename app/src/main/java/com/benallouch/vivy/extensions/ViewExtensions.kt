package com.benallouch.vivy.extensions

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.style.UnderlineSpan
import com.amulyakhare.textdrawable.TextDrawable
import com.benallouch.vivy.R


fun String.toTextDrawable(
    fontSize: Int, context: Context
): TextDrawable? {
    return TextDrawable.builder()
        .beginConfig()
        .textColor(resolveTextColor(R.color.white_cream, context))
        .useFont(Typeface.MONOSPACE)
        .fontSize(fontSize) /* size in px */
        .bold()
        .withBorder(8)
        .toUpperCase()
        .endConfig()
        .buildRect(this, resolveTextColor(R.color.colorAccent, context))
}

@Suppress("DEPRECATION")
private fun resolveTextColor(color: Int, context: Context) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.resources.getColor(color, context.theme)
    } else {
        context.resources.getColor(color)
    }

fun String.underLineText(): SpannableString {
    val content = SpannableString(this)
    content.setSpan(UnderlineSpan(), 0, this.length, 0)
    return content
}