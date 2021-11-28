package com.saviway.zarinpaltestproject.presentation.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.saviway.zarinpaltestproject.presentation.base.BaseFragment
import com.saviway.zarinpaltestproject.presentation.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Alireza Nezami on 11/25/2021.
 */
@AndroidEntryPoint
class RepositoriesFragment : BaseFragment() {

    private val viewModel: RepositoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRepositories()
    }

    private fun getRepositories() {
        lifecycleScope.launchWhenResumed {
            viewModel.getRepositories()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyTheme {
                    RepositoryView(
                        viewModel,
                        retry = { getRepositories() }
                    )
                }
            }
        }
    }

}