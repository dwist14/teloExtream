package com.telo.core.base

import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.telo.core.base.App
import com.telo.core.base.BasePresenter
import com.telo.core.base.BaseView
import com.telo.core.component.AppComponent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity: RxAppCompatActivity(), BaseView {

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

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        presenter = null
    }

    protected fun initToolbar(toolbar: Toolbar, paramStrTitle: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = paramStrTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
