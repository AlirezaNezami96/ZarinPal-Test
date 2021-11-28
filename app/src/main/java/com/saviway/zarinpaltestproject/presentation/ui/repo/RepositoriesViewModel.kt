package com.saviway.zarinpaltestproject.presentation.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saviway.zarinpaltestproject.data.model.RepositoryData
import com.saviway.zarinpaltestproject.domain.repository.UserRepository
import com.saviway.zarinpaltestproject.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
@HiltViewModel
class RepositoriesViewModel
@Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _networkError: MutableLiveData<Boolean> = MutableLiveData()
    val networkError: LiveData<Boolean> = _networkError

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _reposData = MutableLiveData<List<RepositoryData.Data.User.Repositories.Node>>()
    val reposData: LiveData<List<RepositoryData.Data.User.Repositories.Node>> = _reposData

    suspend fun getRepositories() {
        _loading.postValue(true)
        _networkError.postValue(false)
        val response = repository.getRepositories()
        if (response.status == Status.ERROR) {
            _networkError.postValue(true)
        } else if (response.status == Status.SUCCESS) {
            _reposData.postValue(response.data!!)
        }
        _loading.postValue(false)
    }

}