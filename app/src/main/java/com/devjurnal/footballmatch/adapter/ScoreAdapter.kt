package com.devjurnal.footballmatch.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devjurnal.footballmatch.DetailMatchActivity
import com.devjurnal.footballmatch.R
import com.devjurnal.footballmatch.models.EventsItem
import kotlinx.android.synthetic.main.item_score.view.*

class ScoreAdapter(json: List<EventsItem?>?): RecyclerView.Adapter<ScoreAdapter.MyHolder>() {
    var TAG: String = "KATEGORIADAPTER"
    //    var json : List<Mst_Category>
    var json: List<EventsItem?>?
    lateinit var itemView: View
//    lateinit var context: Context

    // inisialisasi List
    init {
//        this.context = context
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
        holder?.bind(position, json)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(position: Int, json: List<EventsItem?>?) {
            itemView.date_match.text = json?.get(position)?.dateEvent.toString()
            itemView.home_team.text = json?.get(position)?.strHomeTeam.toString()
            itemView.away_team.text = json?.get(position)?.strAwayTeam
            if (json?.get(position)?.intHomeScore != null){
                itemView.score_team.text = json?.get(position)?.intHomeScore.toString() +
                        " VS " + json?.get(position)?.intAwayScore.toString()
            }

            itemView.setOnClickListener {
                val bundle: Bundle = Bundle()
                val event : EventsItem? = json?.get(position)
                bundle.putParcelable("event",event)

                if (json?.get(position)?.intHomeScore != null){
                    val i: Intent = Intent(itemView.context, DetailMatchActivity::class.java)
                    i.putExtra("bundle", bundle)
                    i.putExtra("pastnext", "PAST")
                    itemView.context.startActivity(i)

                }else{
                    val i: Intent = Intent(itemView.context, DetailMatchActivity::class.java)
                    i.putExtra("homeTeam", json?.get(position)?.idHomeTeam)
                    i.putExtra("awayTeam", json?.get(position)?.idAwayTeam)
                    i.putExtra("bundle", bundle)
                    i.putExtra("pastnext", "NEXT")
                    itemView.context.startActivity(i)
                }


            }
        }

    }


}