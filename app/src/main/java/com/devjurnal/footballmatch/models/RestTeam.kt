package com.devjurnal.footballmatch.models

import com.google.gson.annotations.SerializedName

data class RestTeam(

	@field:SerializedName("teams")
	val teams: List<TeamsItem?>? = null
)