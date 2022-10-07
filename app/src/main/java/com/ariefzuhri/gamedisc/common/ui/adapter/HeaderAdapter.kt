package com.ariefzuhri.gamedisc.common.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gamedisc.common.base.BaseViewHolder
import com.ariefzuhri.gamedisc.common.util.setDrawableStart
import com.ariefzuhri.gamedisc.databinding.ItemHeaderBinding

class HeaderAdapter(
    title: String, iconRes: Int,
) : RecyclerView.Adapter<BaseViewHolder<HeaderAdapter.Header>>() {

    private var header: Header = Header(title, iconRes)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<Header> {
        val binding = ItemHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Header>, position: Int) {
        holder.bind(header)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class HeaderViewHolder(
        private val binding: ItemHeaderBinding,
    ) : BaseViewHolder<Header>(binding) {

        override fun bind(item: Header) {
            binding.tvTitle.apply {
                text = item.title
                setDrawableStart(item.iconRes)
            }
        }
    }

    data class Header(
        val title: String,
        val iconRes: Int,
    )
}