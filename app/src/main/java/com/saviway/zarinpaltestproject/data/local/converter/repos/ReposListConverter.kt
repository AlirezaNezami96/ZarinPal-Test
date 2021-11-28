package com.saviway.zarinpaltestproject.data.local.converter.repos

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData
import com.saviway.zarinpaltestproject.data.model.RepositoryData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class ReposListConverter {

    private val gson = Gson()

    @TypeConverter
    fun repoListToString(repos: List<RepositoryData.Data.User.Repositories.Node>): String {
        return gson.toJson(repos)
    }

    @TypeConverter
    fun stringToRepoList(json: String):List<RepositoryData.Data.User.Repositories.Node>{
        val type = object : TypeToken<List<RepositoryData.Data.User.Repositories.Node>>() {
        }.type

        return gson.fromJson(json,type)
    }
}