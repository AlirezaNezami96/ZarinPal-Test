package com.saviway.zarinpaltestproject.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.saviway.zarinpaltestproject.R

/**
 * Created by Alireza Nezami on 11/28/2021.
 */
@Composable
fun InternetDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean = false,
    onDialogPositiveButtonClicked: (() -> Unit)? = null,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
) {
    val buttonPaddingAll = 8.dp
    val dialogShape = RoundedCornerShape(16.dp)

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                onDialogStateChange?.invoke(true)
                onDismissRequest?.invoke()
            },
            title = null,
            text = null,
            buttons = {
                Column {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        painter = painterResource(R.drawable.ic_disconnected),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                    )
                    Text(
                        text = stringResource(R.string.no_internet),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onPrimary
                    )

                    Text(
                        text = stringResource(R.string.check_network_try_again),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.body1,
                        color = androidx.compose.ui.graphics.Color.Gray
                    )


                    Row(
                        modifier = Modifier.padding(all = buttonPaddingAll),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onDialogStateChange?.invoke(true)
                                onDialogPositiveButtonClicked?.invoke()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.dialog_retry),
                                color = MaterialTheme.colors.onPrimary,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = modifier,
            shape = dialogShape
        )
    }
}