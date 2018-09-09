package com.devjurnal.footballmatch.screen.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devjurnal.footballmatch.screen.detail.DetailMatchActivity

import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.adapter.FavoriteMatchAdapter
import com.devjurnal.footballmatch.localDB.FavoriteContract
import com.devjurnal.footballmatch.localDB.database
import com.devjurnal.footballmatch.screen.match.MatchPresenter
import com.devjurnal.footballmatch.screen.match.MatchView
import com.devjurnal.footballmatch.models.EventsItem
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteFragment : Fragment(),FavoriteView {
    override fun showTeamList(data: List<FavoriteContract?>?) {
        data.let {
            matchAdapter.refresh(it)
        }
    }

    override fun showLoading() {
        rootView.pb_fav.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        rootView.pb_fav.visibility = View.GONE
    }

    override fun showError() {
        toast("data tidak ditemukan")
    }

    private lateinit var rootView: View
    private var TAG: String = "FAV_MATCH"
    private lateinit var matchAdapter: FavoriteMatchAdapter
    private val matchItems: MutableList<FavoriteContract> = mutableListOf()
    private lateinit var favoritePresenter: FavoritePresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        val bundle: Bundle = Bundle()
        matchAdapter = FavoriteMatchAdapter(matchItems as ArrayList<FavoriteContract>) {
            ctx.startActivity<DetailMatchActivity>(
                    "pastnext" to TAG,
                    "id" to it.idEvent
            )
        }
            rootView.rv_fav.layoutManager = LinearLayoutManager(context)
            rootView.rv_fav.adapter = matchAdapter
            favoritePresenter = FavoritePresenter(this)
        favoritePresenter.getMatch(context!!)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        favoritePresenter.getMatch(context!!)
    }
    override fun onResume() {
        super.onResume()
//        onStart()
    }


}
