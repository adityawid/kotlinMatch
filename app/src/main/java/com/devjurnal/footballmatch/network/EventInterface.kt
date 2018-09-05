package com.devjurnal.footballmatch.network

import com.devjurnal.footballmatch.models.RestEvents
import com.devjurnal.footballmatch.models.RestTeam
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface EventInterface {

    @GET("json/1/eventsnextleague.php?id=4328")
    fun getNextMatch(): Observable<RestEvents>

    @GET("json/1/eventspastleague.php?id=4328")
    fun getPastMatch(): Observable<RestEvents>


    @GET("json/1/lookupteam.php")
    fun getImage(@Query("id") id : String ): Observable<RestTeam>
}