package com.devjurnal.footballmatch.models

import com.google.gson.annotations.SerializedName

data class RestEvents(

	@field:SerializedName("events")
	val events: List<EventsItem?>? = null
)