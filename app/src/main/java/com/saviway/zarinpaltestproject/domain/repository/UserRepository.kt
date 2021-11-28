package com.saviway.zarinpaltestproject.domain.repository

import com.saviway.zarinpaltestproject.data.model.ProfileData
import com.saviway.zarinpaltestproject.data.model.RepositoryData
import com.saviway.zarinpaltestproject.util.Resource

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
interface UserRepository {

    suspend fun getProfile() : Resource<ProfileData.Data.User>

    suspend fun getRepositories() :Resource<List<RepositoryData.Data.User.Repositories.Node>>
}