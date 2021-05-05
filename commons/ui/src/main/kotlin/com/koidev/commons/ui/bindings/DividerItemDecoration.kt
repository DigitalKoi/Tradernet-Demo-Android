package com.koidev.commons.ui.bindings

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView


class DividerItemDecoration(val context: Context, private val spacingPx: Float) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    init {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        divider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        val marginStartPx = TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            spacingPx,
//            context.resources.displayMetrics
//        ).toInt()
        val left = parent.paddingLeft + 80
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        // Ignore the first element because it's a Header
        for (i in 1 until childCount) {
            divider?.also { divider ->
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
