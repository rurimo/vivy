package com.benallouch.vivy.compose

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ViewModelActivity : AppCompatActivity() {

  protected inline fun <reified T : ViewDataBinding> binding(resId: Int): Lazy<T> =
    lazy { DataBindingUtil.setContentView<T>(this, resId) }
}
