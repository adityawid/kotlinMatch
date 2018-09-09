package com.devjurnal.footballmatch.screen.favorite

import android.content.Context
import com.devjurnal.footballmatch.localDB.FavoriteContract
import com.devjurnal.footballmatch.localDB.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritePresenter(private val view: FavoriteView) {

    fun getMatch(context: Context) {
        view.showLoading()
        context?.database?.use {
            val result = select(FavoriteContract.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteContract>())
            view.showTeamList(favorite)
            view.hideLoading()
        }
    }
}