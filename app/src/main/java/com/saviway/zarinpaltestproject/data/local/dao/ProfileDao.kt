package com.saviway.zarinpaltestproject.data.local.dao

import androidx.room.*
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/11/2021.
 */
@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: ProfileData.Data.User)

    @Query("DELETE FROM Profile")
    suspend fun delete(): Int

    @Query("SELECT * FROM Profile")
    suspend fun getProfile(): ProfileData.Data.User?

}