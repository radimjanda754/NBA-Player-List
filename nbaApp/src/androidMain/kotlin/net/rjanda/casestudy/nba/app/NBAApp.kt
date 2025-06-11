package net.rjanda.casestudy.nba.app

import android.app.Application
import net.rjanda.casestudy.nba.di.initKoin

class NBAApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}
