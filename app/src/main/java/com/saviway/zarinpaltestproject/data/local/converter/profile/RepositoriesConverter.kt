package com.saviway.zarinpaltestproject.data.local.converter.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class RepositoriesConverter {

    private val gson = Gson()

    @TypeConverter
    fun repositoriesToString(item: ProfileData.Data.User.Repositories): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToRepositories(json: String):ProfileData.Data.User.Repositories{
        val type = object : TypeToken<ProfileData.Data.User.Repositories>() {
        }.type

        return gson.fromJson(json,type)
    }
}