package com.saviway.zarinpaltestproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saviway.zarinpaltestproject.data.local.converter.profile.*
import com.saviway.zarinpaltestproject.data.local.converter.repos.ReposListConverter
import com.saviway.zarinpaltestproject.data.local.dao.ProfileDao
import com.saviway.zarinpaltestproject.data.local.dao.RepositoriesDao
import com.saviway.zarinpaltestproject.data.model.ProfileData
import com.saviway.zarinpaltestproject.data.model.RepositoryData
import javax.inject.Singleton

/**
 * Created by Alireza Nezami on 11/11/2021.
 */
@Singleton
@Database(
    entities = [
        ProfileData.Data.User::class,
        RepositoryData.Data.User.Repositories::class,
    ],
    exportSchema = false,
    version = 1,
)

@TypeConverters(
    FollowersConverter::class,
    FollowingConverter::class,
    OrganizationsConverter::class,
    OrganizationsNodesConverter::class,
    RepositoriesConverter::class,
    StarredRepositoriesConverter::class,
    ReposListConverter::class,
)
abstract class DatabaseService : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    abstract fun repositoriesDao(): RepositoriesDao
}