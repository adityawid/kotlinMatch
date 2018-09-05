package com.devjurnal.footballmatch.match

import android.util.Log
import com.devjurnal.footballmatch.models.RestEvents
import com.devjurnal.footballmatch.network.ApiConfig
import com.devjurnal.footballmatch.network.EventInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchPresenter(private val view: MatchView) {

    fun getMatch(pastNext : String?){
        view.showLoading()
        when(pastNext) {
            "NEXT_MATCH" -> {
                val nextMatch: Observable<RestEvents> = ApiConfig.retrofits
                        .create<EventInterface>(EventInterface::class.java)
                        .getNextMatch()

                nextMatch.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            t: RestEvents? ->
                            Log.d("NEXT", t.toString())
                            view.hideLoading()

//                            rootView.rv_prev.adapter = ScoreAdapter(t?.events)
                            view.showTeamList(t?.events)
                        }) {
                            error ->
                            view.hideLoading()

                            Log.e("error NEXT" , error.localizedMessage)
                        }
            }

            "PAST_MATCH" ->{
                val nextMatch: Observable<RestEvents> = ApiConfig.retrofits
                        .create<EventInterface>(EventInterface::class.java)
                        .getPastMatch()

                nextMatch.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ t: RestEvents? ->
                            Log.d("PAST", t.toString())
                            view.hideLoading()

//                            rootView.rv_prev.adapter = ScoreAdapter(t?.events)
                            view.showTeamList(t?.events)
                        }) { error ->
                            view.hideLoading()
                            Log.e("error PAST", error.localizedMessage)
                        }
            }
        }
    }
}