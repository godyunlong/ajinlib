package com.jianbian.baselib.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import kotlin.collections.ArrayList
abstract class MultiTextView<T> : MultiView<T> {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

     override fun actionClicked(view: View, item: T, position: Int) {
         setTextViewStatus(view,item,position,!getViewTag(view))
     }

     override fun actionClicked(views: ArrayList<View>, view: View, item: T, position: Int) {
         actionClicked(view,item,position)
     }

     override fun actionView(item: T, position: Int, selectEd: Boolean): View {
         val textView = TextView(context)
         if (ajItemHaveAverage){
             var itemWith = (widthSize - (intervalLeftRight * ajItemAverageNumber - 1)) / ajItemAverageNumber
             textView.layoutParams = LayoutParams(itemWith,ViewGroup.LayoutParams.WRAP_CONTENT)
         }else{
             textView.layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
         }
         textView.setPadding(ajItemPaddingLeft,ajItemPaddingTop,ajItemPaddingRight,ajItemPaddingBottom)
         textView.isSingleLine = true
         textView.gravity = Gravity.CENTER
         setTextViewStatus(textView,item,position,selectEd)
         actionTextView(textView,item,position)
         return textView
     }

    fun setTextViewStatus(view: View,item: T,position: Int,selectEd: Boolean){
        if (view !is TextView)
            return
        if (selectEd){
            view.setTextColor(ajItemTextSelectColor)
            if (ajItemSelectBackground != null) {
                view.setBackgroundDrawable(ajItemSelectBackground!!)
            }
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ajItemTextSelectSize)
        }else{
            view.setTextColor(ajItemTextSelectNotColor)
            if (ajItemSelecNotBackground != null) {
                view.setBackgroundDrawable(ajItemSelecNotBackground!!)
            }
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ajItemTextSelectNotSize)
        }
        listener?.onMultiChoseView(view,item,position,selectEd,getViewTag(view) == selectEd)
        setTag(view,selectEd)
    }

    abstract fun actionTextView(textView: TextView,item: T,position: Int)
 }