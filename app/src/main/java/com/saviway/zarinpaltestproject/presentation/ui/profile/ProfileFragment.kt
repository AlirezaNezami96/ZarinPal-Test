package com.saviway.zarinpaltestproject.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.saviway.zarinpaltestproject.R
import com.saviway.zarinpaltestproject.presentation.base.BaseFragment
import com.saviway.zarinpaltestproject.presentation.theme.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Alireza Nezami on 11/24/2021.
 */
const val TAG = "ProfileFragment"

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getProfile()
    }

    private fun getProfile() {
        lifecycleScope.launchWhenResumed {
            viewModel.getProfileDetails()
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
                    ProfileView(
                        viewModel,
                        retry = { getProfile() },
                        navToRepos = {
                            findNavController().navigate(R.id.profileToRepositories)
                        }
                    )
                }
            }
        }
    }

}