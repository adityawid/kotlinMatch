package com.devjurnal.footballmatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devjurnal.footballmatch.adapter.ScoreAdapter
import com.devjurnal.footballmatch.models.RestEvents
import com.devjurnal.footballmatch.models.RestTeam
import com.devjurnal.footballmatch.network.ApiConfig
import com.devjurnal.footballmatch.network.EventInterface
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_next_match.*

class DetailNextMatchActivity : AppCompatActivity() {
//    var bundle: Bundle = intent.getBundleExtra("bundle")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_next_match)

        getHomeTeam(intent.getStringExtra("homeTeam").toString())
        getAwayTeam(intent.getStringExtra("awayTeam").toString())
    }

    private fun getAwayTeam(id: String) {
        val nextMatch: Observable<RestTeam> = ApiConfig.retrofits
                .create<EventInterface>(EventInterface::class.java)
                .getImage(id)

        nextMatch.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    Log.d("HOME", t.teams?.get(0)?.strTeamBadge.toString())
                    Picasso.with(applicationContext).load(t.teams?.get(0)?.strTeamBadge.toString())
                            .into(DNMA_image_home)

                }) {
                    error ->
                    Log.e("error HOME" , error.localizedMessage)
                }
    }

    private fun getHomeTeam(id: String) {
        val nextMatch: Observable<RestTeam> = ApiConfig.retrofits
                .create<EventInterface>(EventInterface::class.java)
                .getImage(id)

        nextMatch.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    Log.d("HOME", t.teams?.get(0)?.strTeamBadge.toString())
                    Picasso.with(applicationContext).load(t.teams?.get(0)?.strTeamBadge.toString())
                            .into(DNMA_image_home)

                }) {
                    error ->
                    Log.e("error HOME" , error.localizedMessage)
                }

    }
}
