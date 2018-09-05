package com.devjurnal.footballmatch.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.adapter.ScoreAdapter
import com.devjurnal.footballmatch.models.RestEvents
import com.devjurnal.footballmatch.network.ApiConfig
import com.devjurnal.footballmatch.network.EventInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_prev_match.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PrevMatchFragment : Fragment() {

    lateinit var rootView : View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_prev_match, container, false)
        rootView.rv_prev.layoutManager = LinearLayoutManager(context)
        getSchedule()

        return rootView
    }

    private fun getSchedule() {
        val nextMatch: Observable<RestEvents> = ApiConfig.retrofits
                .create<EventInterface>(EventInterface::class.java)
                .getPastMatch()

        nextMatch.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: RestEvents? ->
                    Log.d("PAST", t.toString())

                    rootView.rv_prev.adapter = ScoreAdapter(t?.events)
                }) {
                    error ->
                    Log.e("error PAST" , error.localizedMessage)
                }
    }


}
