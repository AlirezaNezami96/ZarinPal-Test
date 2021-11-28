package com.saviway.zarinpaltestproject.data.local.converter.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class OrganizationsNodesConverter {

    private val gson = Gson()

    @TypeConverter
    fun nodesToString(item: List<ProfileData.Data.User.Organizations.Node>): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToNodes(json: String):List<ProfileData.Data.User.Organizations.Node>{
        val type = object : TypeToken<List<ProfileData.Data.User.Organizations.Node>>() {
        }.type

        return gson.fromJson(json,type)
    }
}