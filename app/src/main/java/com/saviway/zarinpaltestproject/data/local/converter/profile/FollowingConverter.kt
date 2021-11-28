package com.saviway.zarinpaltestproject.data.local.converter.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class FollowingConverter {

    private val gson = Gson()

    @TypeConverter
    fun followingToString(item: ProfileData.Data.User.Following): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToFollowing(json: String):ProfileData.Data.User.Following{
        val type = object : TypeToken<ProfileData.Data.User.Following>() {
        }.type

        return gson.fromJson(json,type)
    }
}