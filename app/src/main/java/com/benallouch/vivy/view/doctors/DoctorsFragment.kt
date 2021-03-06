package com.benallouch.vivy.view.doctors

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benallouch.vivy.R
import com.benallouch.vivy.compose.ViewModelFragment
import com.benallouch.vivy.databinding.FragmentDoctorsBinding
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.view.adapter.AdapterCallbacks
import com.benallouch.vivy.view.adapter.DoctorsAdapter
import com.benallouch.vivy.view.adapter.RecyclerViewPager
import com.benallouch.vivy.view.detail.DOCTOR
import com.benallouch.vivy.view.detail.DoctorDetailActivity
import com.benallouch.vivy.view.detail.REQUEST_CODE
import kotlinx.android.synthetic.main.fragment_doctors.*
import org.koin.android.viewmodel.ext.android.getViewModel


class DoctorsFragment : ViewModelFragment(), AdapterCallbacks {

    private lateinit var recyclerViewPager: RecyclerViewPager
    private lateinit var doctorListAdapter: DoctorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentDoctorsBinding>(inflater, R.layout.fragment_doctors, container)
            .apply {
                viewModel = getViewModel<DoctorsFragmentViewModel>().apply {
                    postDoctorsWithPagination(lastKey = null)
                }
                lifecycleOwner = this@DoctorsFragment
                adapter = DoctorsAdapter(this@DoctorsFragment)
                doctorListAdapter = adapter!!
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupScrollListener()
    }

    private fun setupScrollListener() {
        recyclerViewPager = RecyclerViewPager(
            recyclerView = recyclerView_doctors,
            loadMore = { lastKey ->
                fetchDoctors(lastKey = lastKey)
            })
        recyclerViewPager.isLoading = false
    }

    private fun fetchDoctors(lastKey: String) {
        getViewModel<DoctorsFragmentViewModel>().postDoctorsWithPagination(lastKey)
    }

    override fun onDoctorItemSelected(doctor: Doctor) {
        val intent = Intent(context, DoctorDetailActivity::class.java).putExtra(DOCTOR, doctor)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            //Back press should trigger putting the items in different types (recent or all)
            if (resultCode == Activity.RESULT_CANCELED) {
                doctorListAdapter.dispatchChanges()

                //We can activate this if we want to scroll to the first position
                /*   Timer().schedule(300) {
                       requireActivity().runOnUiThread {
                           recyclerView_doctors.smoothScrollToPosition(
                               0
                           )
                       }
                   }*/
            }
        }
    }

    override fun onDataAvailable(key: String?) {
        recyclerViewPager.setDataLoaded()
        recyclerViewPager.lastKey = key
        progress_doctors.visibility = View.GONE
    }

    companion object {
        const val FRAGMENT_TAG = "DOCTORS_FRAGMENT"
    }


}