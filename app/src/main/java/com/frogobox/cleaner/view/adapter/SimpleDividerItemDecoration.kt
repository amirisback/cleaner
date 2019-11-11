package com.frogobox.cleaner.view.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.R

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * Cleaner
 * Copyright (C) 11/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.cleaner.view.adapter
 *
 */
class SimpleDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private var mDivider: Drawable? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDivider = context.resources.getDrawable(R.drawable.line_divide, context.theme)
        } else {
            mDivider = context.resources.getDrawable(R.drawable.line_divide)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }
}