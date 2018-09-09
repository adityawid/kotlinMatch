package com.devjurnal.footballmatch.screen.favorite

import com.devjurnal.footballmatch.localDB.FavoriteContract

interface FavoriteView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<FavoriteContract?>?)
    fun showError()
}