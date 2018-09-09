package com.devjurnal.footballmatch.screen.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.R.drawable.ic_add_to_favorites
import com.devjurnal.footballmatch.R.drawable.ic_added_to_favorites
import com.devjurnal.footballmatch.localDB.FavoriteContract
import com.devjurnal.footballmatch.localDB.database
import com.devjurnal.footballmatch.models.EventsItem
import com.devjurnal.footballmatch.models.TeamsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailMatchActivity : AppCompatActivity(), DetailView {
    private var matchDetail: EventsItem? = null
    lateinit var detailPresenter: DetailPresenter

    private lateinit var pastNext: String
    private lateinit var id: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        pastNext = intent.getStringExtra("pastnext")
        id = intent.getStringExtra("id")
        getFavouriteState()

        detailPresenter = DetailPresenter(this)
        detailPresenter.getEvent(id)
    }

    private fun getFavouriteState() {
        try {
            database.use {
                val results = select(FavoriteContract.TABLE_FAVORITE)
                        .whereArgs("(ID_EVENT = {id})", "id" to id)
                val favorites = results.parseList(classParser<FavoriteContract>())
                if (!favorites.isEmpty()) isFavorite = true
                Log.d("fav", "onCreate$isFavorite")
            }

        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun setFavoriteState() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        menuItem = menu
        setFavoriteState()
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {
                if (isFavorite) removeFavorite() else saveFavorite()

                Log.d("fav", "before$isFavorite")
                isFavorite = !isFavorite
                Log.d("fav", "after$isFavorite")
                setFavoriteState()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun removeFavorite() {
        try {
            database.use {
                delete(FavoriteContract.TABLE_FAVORITE, "(ID_EVENT = {id})",
                        "id" to id)
            }
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun saveFavorite() {
        try {

            database.use {
                insert(FavoriteContract.TABLE_FAVORITE,
                        FavoriteContract.ID_EVENT to matchDetail?.idEvent,
                        FavoriteContract.DATE_EVENT to matchDetail?.dateEvent,
                        FavoriteContract.HOME_TEAM to matchDetail?.strHomeTeam,
                        FavoriteContract.AWAY_TEAM to matchDetail?.strAwayTeam,
                        FavoriteContract.HOME_ID to matchDetail?.idHomeTeam,
                        FavoriteContract.AWAY_ID to matchDetail?.idAwayTeam,
                        FavoriteContract.TEAM_HOME_SCORE to matchDetail?.intHomeScore.toString(),
                        FavoriteContract.TEAM_AWAY_SCORE to matchDetail?.intAwayScore.toString()
                )
            }


        } catch (e: SQLiteConstraintException) {
        }

    }

    override fun showLoading() {
        scr_detail.visibility = View.GONE
        pb_detail.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        pb_detail.visibility = View.GONE
        scr_detail.visibility = View.VISIBLE
    }

    override fun showEvent(data: List<EventsItem?>?) {
        matchDetail = data?.get(0)
        DPMA_txt_date.text = data?.get(0)?.strDate
        DPMA_txt_team_home.text = data?.get(0)?.strHomeTeam
        DPMA_txt_team_away.text = data?.get(0)?.strAwayTeam
        detailPresenter.getBadgeTeam(data?.get(0)?.idHomeTeam.toString(), "HOME")
        detailPresenter.getBadgeTeam(data?.get(0)?.idAwayTeam.toString(), "AWAY")


        if (data?.get(0)?.intHomeScore != null) {
            DPMA_txt_score.text = data?.get(0)?.intHomeScore.toString() + " : " + data?.get(0)?.intAwayScore
        } else {
            DPMA_txt_score.text = getString(R.string.vs)
        }

        DPMA_txt_form_home.text = data?.get(0)?.strHomeFormation
        DPMA_txt_form_away.text = data?.get(0)?.strAwayFormation
        DPMA_txt_goal_home.text = data?.get(0)?.strHomeGoalDetails?.replace(";", "\n")
        DPMA_txt_goal_away.text = data?.get(0)?.strAwayGoalDetails?.replace(";", "\n")

        if (data?.get(0)?.strAwayRedCards != "") {
            DPMA_txt_red_away.text = data?.get(0)?.strAwayRedCards?.replace(";", "\n")
        } else {
            DPMA_txt_red_away.text = "0"
        }

        if (data?.get(0)?.strHomeRedCards != "") {
            DPMA_txt_red_home.text = data?.get(0)?.strHomeRedCards?.replace(";", "\n")
        } else {
            DPMA_txt_red_home.text = "0"
        }

        if (data?.get(0)?.strAwayYellowCards != "") {
            DPMA_txt_yellow_away.text = data?.get(0)?.strAwayYellowCards?.replace(";", "\n")
        } else {
            DPMA_txt_yellow_away.text = "0"
        }

        if (data?.get(0)?.strHomeYellowCards != "") {
            DPMA_txt_yellow_home.text = data?.get(0)?.strHomeYellowCards?.replace(";", "\n")
        } else {
            DPMA_txt_yellow_home.text = "0"
        }


        DPMA_txt_gk_away.text = data?.get(0)?.strAwayLineupGoalkeeper
        DPMA_txt_gk_home.text = data?.get(0)?.strHomeLineupGoalkeeper

        DPMA_txt_def_home.text = data?.get(0)?.strHomeLineupDefense?.replace(";", "\n")
        DPMA_txt_def_away.text = data?.get(0)?.strAwayLineupDefense?.replace(";", "\n")

        DPMA_txt_mid_home.text = data?.get(0)?.strHomeLineupMidfield?.replace(";", "\n")
        DPMA_txt_mid_away.text = data?.get(0)?.strAwayLineupMidfield?.replace(";", "\n")
        DPMA_txt_fwd_home.text = data?.get(0)?.strHomeLineupForward?.replace(";", "\n")
        DPMA_txt_fwd_away.text = data?.get(0)?.strAwayLineupForward?.replace(";", "\n")

        DPMA_txt_sub_home.text = data?.get(0)?.strHomeLineupSubstitutes?.replace(";", "\n")
        DPMA_txt_sub_away.text = data?.get(0)?.strAwayLineupSubstitutes?.replace(";", "\n")
    }

    override fun showHomeTeam(data: List<TeamsItem?>?) {
        Picasso.with(applicationContext).load(data?.get(0)?.strTeamBadge.toString())
                .into(DPMA_img_home)
    }

    override fun showAwayTeam(data: List<TeamsItem?>?) {
        Picasso.with(applicationContext).load(data?.get(0)?.strTeamBadge.toString())
                .into(DPMA_img_away)
    }

    override fun showError() {
        toast("data tidak ditemukan")
    }
}
