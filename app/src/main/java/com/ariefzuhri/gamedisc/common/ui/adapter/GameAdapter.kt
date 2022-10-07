package com.ariefzuhri.gamedisc.common.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gamedisc.common.util.fillMaxWidth
import com.ariefzuhri.gamedisc.common.util.load
import com.ariefzuhri.gamedisc.databinding.ItemGameBinding
import com.ariefzuhri.gamedisc.domain.enums.ViewOrientation
import com.ariefzuhri.gamedisc.domain.model.Game

class GameAdapter(
    private val orientation: ViewOrientation,
) : PagingDataAdapter<Game, GameAdapter.GameViewHolder>(GameComparator) {

    private var listener: EventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        game?.let { holder.bind(it) }
    }

    object GameComparator : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }

    inner class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.apply {
                adjustOrientation()
                initView(game)
                initClickListeners(game)
            }
        }

        private fun ItemGameBinding.adjustOrientation() {
            if (orientation == ViewOrientation.VERTICAL) {
                cvContainer.fillMaxWidth()
                imgPoster.fillMaxWidth()
            }
        }

        private fun ItemGameBinding.initView(game: Game) {
            tvTitle.text = game.title
            tvGenres.text = game.genres
            tvRating.text = game.rating.toString()
            imgPoster.load(game.posterUrl)
        }

        private fun ItemGameBinding.initClickListeners(game: Game) {
            root.setOnClickListener {
                listener?.onItemClick(game)
            }
        }
    }

    fun setEventListener(listener: EventListener?) {
        this.listener = listener
    }

    interface EventListener {

        fun onItemClick(game: Game)
    }
}
