package com.devjurnal.footballmatch.match

import com.devjurnal.footballmatch.models.EventsItem

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<EventsItem?>?)
    fun showError()
}