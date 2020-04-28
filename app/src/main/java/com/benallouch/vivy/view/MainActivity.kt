package com.benallouch.vivy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.benallouch.vivy.R
import com.benallouch.vivy.view.doctors.DoctorsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }

    private fun initializeUI() {
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragmentHolder, DoctorsFragment(), DoctorsFragment.FRAGMENT_TAG)
            commit()
        }
    }
}
