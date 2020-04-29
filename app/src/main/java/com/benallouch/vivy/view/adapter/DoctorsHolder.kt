package com.benallouch.vivy.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benallouch.vivy.R
import com.benallouch.vivy.extensions.notifyNewData
import com.benallouch.vivy.extensions.openAddress
import com.benallouch.vivy.extensions.startPhoneCall
import com.benallouch.vivy.extensions.toTextDrawable
import com.benallouch.vivy.model.Doctor
import com.benallouch.vivy.model.HeaderItem
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import kotlinx.android.synthetic.main.header_layout.view.*
import kotlinx.android.synthetic.main.item_doctor.view.*
import kotlin.properties.Delegates

private const val TYPE_HEADER = 1
private const val TYPE_DATA = 2

class DoctorsHolder(val callbacks: AdapterCallbacks) :
    RecyclerView.Adapter<DoctorsHolder.BaseViewHolder<*>>() {

    private var items: List<ListItem> by Delegates.observable(emptyList()) { _, old, new ->
        notifyNewData(old, new) { o, n -> o.itemType == n.itemType }
    }

    private var headerItemRecent = arrayListOf<ListItem>()
    private var headerItemAll = arrayListOf<ListItem>()
    private var sortedDoctors = arrayListOf<Doctor>()
    private var fetchedItemsRecent = arrayListOf<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> StickyHolder(
                layoutInflater.inflate(R.layout.header_layout, parent, false)

            )
            else -> DoctorViewHolder(layoutInflater.inflate(R.layout.item_doctor, parent, false))

        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = items[position]
        when (holder) {
            is StickyHolder -> holder.bind(item as HeaderItem)
            is DoctorViewHolder -> holder.bind(item as Doctor)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Doctor -> TYPE_DATA
            is HeaderItem -> TYPE_HEADER
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    fun updateData(newData: Pair<String, List<Doctor>>) {
        headerItemRecent.clear()
        headerItemAll.clear()
        headerItemRecent.add(HeaderItem("Recent Doctors"))
        headerItemAll.add(HeaderItem("Vivy Doctors"))

        val fetchedItemsAll = arrayListOf<Doctor>()
        fetchedItemsAll.addAll(newData.second as ArrayList<Doctor>)

        sortedDoctors.addAll((fetchedItemsAll.sortedWith(compareByDescending<Doctor> { it.rating }
            .thenByDescending { it.name })))

        showDoctorsByType()
        callbacks.onDataAvailable(newData.first)
    }

    fun showDoctorsByType() {
        sortedDoctors.removeAll(fetchedItemsRecent)

        items = if (fetchedItemsRecent.isNotEmpty())
            headerItemRecent + fetchedItemsRecent.reversed() + headerItemAll + sortedDoctors
        else
            sortedDoctors
    }

    fun dispatchChanges() = notifyDataSetChanged()

    inner class DoctorViewHolder(view: View) : BaseViewHolder<Doctor>(view) {
        override fun bind(doctor: Doctor) {
            itemView.run {
                doctor_tv_name.text = doctor.name
                doctor_rating_iv.rating = doctor.rating.toFloat()

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
                    fetchedItemsRecent.add(doctor)
                    showDoctorsByType()
                }
            }
        }
    }

    inner class StickyHolder(view: View) : BaseViewHolder<HeaderItem>(view) {
        override fun bind(headerItem: HeaderItem) {
            itemView.textView.text = headerItem.title
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

}
