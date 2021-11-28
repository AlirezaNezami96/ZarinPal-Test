package com.saviway.zarinpaltestproject.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.saviway.zarinpaltestproject.data.local.converter.repos.ReposListConverter

data class RepositoryData(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("user")
        val user: User
    ) {
        data class User(
            @SerializedName("repositories")
            val repositories: Repositories
        ) {


            @Entity(tableName = "Repository")
            data class Repositories(

                @PrimaryKey(autoGenerate = true)
                val id: Int?,

                @TypeConverters(ReposListConverter::class)
                @SerializedName("nodes")
                val nodes: List<Node>

            ) {
                data class Node(
                    @SerializedName("name")
                    val name: String,

                    @SerializedName("id")
                    val id: String,

                    @SerializedName("description")
                    val description: String,

                    @SerializedName("openGraphImageUrl")
                    val openGraphImageUrl: String,

                    @SerializedName("forkCount")
                    val forkCount: Int,

                    @SerializedName("viewerHasStarred")
                    val viewerHasStarred: Boolean,

                    @SerializedName("stargazerCount")
                    val stargazerCount: Int,

                    @SerializedName("updatedAt")
                    val updatedAt: String,

                    @SerializedName("primaryLanguage")
                    val primaryLanguage: PrimaryLanguage?,

                    @SerializedName("licenseInfo")
                    val licenseInfo: LicenseInfo?

                ) {
                    data class PrimaryLanguage(
                        @SerializedName("color")
                        val color: String?,

                        @SerializedName("name")
                        val name: String?

                    )

                    data class LicenseInfo(
                        @SerializedName("name")
                        val name: String?

                    )
                }
            }
        }
    }
}