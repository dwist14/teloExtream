package com.telo.core.base

interface Presenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}