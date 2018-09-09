package com.devjurnal.footballmatch.screen.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.adapter.ScoreAdapter
import com.devjurnal.footballmatch.models.EventsItem
import com.devjurnal.footballmatch.screen.detail.DetailMatchActivity
import kotlinx.android.synthetic.main.fragment_next_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment(), MatchView {
    lateinit var rootView: View
    private var TAG: String = "NEXT_MATCH"
    lateinit var matchPresenter: MatchPresenter
    lateinit var matchAdapter: ScoreAdapter
    private val matchItems: MutableList<EventsItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_next_match, container, false)
        rootView.let { it ->
            matchAdapter = ScoreAdapter(matchItems) {
                ctx.startActivity<DetailMatchActivity>(
                        "pastnext" to TAG,
                        "id" to it.idEvent
                )
            }
            rootView.rv_next.layoutManager = LinearLayoutManager(context)
            rootView.rv_next.adapter = matchAdapter
            matchPresenter = MatchPresenter(this)

            matchPresenter.getMatch(TAG)
        }
        return rootView

    }

    override fun showLoading() {
        rootView.pb_next.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        rootView.pb_next.visibility = View.GONE
    }

    override fun showTeamList(data: List<EventsItem?>?) {
        data.let {
            matchAdapter.refresh(it)
        }
    }

    override fun showError() {
        toast("data tidak ditemukan")
    }


}
