package com.saviway.zarinpaltestproject.data.local.dao

import androidx.room.*
import com.saviway.zarinpaltestproject.data.model.ProfileData
import com.saviway.zarinpaltestproject.data.model.RepositoryData

/**
 * Created by Alireza Nezami on 11/11/2021.
 */
@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: RepositoryData.Data.User.Repositories)

    @Query("DELETE FROM Repository")
    suspend fun delete(): Int

    @Query("SELECT * FROM Repository")
    suspend fun getRepos(): RepositoryData.Data.User.Repositories?


}