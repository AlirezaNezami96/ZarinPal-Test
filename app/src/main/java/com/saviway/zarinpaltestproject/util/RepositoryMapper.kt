package com.saviway.zarinpaltestproject.util

import com.saviway.zarinpaltestproject.RepositoriesQuery
import com.saviway.zarinpaltestproject.data.model.RepositoryData

/**
 * Created by Alireza Nezami on 11/27/2021.
 */
object RepositoryMapper {

    fun mapRepositoryQueryToRepositories(data: RepositoriesQuery.Data): RepositoryData.Data.User.Repositories {
        val nodes: MutableList<RepositoryData.Data.User.Repositories.Node> = mutableListOf()
        data.user()?.repositories()?.nodes()?.forEach {
            nodes.add(
                RepositoryData.Data.User.Repositories.Node(
                    name = it.name().orEmpty(),
                    description = it.description().orEmpty(),
                    id = it.id().orEmpty(),
                    openGraphImageUrl = it.openGraphImageUrl() as String,
                    forkCount = it.forkCount(),
                    viewerHasStarred = it.viewerHasStarred(),
                    stargazerCount = it.stargazerCount(),
                    updatedAt = it.updatedAt() as String,
                    licenseInfo = RepositoryData.Data.User.Repositories.Node.LicenseInfo(
                        it.licenseInfo()?.name()
                    ),
                    primaryLanguage = RepositoryData.Data.User.Repositories.Node.PrimaryLanguage(
                        name = it.primaryLanguage()?.name(),
                        color = it.primaryLanguage()?.color()
                    )
                )
            )
        }
        return RepositoryData.Data.User.Repositories(
            id = null,
            nodes = nodes
        )
    }
}