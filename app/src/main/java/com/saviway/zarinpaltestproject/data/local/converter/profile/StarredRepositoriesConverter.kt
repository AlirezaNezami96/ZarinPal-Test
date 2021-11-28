package com.saviway.zarinpaltestproject.data.local.converter.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class StarredRepositoriesConverter {

    private val gson = Gson()

    @TypeConverter
    fun starredToString(item: ProfileData.Data.User.StarredRepositories): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToStarredRepositories(json: String):ProfileData.Data.User.StarredRepositories{
        val type = object : TypeToken<ProfileData.Data.User.StarredRepositories>() {
        }.type

        return gson.fromJson(json,type)
    }
}