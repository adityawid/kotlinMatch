package com.devjurnal.footballmatch.screen.detail

import com.devjurnal.footballmatch.models.EventsItem
import com.devjurnal.footballmatch.models.TeamsItem

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showEvent(data: List<EventsItem?>?)
    fun showHomeTeam(data: List<TeamsItem?>?)
    fun showAwayTeam(data: List<TeamsItem?>?)
    fun showError()
}