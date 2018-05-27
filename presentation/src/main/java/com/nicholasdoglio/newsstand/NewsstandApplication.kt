package com.nicholasdoglio.newsstand

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid

class NewsstandApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this);
    }
}