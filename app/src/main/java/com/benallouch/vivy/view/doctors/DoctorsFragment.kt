package com.benallouch.vivy.view.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benallouch.vivy.R
import com.benallouch.vivy.compose.ViewModelFragment
import com.benallouch.vivy.databinding.FragmentDoctorsBinding
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.view.adapter.DoctorsHolder
import com.benallouch.vivy.view.adapter.RecyclerViewPager
import kotlinx.android.synthetic.main.fragment_doctors.*
import org.koin.android.viewmodel.ext.android.getViewModel

class DoctorsFragment : ViewModelFragment(), DoctorsHolder.Callbacks {

    private lateinit var rv: RecyclerViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentDoctorsBinding>(inflater, R.layout.fragment_doctors, container)
            .apply {
                viewModel = getViewModel<DoctorsFragmentViewModel>().apply {
                    postDoctorsWithPagination(
                        lastKey = null
                    )
                }
                lifecycleOwner = this@DoctorsFragment
                adapter = DoctorsHolder(this@DoctorsFragment)
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupScrollListener()
    }

    private fun setupScrollListener() {
        rv = RecyclerViewPager(
            recyclerView = recyclerView_doctors,
            loadMore = { lastKey ->
                fetchDoctors(lastKey = lastKey)
            })
        rv.isLoading = false
    }

    private fun fetchDoctors(lastKey: String) {
        getViewModel<DoctorsFragmentViewModel>().postDoctorsWithPagination(lastKey)
    }

    override fun onDoctorItemSelected(doctor: Doctor) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataAvailable(key: String?) {
        rv.setDataLoaded()
        rv.lastKey = key
        progress_doctors.visibility = View.GONE
    }

    companion object {
        const val FRAGMENT_TAG = "DOCTORS_FRAGMENT"
    }

}