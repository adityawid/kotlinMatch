package com.devjurnal.footballmatch.screen.detail

import android.util.Log
import com.devjurnal.footballmatch.models.RestEvents
import com.devjurnal.footballmatch.models.RestTeam
import com.devjurnal.footballmatch.network.ApiConfig
import com.devjurnal.footballmatch.network.EventInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailPresenter(private val view: DetailView) {
    fun getEvent(idEvent: String) {
        view.showLoading()
        val nextMatch: Observable<RestEvents> = ApiConfig.retrofits
                .create<EventInterface>(EventInterface::class.java)
                .getEvent(idEvent)

        nextMatch.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: RestEvents? ->
                    Log.d("Event", t.toString())
                    view.hideLoading()

                    view.showEvent(t?.events)
                }) { error ->
                    view.hideLoading()

                    Log.e("error Event", error.localizedMessage)
                }
    }

    fun getBadgeTeam(idTeam: String, homeAway: String) {
        view.showLoading()
        val nextMatch: Observable<RestTeam> = ApiConfig.retrofits
                .create<EventInterface>(EventInterface::class.java)
                .getImage(idTeam)

        nextMatch.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->

                    Log.d("HOME", t.teams?.get(0)?.strTeamBadge.toString())
                    when (homeAway) {
                        "AWAY" -> view.showAwayTeam(t.teams)
                        "HOME" -> view.showHomeTeam(t.teams)
                    }

                    view.hideLoading()
                }) { error ->
                    Log.e("error HOME", error.localizedMessage)
                }

    }
}