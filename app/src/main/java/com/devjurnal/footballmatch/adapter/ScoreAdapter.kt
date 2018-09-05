package com.devjurnal.footballmatch.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.models.EventsItem
import kotlinx.android.synthetic.main.item_score.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ScoreAdapter(json: List<EventsItem?>?, private val listener: (EventsItem) -> Unit) : RecyclerView.Adapter<ScoreAdapter.MyHolder>() {
    var TAG: String = "SCOREADAPTER"
    var json: List<EventsItem?>?
    lateinit var itemView: View

    // inisialisasi List
    init {
        this.json = json
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_score, parent, false)
        return MyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return json?.size!!
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder?.bind(position, json, listener)
    }

    fun refresh(fill: List<EventsItem?>?) {
        json = fill
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, json: List<EventsItem?>?, listener: (EventsItem) -> Unit) {
            itemView.date_match.text = json?.get(position)?.dateEvent.toString()
            itemView.home_team.text = json?.get(position)?.strHomeTeam.toString()
            itemView.away_team.text = json?.get(position)?.strAwayTeam
            if (json?.get(position)?.intHomeScore != null) {
                itemView.score_team.text = json?.get(position)?.intHomeScore.toString() +
                        " VS " + json?.get(position)?.intAwayScore.toString()
            }

            itemView.onClick {
                listener(json?.get(position)!!)
            }
        }

    }


}