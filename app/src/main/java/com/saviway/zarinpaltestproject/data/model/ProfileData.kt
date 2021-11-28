package com.saviway.zarinpaltestproject.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.saviway.zarinpaltestproject.data.local.converter.profile.*

data class ProfileData(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("user")
        val user: User
    ) {


        @Entity(tableName = "Profile")
        data class User(

            @PrimaryKey(autoGenerate = true)
            val id: Int?,

            @TypeConverters(FollowersConverter::class)
            @SerializedName("followers")
            var followers: Followers,

            @TypeConverters(FollowingConverter::class)
            @SerializedName("following")
            var following: Following,

            @TypeConverters(StarredRepositoriesConverter::class)
            @SerializedName("starredRepositories")
            var starredRepositories: StarredRepositories,

            @TypeConverters(RepositoriesConverter::class)
            @SerializedName("repositories")
            var repositories: Repositories,

            @SerializedName("viewerIsFollowing")
            val viewerIsFollowing: Boolean?,

            @SerializedName("viewerCanSponsor")
            val viewerCanSponsor: Boolean?,

            @SerializedName("bio")
            val bio: String,

            @SerializedName("email")
            val email: String,

            @SerializedName("avatarUrl")
            val avatarUrl: String,

            @SerializedName("login")
            val login: String,

            @SerializedName("name")
            val name: String,

            @SerializedName("company")
            val company: String,

            @SerializedName("location")
            val location: String,

            @SerializedName("twitterUsername")
            val twitterUsername: String,

            @SerializedName("websiteUrl")
            val websiteUrl: String,

            @TypeConverters(OrganizationsConverter::class)
            @SerializedName("organizations")
            var organizations: Organizations?
        ) {
            data class Followers(
                @SerializedName("totalCount")
                val totalCount: Int?
            )

            data class Following(
                @SerializedName("totalCount")
                val totalCount: Int?
            )

            data class StarredRepositories(
                @SerializedName("totalCount")
                val totalCount: Int?
            )

            data class Repositories(
                @SerializedName("totalCount")
                val totalCount: Int?
            )


            data class Organizations(
                @TypeConverters(OrganizationsNodesConverter::class)
                @SerializedName("nodes")
                var nodes: List<Node>
            ) {
                data class Node(
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("avatarUrl")
                    val avatarUrl: String
                )
            }
        }
    }
}