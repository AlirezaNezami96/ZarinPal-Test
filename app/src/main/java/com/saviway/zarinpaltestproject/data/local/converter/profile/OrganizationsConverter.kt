package com.saviway.zarinpaltestproject.data.local.converter.profile

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class OrganizationsConverter {

    private val gson = Gson()

    @TypeConverter
    fun organizationsToString(item: ProfileData.Data.User.Organizations): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToOrganizations(json: String): ProfileData.Data.User.Organizations {
        val type = object : TypeToken<ProfileData.Data.User.Organizations>() {
        }.type

        return gson.fromJson(json, type)
    }
}