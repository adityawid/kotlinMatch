package com.devjurnal.footballmatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.devjurnal.footballmatch.models.EventsItem
import com.devjurnal.footballmatch.models.RestTeam
import com.devjurnal.footballmatch.network.ApiConfig
import com.devjurnal.footballmatch.network.EventInterface
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_match.*

class DetailMatchActivity : AppCompatActivity() {
    private var matchDetail :EventsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        var bundle: Bundle = intent.getBundleExtra("bundle")
        var pastNext = intent.getStringExtra("pastnext")

        matchDetail = bundle.getParcelable<EventsItem?>("event")

        DPMA_txt_date.text = matchDetail?.strDate
        DPMA_txt_team_home.text = matchDetail?.strHomeTeam
        DPMA_txt_team_away.text = matchDetail?.strAwayTeam
        getBedgeTeam(matchDetail?.idHomeTeam.toString() , "HOME")
        getBedgeTeam(matchDetail?.idAwayTeam.toString() , "AWAY")

        if (pastNext == "PAST"){
            getPastMatch()
        }

        Log.d("DETAIL", bundle.getParcelable<EventsItem?>("event").toString())
    }

    private fun getPastMatch() {

        DPMA_txt_score.text = matchDetail?.intHomeScore.toString() +" : " + matchDetail?.intAwayScore.toString()
        DPMA_txt_form_home.text = matchDetail?.strHomeFormation
        DPMA_txt_form_away.text = matchDetail?.strAwayFormation
        DPMA_txt_goal_home.text = matchDetail?.strHomeGoalDetails?.replace(";","\n")
        DPMA_txt_goal_away.text = matchDetail?.strAwayGoalDetails?.replace(";","\n")

        if(matchDetail?.strAwayRedCards != ""){
            DPMA_txt_red_away.text = matchDetail?.strAwayRedCards?.replace(";","\n")
        }else {
            DPMA_txt_red_away.text = "0"
        }

        if(matchDetail?.strHomeRedCards != ""){
            DPMA_txt_red_home.text = matchDetail?.strHomeRedCards?.replace(";","\n")
        }else {
            DPMA_txt_red_home.text = "0"
        }

        if(matchDetail?.strAwayYellowCards != ""){
            DPMA_txt_yellow_away.text = matchDetail?.strAwayYellowCards?.replace(";","\n")
        }else {
            DPMA_txt_yellow_away.text = "0"
        }

        if(matchDetail?.strHomeYellowCards != ""){
            DPMA_txt_yellow_home.text = matchDetail?.strHomeYellowCards?.replace(";","\n")
        }else {
            DPMA_txt_yellow_home.text = "0"
        }


        DPMA_txt_gk_away.text = matchDetail?.strAwayLineupGoalkeeper
        DPMA_txt_gk_home.text = matchDetail?.strHomeLineupGoalkeeper

        DPMA_txt_def_home.text = matchDetail?.strHomeLineupDefense?.replace(";","\n")
        DPMA_txt_def_away.text = matchDetail?.strAwayLineupDefense?.replace(";","\n")

        DPMA_txt_mid_home.text = matchDetail?.strHomeLineupMidfield?.replace(";","\n")
        DPMA_txt_mid_away.text = matchDetail?.strAwayLineupMidfield?.replace(";","\n")
        DPMA_txt_fwd_home.text = matchDetail?.strHomeLineupForward?.replace(";","\n")
        DPMA_txt_fwd_away.text = matchDetail?.strAwayLineupForward?.replace(";","\n")

        DPMA_txt_sub_home.text = matchDetail?.strHomeLineupSubstitutes?.replace(";","\n")
        DPMA_txt_sub_away.text = matchDetail?.strAwayLineupSubstitutes?.replace(";","\n")


    }

    private fun getBedgeTeam(id: String, homeAway: String) {
        val nextMatch: Observable<RestTeam> = ApiConfig.retrofits
                .create<EventInterface>(EventInterface::class.java)
                .getImage(id)

        nextMatch.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    Log.d("HOME", t.teams?.get(0)?.strTeamBadge.toString())
                    when (homeAway) {
                        "AWAY" -> Picasso.with(applicationContext).load(t.teams?.get(0)?.strTeamBadge.toString())
                                .into(DPMA_img_away)
                        "HOME" -> Picasso.with(applicationContext).load(t.teams?.get(0)?.strTeamBadge.toString())
                                .into(DPMA_img_home)
                    }

                }) {
                    error ->
                    Log.e("error HOME" , error.localizedMessage)
                }
    }
}
