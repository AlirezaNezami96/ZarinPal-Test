package com.saviway.zarinpaltestproject.domain.repository

import android.util.Log
import com.saviway.zarinpaltestproject.data.local.dao.ProfileDao
import com.saviway.zarinpaltestproject.data.local.dao.RepositoriesDao
import com.saviway.zarinpaltestproject.data.model.ProfileData
import com.saviway.zarinpaltestproject.data.model.RepositoryData
import com.saviway.zarinpaltestproject.data.network.ProfileApi
import com.saviway.zarinpaltestproject.data.network.RepositoriesApi
import com.saviway.zarinpaltestproject.util.RepositoryMapper
import com.saviway.zarinpaltestproject.util.Resource
import com.saviway.zarinpaltestproject.util.UserMapper
import javax.inject.Inject

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
class UserRepositoryImpl
@Inject
constructor(
    private val profileDao: ProfileDao,
    private val repositoriesDao: RepositoriesDao,
    private val profileApi: ProfileApi,
    private val repositoriesApi: RepositoriesApi
) : UserRepository {

    /**
     * Check for the local database for existing user profile
     * If not existed, fetch from github server,
     * Then saves it to local db for offline-first approach
     */
    override suspend fun getProfile(): Resource<ProfileData.Data.User> {
        Log.i("TAG", "getProfile: ${System.currentTimeMillis()}")
        val response = profileApi.getProfile()
        Log.i("TAG", "getProfile: ${System.currentTimeMillis()}")

        if (response == null) {
            val profile = profileDao.getProfile()
            profile?.let {
                return Resource.success(profile)
            } ?: return Resource.error("Internet not connected", null)
        } else {
            val user = UserMapper.mapProfileQueryToUser(response)
            profileDao.delete()
            profileDao.insert(user)
            return Resource.success(user)
        }
    }

    override suspend fun getRepositories(): Resource<List<RepositoryData.Data.User.Repositories.Node>> {
        val response = repositoriesApi.getRepositories()

        if (response == null) {
            val repos = repositoriesDao.getRepos()

            repos?.let {
                return Resource.success(repos.nodes)
            } ?: return Resource.error("Internet not connected", listOf())
        } else {
            val repositories = RepositoryMapper.mapRepositoryQueryToRepositories(response)
            repositoriesDao.delete()
            repositoriesDao.insert(repositories)
            return Resource.success(repositories.nodes)
        }
    }


}