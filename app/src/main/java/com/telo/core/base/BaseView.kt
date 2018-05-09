package com.telo.core.base

interface BaseView {
    fun onLoading(loading: Boolean)
    fun onMessage(msg: String)
    fun setPresenter(presenter: BasePresenter<*>)

}