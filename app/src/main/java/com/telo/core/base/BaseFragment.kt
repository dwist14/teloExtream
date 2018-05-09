package com.telo.core.base

import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import com.telo.core.base.BasePresenter
import com.telo.core.base.BaseView
import com.telo.core.component.AppComponent
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by Dwi Styo Purwoko on 18/04/18.
 */
abstract class BaseFragment : RxFragment(), BaseView {
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private var presenter: BasePresenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityInject()

    }

    protected abstract fun onActivityInject()

    fun getAppcomponent(): AppComponent = App.appComponent

    override fun setPresenter(presenter: BasePresenter<*>) {
        this.presenter = presenter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detachView()
        presenter = null
    }
}