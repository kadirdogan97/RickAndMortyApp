package com.kadirdogan97.rickandmortyapp.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/12/2020.
 */
fun <T> Observable<Result<T>>.doOnSuccess(
    onSuccess: (T) -> (Unit)
): Observable<Result<T>> {
    return this.doOnNext {
        if (it is Result.Success) {
            onSuccess(it.data)
        }
    }

}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun Any?.runIfNull(block: () -> Unit) {
    if (this == null) block()
}