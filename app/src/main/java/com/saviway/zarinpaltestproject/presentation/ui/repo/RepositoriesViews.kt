package com.saviway.zarinpaltestproject.presentation.ui.repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.request.RequestOptions
import com.saviway.zarinpaltestproject.R
import com.saviway.zarinpaltestproject.data.model.RepositoryData
import com.saviway.zarinpaltestproject.presentation.theme.GREY
import com.saviway.zarinpaltestproject.presentation.ui.common.InternetDialog
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.LocalGlideRequestOptions

/**
 * Created by Alireza Nezami on 11/28/2021.
 */
@Composable
fun RepositoryView(
    viewModel: RepositoriesViewModel,
    retry: () -> Unit
) {
    val repos by viewModel.reposData.observeAsState()
    val isLoading by viewModel.loading.observeAsState()
    val error by viewModel.networkError.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {

        ListView(repos, viewModel)

        Loading(
            isLoading = isLoading == true,
            modifier = Modifier
        )

        error?.let {
            InternetDialog(
                dialogState = it,
                onDismissRequest = { retry() },
                onDialogPositiveButtonClicked = { retry() }
            )
        }

    }
}

@Composable
fun ListView(
    repos: List<RepositoryData.Data.User.Repositories.Node>?,
    viewModel: RepositoriesViewModel
) {
    repos?.let {
        LazyColumn() {
            items(repos) { repo ->
                RepoListItem(repo, viewModel)
            }
        }
    }
}

@Composable
fun RepoListItem(
    repo: RepositoryData.Data.User.Repositories.Node,
    viewModel: RepositoriesViewModel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                end = 16.dp,
                start = 16.dp,
                top = 8.dp
            )
            .background(color = MaterialTheme.colors.surface)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val (image,
                name,
                description,
                star,
                details) = createRefs()

            val requestOptions = RequestOptions()
                .override(300, 300)
                .circleCrop()
            CompositionLocalProvider(LocalGlideRequestOptions provides requestOptions) {
                GlideImage(
                    imageModel = repo.openGraphImageUrl,
                    contentScale = ContentScale.Fit,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(50.dp)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
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
                    },
                )
            }


            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = GREY
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                modifier = Modifier
                    .constrainAs(star) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(
                    imageVector =
                    if (repo.viewerHasStarred) Icons.Outlined.Star
                    else Icons.Filled.Star,
                    contentDescription = "",
                    tint = if (repo.viewerHasStarred) Color.White else Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text =
                    if (repo.viewerHasStarred) stringResource(id = R.string.unstar)
                    else stringResource(id = R.string.star),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(Color.Transparent)
                )
            }

            TitleText(
                text = repo.name.orEmpty(),
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                    }
                    .padding(vertical = 2.dp, horizontal = 5.dp)
            )

            SubTitleText(
                text = repo.description.orEmpty(),
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(star.bottom)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(vertical = 2.dp, horizontal = 5.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .constrainAs(details) {
                        top.linkTo(description.bottom)
                        start.linkTo(image.end)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = "",
                    tint = Color(android.graphics.Color.parseColor(repo.primaryLanguage?.color)),
                    modifier = Modifier.size(16.dp)
                )

                repo.primaryLanguage?.name?.let {
                    SubTitleText(
                        text = it,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 8.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "",
                    tint = GREY,
                    modifier = Modifier
                        .size(16.dp)
                )

                SubTitleText(
                    text = repo.stargazerCount.toString(),
                    modifier = Modifier
                        .padding(start = 5.dp, end = 8.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_fork),
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp)
                )

                SubTitleText(
                    text = repo.forkCount.toString(),
                    modifier = Modifier
                        .padding(start = 5.dp, end = 8.dp)
                )

                repo.licenseInfo?.name?.let {
                    Icon(
                        painter = painterResource(R.drawable.ic_balance),
                        contentDescription = "",
                        modifier = Modifier
                            .size(16.dp)
                    )

                    SubTitleText(
                        text = it,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 8.dp)
                    )
                }
            }
        }
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
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.primary,
        modifier = modifier,
        fontWeight = FontWeight.Normal,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Start
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
        maxLines = 5,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.secondary,
        modifier = modifier,
        fontWeight = FontWeight.Light,
        style = MaterialTheme.typography.caption,
        textAlign = TextAlign.Start
    )
}

@Composable
fun Loading(
    isLoading: Boolean,
    modifier: Modifier
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
