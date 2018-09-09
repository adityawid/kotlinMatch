package com.devjurnal.footballmatch.localDB

data class FavoriteContract(
        val id: Long?,
        val idEvent: String,
        val dateEvent: String,
        val homeTeam: String,
        val awayTeam: String,
        val idHomeTeam: String,
        val idAwayTeam: String,
        val homeScore: String,
        val awayScore: String) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_ID: String = "HOME_ID"
        const val AWAY_ID: String = "AWAY_ID"
        const val TEAM_HOME_SCORE: String = "team_home_score"
        const val TEAM_AWAY_SCORE: String = "team_away_score"
    }
}