package com.ariefzuhri.gamedisc.common.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseWrapperHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(adapter: RecyclerView.Adapter<*>)
}