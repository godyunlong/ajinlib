package com.jianbian.baselib.ui.act

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.gyf.barlibrary.ImmersionBar
import com.jianbian.baselib.R
import com.jianbian.baselib.ui.dialog.LoadingDialog
import com.jianbian.baselib.utils.ActivityController
import com.jianbian.baselib.utils.AppUtil
import com.jianbian.baselib.utils.setOnClick
import kotlinx.android.synthetic.main.layout_base_view.*

abstract class BaseAct :FragmentActivity(){
    private var loadingDialog: LoadingDialog ?=null
    protected var defindPage:Int = 0
    protected var pageSize = 20;
    protected var page = defindPage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityController.instance?.addAct(this)
        setContentView(R.layout.layout_base_view)
        initView()
        setStatusBarMode(statusBarView(),statusBarDurk())
    }

    /**
     * 设置返回按钮
     */
    fun setGoBackView(view: View){
        view.setOnClick(View.OnClickListener {
            finish()
        })
    }

    fun setResetView(view: View){
        view.setOnClick(View.OnClickListener {
            reLoadData()
        })
    }

    /**
     * 设置正文
     */
    fun setContentLayout(@LayoutRes layout:Int){
        setContentLayout(LayoutInflater.from(this).inflate(layout,null))
    }

    fun setContentLayout(view: View){
        content_frame_layout.removeAllViews()
        content_frame_layout.addView(view)
    }

    /**
     * 设置状态栏的view
     */
    fun setTitleLayout(@LayoutRes layout: Int){
        setTitleView(LayoutInflater.from(this).inflate(layout,null))
    }
    fun setTitleView(view: View){
        title_layout_frame_layout.removeAllViews()
        title_layout_frame_layout.addView(view)
    }
    fun getTitleFrameLayout():View{
        return title_layout_frame_layout
    }
    fun getContentLayout():View{
        return content_frame_layout
    }
    fun setStatusBarMode(view: View?, dark: Boolean) {
        val bar = ImmersionBar.with(this)
            .supportActionBar(false)
            .navigationBarEnable(false)
            .transparentBar()
            .statusBarDarkFont(dark)
        if (view != null)
            bar.titleBar(view)
        bar.keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
        bar.init()
    }

    /**
     * 设置底部导航栏
     */
    fun setNavigationLayout(@LayoutRes layout: Int){
        setNavigationView(LayoutInflater.from(this).inflate(layout,null))
    }
    fun setNavigationView(view: View){
        navigation_bar_frame_layout.removeAllViews()
        navigation_bar_frame_layout.addView(view)
    }


    /**
     * 设置预加载
     */
    fun setPreloadingLayout(@LayoutRes layout: Int){
        setPreloadingView(LayoutInflater.from(this).inflate(layout,null))
    }
    fun setPreloadingView(view: View){
        pre_loading_frame_layout.removeAllViews()
        pre_loading_frame_layout.addView(view)
    }

    /**
     * 设置加载失败的时候
     */
    fun setErrorLayout(@LayoutRes layout: Int){
        setErrorView(LayoutInflater.from(this).inflate(layout,null))
    }
    fun setErrorView(view: View){
        err_loading_frame_layout.removeAllViews()
        err_loading_frame_layout.addView(view)
    }

    /**
     * 显示预加载
     */
    open fun showPreLoading(){
        Handler(Looper.getMainLooper()).run {
            pre_loading_frame_layout.visibility = View.VISIBLE
            err_loading_frame_layout.visibility = View.GONE
            content_frame_layout.visibility = View.GONE
        }
    }

    /**
     * 加载失败
     */
    open fun showError(){
        Handler(Looper.getMainLooper()).run {
            pre_loading_frame_layout.visibility = View.GONE
            err_loading_frame_layout.visibility = View.VISIBLE
            content_frame_layout.visibility = View.GONE
        }
    }

    /**
     * 加载完成
     */
    open fun loadSuc(){
        Handler(Looper.getMainLooper()).run {
            pre_loading_frame_layout.visibility = View.GONE
            err_loading_frame_layout.visibility = View.GONE
            content_frame_layout.visibility = View.VISIBLE
        }
    }

    open fun showLoading(str: String?) {
        if (loadingDialog == null)
            loadingDialog= LoadingDialog(this)
        loadingDialog!!.show()
        loadingDialog?.setText(str)
    }

    open fun disLoading() {
        loadingDialog?.dismiss()
    }

    open fun getData(page:Int,pageSize:Int){}
    open fun reLoadData(){}


    abstract fun initView()
    abstract fun statusBarView():View?
    open fun statusBarDurk():Boolean = true

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.instance?.removeAct(this)
    }
}