package interview.dansmultipro.core

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


inline fun <reified T : Parcelable> Bundle.getSafeParcelable(key : String): T? = when{
    Build.VERSION.SDK_INT>=33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as T?
}
