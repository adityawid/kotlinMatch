package com.devjurnal.footballmatch.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devjurnal.footballmatch.DetailMatchActivity
import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.adapter.ScoreAdapter
import com.devjurnal.footballmatch.models.EventsItem
import kotlinx.android.synthetic.main.fragment_prev_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 *
 */
class PrevMatchFragment : Fragment(), MatchView {
    lateinit var rootView: View
    var TAG: String = "PAST_MATCH"
    lateinit var matchPresenter: MatchPresenter
    lateinit var matchAdapter: ScoreAdapter
    private val matchItems: MutableList<EventsItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_prev_match, container, false)
        val bundle: Bundle = Bundle()
        rootView.let { it ->
            matchAdapter = ScoreAdapter(matchItems) {
                bundle.putParcelable("event", it)
                ctx.startActivity<DetailMatchActivity>(
                        "bundle" to bundle, "pastnext" to TAG
                )
            }
            rootView.rv_prev.layoutManager = LinearLayoutManager(context)
            rootView.rv_prev.adapter = matchAdapter
            matchPresenter = MatchPresenter(this)

            matchPresenter.getMatch(TAG)
        }

        return rootView
    }

    override fun showLoading() {
        rootView.pb_past.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        rootView.pb_past.visibility = View.GONE
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
