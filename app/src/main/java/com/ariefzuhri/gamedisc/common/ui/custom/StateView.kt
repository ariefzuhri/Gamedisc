package com.ariefzuhri.gamedisc.common.ui.custom

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.ariefzuhri.gamedisc.R
import com.ariefzuhri.gamedisc.common.util.gone
import com.ariefzuhri.gamedisc.databinding.ViewStateBinding

class StateView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = ViewStateBinding
        .inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        .also { addView(it.root) }

    init {
        binding.apply {
            setGravityToCenter()
            initViews(context, attrs)
        }
    }

    private fun setGravityToCenter() {
        gravity = Gravity.CENTER
    }

    private fun ViewStateBinding.initViews(context: Context, attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StateView).also {
            aniIllustration.apply {
                setAnimation(it.getString(R.styleable.StateView_animationFile))

                if (it.hasValue(R.styleable.StateView_animationColor)) {
                    val color = it.getColor(R.styleable.StateView_animationColor, -1)
                    val colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                    addValueCallback(KeyPath("**"), LottieProperty.COLOR_FILTER) {
                        colorFilter
                    }
                }

                layoutParams = layoutParams.apply {
                    width = it.getDimensionPixelSize(
                        R.styleable.StateView_animationWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    height = it.getDimensionPixelSize(
                        R.styleable.StateView_animationHeight,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }

            tvTitle.apply {
                val title = it.getString(R.styleable.StateView_title).orEmpty()
                text = title
                gone(title.isEmpty())
            }

            tvDescription.apply {
                val description = it.getString(R.styleable.StateView_description).orEmpty()
                text = description
                gone(description.isEmpty())
            }

            btnRetry.gone(!it.getBoolean(R.styleable.StateView_isError, false))
        }
        attributes.recycle()
    }

    fun setTitle(title: String?) {
        binding.tvTitle.apply {
            text = title
            gone(title?.isEmpty() == true)
        }
    }

    fun setRetryAction(onClick: () -> Unit) {
        binding.btnRetry.setOnClickListener { onClick() }
    }
}