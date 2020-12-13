package com.example.weatherforecast.ui.locations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorLong
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R

class MyItemDecorator(
    context: Context,
    @ColorLong private val color: Int = ContextCompat.getColor(context, R.color.grey),
    private val dividerHeight: Int = context.resources.getDimensionPixelSize(R.dimen.divider_height)
) : RecyclerView.ItemDecoration() {

    private val dividerPaint = Paint()

    init {
        dividerPaint.color = color
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = dividerHeight
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val childCount = parent.childCount
        val right = parent.width

        for (i in 0 until childCount - 1) {
            val item = parent.getChildAt(i)

            val top = item.bottom
            val bottom = item.bottom + dividerHeight
            c.drawRect(0F, top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
        }
    }
}