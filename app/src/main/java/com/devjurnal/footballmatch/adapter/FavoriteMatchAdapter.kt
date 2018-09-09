package com.devjurnal.footballmatch.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.localDB.FavoriteContract
import kotlinx.android.synthetic.main.item_score.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteMatchAdapter(json: ArrayList<FavoriteContract>, private val listener: (FavoriteContract) -> Unit) : RecyclerView.Adapter<FavoriteMatchAdapter.MyHolder>() {
    var json: List<FavoriteContract?>?
    lateinit var itemView: View

    // inisialisasi List
    init {
        this.json = json
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return MyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return json?.size!!
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(position, json, listener)
    }

    fun refresh(fill: List<FavoriteContract?>?) {
        json = fill
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int, json: List<FavoriteContract?>?, listener: (FavoriteContract) -> Unit) {
            itemView.date_match.text = json?.get(position)?.dateEvent
            itemView.home_team.text = json?.get(position)?.homeTeam
            itemView.away_team.text = json?.get(position)?.awayTeam

            if (json?.get(position)?.homeScore.equals("null")) {
                itemView.score_team.text = " VS "
            } else {
                itemView.score_team.text = json?.get(position)?.homeScore.toString() +
                        " VS " + json?.get(position)?.awayScore.toString()
            }

            itemView.onClick {
                listener(json?.get(position)!!)
            }
        }

    }


}