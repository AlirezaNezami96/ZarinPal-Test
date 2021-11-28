package com.saviway.zarinpaltestproject.presentation.ui.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.request.RequestOptions
import com.saviway.zarinpaltestproject.R
import com.saviway.zarinpaltestproject.data.model.ProfileData
import com.saviway.zarinpaltestproject.presentation.theme.BLUE
import com.saviway.zarinpaltestproject.presentation.theme.GREY
import com.saviway.zarinpaltestproject.presentation.theme.PINK
import com.saviway.zarinpaltestproject.presentation.ui.common.InternetDialog
import com.saviway.zarinpaltestproject.util.CustomExtensions.divideBigNumber
import com.saviway.zarinpaltestproject.util.CustomExtensions.toUserName
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.LocalGlideRequestOptions

/**
 * Created by Alireza Nezami on 11/28/2021.
 */
@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    retry: () -> Unit,
    navToRepos: () -> Unit,
) {

    val profile by viewModel.profileData.observeAsState()
    val isLoading by viewModel.loading.observeAsState()
    val error by viewModel.networkError.observeAsState()

    profile?.let { userProfile ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface)

        ) {

            Header(
                userProfile,
                Modifier
                    .fillMaxWidth()
            )

            Statistics(
                userProfile,
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Organizations(
                userProfile,
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            RepositoriesButton {
                navToRepos()
                Log.i("TAG", "Main: ")
            }
        }
    }


    isLoading?.let {
        Loading(
            it
        )
    }

    error?.let {
        InternetDialog(
            dialogState = it,
            onDismissRequest = {
                retry()
            },
            onDialogPositiveButtonClicked = {
                retry()
            },
        )
    }
}

@Composable
fun Loading(
    isLoading: Boolean,
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}

@Composable
fun Header(
    item: ProfileData.Data.User,
    modifier: Modifier
) {

    ConstraintLayout(
        modifier = Modifier
            .then(modifier)
    ) {
        val (avatar,
            name,
            username,
            company,
            follow,
            sponsor,
            location,
            website) = createRefs()

        val requestOptions = RequestOptions()
            .override(300, 300)
            .circleCrop()
        CompositionLocalProvider(LocalGlideRequestOptions provides requestOptions) {
            GlideImage(
                imageModel = item.avatarUrl,
                contentScale = ContentScale.Fit,
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 50.dp)
                    .size(100.dp)
                    .constrainAs(avatar) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                loading = {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val indicator = createRef()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(indicator) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )
                    }
                },
                failure = {
                    Text(text = "image request failed.")
                }
            )
        }

        Button(
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (item.viewerIsFollowing == true) MaterialTheme.colors.secondary
                else MaterialTheme.colors.primaryVariant
            ),
            shape = RoundedCornerShape(10),
            elevation = ButtonDefaults.elevation(5.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(follow) {
                    bottom.linkTo(avatar.bottom)
                    end.linkTo(avatar.start)
                }
        ) {
            Text(
                text =
                if (item.viewerIsFollowing == true) stringResource(id = R.string.unfollow)
                else stringResource(id = R.string.follow),
                color =
                if (item.viewerIsFollowing == true) MaterialTheme.colors.onSecondary
                else MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 5.dp),
            )

        }

        Button(
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = PINK
            ),
            shape = RoundedCornerShape(10),
            elevation = ButtonDefaults.elevation(5.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(sponsor) {
                    bottom.linkTo(avatar.bottom)
                    start.linkTo(avatar.end)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_heart),
                contentDescription = "",
                tint = Color.White
            )

            Text(
                text = stringResource(id = R.string.sponsor),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(horizontal = 5.dp),
            )

        }

        Text(
            text = item.name.orEmpty(),
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(top = 12.dp)
                .constrainAs(name) {
                    top.linkTo(avatar.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )

        Text(
            text = item.login.toUserName(),
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(username) {
                    top.linkTo(name.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )

        Text(
            text = item.company.orEmpty(),
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(company) {
                    top.linkTo(username.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )

        Text(
            text = item.location.orEmpty(),
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(location) {
                    top.linkTo(company.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )

        Text(
            text = item.websiteUrl.orEmpty(),
            color = BLUE,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(website) {
                    top.linkTo(location.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Statistics(
    item: ProfileData.Data.User,
    modifier: Modifier
) {

    Divider(
        color = GREY,
        modifier = modifier.then(
            Modifier
                .height(1.dp)
        )
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            TitleText(
                text = item.repositories.totalCount.divideBigNumber(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 2.dp)
            )

            SubTitleText(
                text = stringResource(id = R.string.repositories),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            TitleText(
                text = item.starredRepositories.totalCount.divideBigNumber(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 2.dp)
            )

            SubTitleText(
                text = stringResource(id = R.string.stars),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            TitleText(
                text = item.followers.totalCount.divideBigNumber(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 2.dp)
            )

            SubTitleText(
                text = stringResource(id = R.string.followers),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {

            TitleText(
                text = item.following.totalCount.divideBigNumber(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 2.dp),
            )

            SubTitleText(
                text = stringResource(id = R.string.following),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }
    }

    Divider(
        color = GREY,
        modifier = modifier.then(
            Modifier
                .height(1.dp)
        )
    )
}

@Composable
fun Organizations(
    item: ProfileData.Data.User,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        TitleText(
            text = stringResource(id = R.string.organizations),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 2.dp)
        )

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            item.organizations?.nodes?.forEach {
                SmallImage(it)
            }
        }
    }

    Divider(
        color = GREY,
        modifier = modifier.then(
            Modifier
                .height(1.dp)
        )
    )
}

@Composable
fun RepositoriesButton(onRepositoriesFragment: (() -> Unit)?) {
    Button(
        onClick = {
            onRepositoriesFragment?.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant
        ),
        shape = RoundedCornerShape(10),
        elevation = ButtonDefaults.elevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.repositories).uppercase(),
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(vertical = 8.dp),
        )

    }

}

// Reusable Title textView
@Composable
fun TitleText(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center
    )
}

// Reusable SubTitle textView
@Composable
fun SubTitleText(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colors.secondary,
        modifier = modifier,
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )
}


@Composable
fun SmallImage(imageUrl: ProfileData.Data.User.Organizations.Node) {
    GlideImage(
        imageModel = imageUrl.avatarUrl,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(32.dp),
        loading = {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val indicator = createRef()
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicator) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        },
    )
}
