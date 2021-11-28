package com.saviway.zarinpaltestproject.util

import com.saviway.zarinpaltestproject.ProfileQuery
import com.saviway.zarinpaltestproject.data.model.ProfileData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
object UserMapper {

    fun mapProfileQueryToUser(data: ProfileQuery.Data): ProfileData.Data.User {
        val nodes: MutableList<ProfileData.Data.User.Organizations.Node> = mutableListOf()
        data.user()?.organizations()?.nodes()?.forEach {
            nodes.add(
                ProfileData.Data.User.Organizations.Node(
                    name = it.name().orEmpty(),
                    avatarUrl = it.avatarUrl().toString()
                )
            )
        }

        return ProfileData.Data.User(
            name = data.user()?.name().toString(),
            bio = data.user()?.bio().toString(),
            email = data.user()?.email().toString(),
            company = data.user()?.company().toString(),
            websiteUrl = data.user()?.websiteUrl().toString(),
            twitterUsername = data.user()?.twitterUsername().toString(),
            location = data.user()?.location().toString(),
            viewerCanSponsor = data.user()?.viewerCanSponsor(),
            viewerIsFollowing = data.user()?.viewerIsFollowing(),
            avatarUrl = data.user()?.avatarUrl().toString(),
            login = data.user()?.login().toString(),
            followers = ProfileData.Data.User.Followers(
                totalCount = data.user()?.followers()?.totalCount()
            ),
            following = ProfileData.Data.User.Following(
                totalCount = data.user()?.following()?.totalCount()
            ),
            starredRepositories = ProfileData.Data.User.StarredRepositories(
                totalCount = data.user()?.starredRepositories()?.totalCount()
            ),
            repositories = ProfileData.Data.User.Repositories(
                totalCount = data.user()?.repositories()?.totalCount()
            ),
            id = null,
            organizations = ProfileData.Data.User.Organizations(nodes)
        )
    }
}