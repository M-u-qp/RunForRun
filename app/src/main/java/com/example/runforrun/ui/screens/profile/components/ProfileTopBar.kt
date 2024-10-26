package com.example.runforrun.ui.screens.profile.components

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.data.model.User
import com.example.runforrun.ui.components.UserProfileImage
import com.example.runforrun.ui.screens.profile.ProfileEvent

@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    editMode: Boolean,
    user: User,
    profileEvent: ProfileEvent
) {
    val context = LocalContext.current
    val userNameFR = remember { FocusRequester() }
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                try {
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    profileEvent.changeUserImage(it)
                } catch (e: SecurityException) {
                    profileEvent.changeUserImage(it)
                }

            }
        }
    )

    LaunchedEffect(key1 = editMode) {
        if (editMode) {
            userNameFR.requestFocus()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                )
        )
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Row {
                    AnimatedVisibility(
                        visible = editMode,
                        enter = scaleIn(),
                        exit = scaleOut(),
                    ) {
                        IconButton(
                            onClick = profileEvent::discardChanges,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                bitmap = ImageBitmap.imageResource(id = R.drawable.close),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    IconButton(
                        onClick = {
                            if (!editMode) {
                                profileEvent.beginEdit()
                            } else {
                                profileEvent.saveProfile(context)
                            }
                        },
                        modifier = if (!editMode) {
                            Modifier.size(24.dp)
                        } else {
                            Modifier.size(32.dp)
                        }
                    ) {
                        Icon(
                            bitmap = if (!editMode) {
                                ImageBitmap.imageResource(id = R.drawable.edit_v2)
                            } else {
                                ImageBitmap.imageResource(id = R.drawable.done)
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.size(20.dp))
            Box {
                UserProfileImage(
                    imgUri = user.img,
                    gender = user.gender,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = editMode,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    IconButton(
                        onClick = {
                            imageLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(id = R.drawable.add_image),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            if (!editMode) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                )
            } else {
                BasicTextField(
                    value = user.name,
                    onValueChange = profileEvent::changeUserName,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .focusRequester(userNameFR)
                        .wrapContentHeight()
                        .width(200.dp)
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary)
                )
                AnimatedVisibility(editMode && user.img != null) {
                    Spacer(modifier = Modifier.size(6.dp))
                    OutlinedButton(
                        onClick = { profileEvent.changeUserImage(null) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete_image),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}