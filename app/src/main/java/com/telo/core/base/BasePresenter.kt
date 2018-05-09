package com.telo.core.base

import com.telo.core.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BasePresenter<V : BaseView> constructor(private var disposable: CompositeDisposable, var scheduler: SchedulerProvider) : Presenter<V> {

    private var weakReference: WeakReference<V>? = null

    override fun attachView(view: V) {
        if (!isViewAttached) {
            weakReference = WeakReference(view)
            view.setPresenter(this)
        }
    }

    override fun detachView() {
        println("detachView")
        weakReference?.clear()
        weakReference = null
        disposable.clear()

    }

    val view: V?
        get() = weakReference?.get()

    private val isViewAttached: Boolean
        get() = weakReference != null && weakReference!!.get() != null


}
