package com.example.clustering.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.clustering.R

object BitmapGenerator {
    private val labels = mutableMapOf<String, Bitmap>()


    fun generateBitmap(context: Context, value: String): Bitmap {
        return labels[value] ?: createLabel(context, value).also { labels[value] = it }
    }

    private fun createLabel(context: Context, value: String): Bitmap {
        return BitmapLabelGenerator().apply {
            setContentView(
                TextView(context).apply {
                    val paddingStart =
                        resources.getDimensionPixelSize(R.dimen.heatmap_label_padding_start)
                    val paddingTop =
                        resources.getDimensionPixelSize(R.dimen.heatmap_label_padding_top)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(paddingStart, paddingTop, paddingStart, paddingTop)
                    text = value
                    setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.label_color,
                            null
                        )
                    )
                    background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.bg_heatmap_label,
                        null
                    )
                },
            )
        }.makeIcon()
    }

    private class BitmapLabelGenerator {

        private lateinit var contentView: View

        fun setContentView(view: View) {
            contentView = view
        }

        fun makeIcon(): Bitmap {
            val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            contentView.measure(measureSpec, measureSpec)

            val measuredWidth = contentView.measuredWidth
            val measuredHeight = contentView.measuredHeight

            contentView.layout(0, 0, measuredWidth, measuredHeight)

            return Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
                .apply {
                    val canvas = Canvas(this)
                    contentView.draw(canvas)
                }
        }
    }
}