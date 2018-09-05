package com.devjurnal.footballmatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devjurnal.footballmatch.models.EventsItem
import kotlinx.android.synthetic.main.activity_detail_past_match.*
class DetailPastMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_past_match)
        var bundle: Bundle = intent.getBundleExtra("bundle")

        val matchDetail = bundle.getParcelable<EventsItem?>("event")
        DPMA_txt_date.text = matchDetail?.strDate
        DPMA_txt_team_home.text = matchDetail?.strHomeTeam
        DPMA_txt_team_away.text = matchDetail?.strAwayTeam
        DPMA_txt_form_home.text = matchDetail?.strHomeFormation
        DPMA_txt_form_away.text = matchDetail?.strAwayFormation
        DPMA_txt_goal_home.text = matchDetail?.strHomeGoalDetails
        DPMA_txt_goal_away.text = matchDetail?.strAwayGoalDetails
//        DPMA_txt_shots_home.text = matchDetail?.intHomeShots.toString()
//        DPMA_txt_shots_away.text = matchDetail?.intAwayShots.toString()

        if(matchDetail?.strAwayRedCards != ""){
            DPMA_txt_red_away.text = matchDetail?.strAwayRedCards
        }else {
            DPMA_txt_red_away.text = "0"
        }

        if(matchDetail?.strHomeRedCards != ""){
            DPMA_txt_red_home.text = matchDetail?.strHomeRedCards
        }else {
            DPMA_txt_red_home.text = "0"
        }

        if(matchDetail?.strAwayYellowCards != ""){
            DPMA_txt_yellow_away.text = matchDetail?.strAwayYellowCards
        }else {
            DPMA_txt_yellow_away.text = "0"
        }

        if(matchDetail?.strHomeYellowCards != ""){
            DPMA_txt_yellow_home.text = matchDetail?.strHomeYellowCards
        }else {
            DPMA_txt_yellow_home.text = "0"
        }


        DPMA_txt_gk_away.text = matchDetail?.strAwayLineupGoalkeeper
        DPMA_txt_gk_home.text = matchDetail?.strHomeLineupGoalkeeper




        Log.d("DETAIL", bundle.getParcelable<EventsItem?>("event").toString())




    }
}
