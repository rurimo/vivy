package com.benallouch.vivy.view.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPager(
    private val recyclerView: RecyclerView,
    private val loadMore: (String) -> Unit
) : RecyclerView.OnScrollListener() {

    var isLoading = false
    var lastKey: String? = null

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        val layoutManager = recyclerView.layoutManager
        layoutManager?.let {
            val totalItemCount = it.itemCount
            val lastVisibleItemPosition = when (layoutManager) {
                is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
                else -> return
            }

            if (isLoading) return

            if (!isLoading && ((lastVisibleItemPosition + 4) >= totalItemCount)) {
                isLoading = true
                lastKey?.let { key -> loadMore(key) }
            }
        }
    }

    fun setDataLoaded() {
        isLoading = false
    }
}