package com.benallouch.vivy.binding

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter("toast")
fun bindToast(view: View, text: LiveData<String>) {
    text.value?.let {
        Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Used for the progress bar
 */
@BindingAdapter("visibilityByResource")
fun bindVisibilityByResource(view: View, list: List<Any>?) {
    when (list.isNullOrEmpty()) {
        true -> view.visibility = View.VISIBLE
        false -> view.visibility = View.GONE
    }
}