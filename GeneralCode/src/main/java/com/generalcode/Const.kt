package com.generalcode

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object Const {

    fun eventCall(activity: Activity, key: String) {
        val keyWithPrefix = "ebmm_$key"
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity)
        val bundle = Bundle()
        bundle.putString(keyWithPrefix, keyWithPrefix)
        mFirebaseAnalytics.logEvent("aaa", bundle)
    }

    fun eventCall(activity: Activity, key: String, value: String) {
        val keyWithPrefix = "ebmm_$key"
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity)
        val bundle = Bundle()
        bundle.putString(keyWithPrefix, value)
        mFirebaseAnalytics.logEvent(keyWithPrefix, bundle)
    }
}
