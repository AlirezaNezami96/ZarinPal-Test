package com.saviway.zarinpaltestproject.data.local.converter.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class FollowersConverter {

    private val gson = Gson()

    @TypeConverter
    fun followerToString(followers: ProfileData.Data.User.Followers): String {
        return gson.toJson(followers)
    }

    @TypeConverter
    fun stringToFollower(json: String):ProfileData.Data.User.Followers{
        val type = object : TypeToken<ProfileData.Data.User.Followers>() {
        }.type

        return gson.fromJson(json,type)
    }
}