package com.ariefzuhri.gamedisc.common.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gamedisc.common.base.BaseWrapperHolder
import com.ariefzuhri.gamedisc.databinding.LayoutHorizontalWrapperBinding

class HorizontalWrapperAdapter(
    private val adapter: RecyclerView.Adapter<*>,
) : RecyclerView.Adapter<BaseWrapperHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWrapperHolder {
        val binding = LayoutHorizontalWrapperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).apply {
            root.apply {
                layoutManager = LinearLayoutManager(
                    parent.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setHasFixedSize(true)
            }
        }
        return HorizontalWrapperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseWrapperHolder, position: Int) {
        holder.bind(adapter)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class HorizontalWrapperViewHolder(
        private val binding: LayoutHorizontalWrapperBinding,
    ) : BaseWrapperHolder(binding) {

        override fun bind(adapter: RecyclerView.Adapter<*>) {
            binding.root.adapter = adapter
        }
    }
}