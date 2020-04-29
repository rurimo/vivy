package com.benallouch.vivy.model

import com.benallouch.vivy.view.adapter.ListItem

data class HeaderItem(val title: String) : ListItem {
    override val itemType: String
        get() = title
}