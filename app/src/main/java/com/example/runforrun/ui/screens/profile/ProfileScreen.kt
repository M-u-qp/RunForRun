package com.example.runforrun.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.ui.screens.profile.components.OurContactsDialog
import com.example.runforrun.ui.screens.profile.components.ProfileSettingsItem
import com.example.runforrun.ui.screens.profile.components.ProfileTopBar
import com.example.runforrun.ui.screens.profile.components.ProgressCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    profileEvent: ProfileEvent,
    paddingValues: PaddingValues,
    navigateToRunningStatistics: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAchievements: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val unlockedAchievements by viewModel.unlockedAchievements.collectAsStateWithLifecycle()
    val selectedAchievements by viewModel.selectedAchievements.collectAsStateWithLifecycle()
    var isVisibleContactsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.getSelectedAchievements()
    }
    LaunchedEffect(key1 = state.error) {
        if (state.error.isNullOrBlank().not()) {
            Toast.makeText(context, state.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                    )
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp),
                        ambientColor = MaterialTheme.colorScheme.primary,
                        spotColor = MaterialTheme.colorScheme.primary
                    )
            )
            Column {
                ProfileTopBar(
                    modifier = Modifier
                        .padding(24.dp)
                        .background(color = Color.Transparent),
                    editMode = state.editMode,
                    user = state.user,
                    profileEvent = profileEvent,
                    unlockedAchievements = unlockedAchievements,
                    selectedAchievements = selectedAchievements,
                    onSelectAchievement = { achievement -> viewModel.selectAchievement(achievement) },
                    onDeselectAchievement = { achievement ->
                        viewModel.deselectAchievement(
                            achievement
                        )
                    }
                )
                ProgressCard(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .offset(y = 40.dp),
                    state = state
                )
            }
        }
        Spacer(modifier = Modifier.size(50.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProfileSettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToRunningStatistics() },
                image = ImageBitmap.imageResource(id = R.drawable.chart),
                title = stringResource(id = R.string.statistics)
            )
            ProfileSettingsItem(
                modifier = Modifier.fillMaxWidth(),
                image = ImageBitmap.imageResource(id = R.drawable.personal_char),
                title = stringResource(id = R.string.personal_characteristics)
            )
            ProfileSettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToAchievements() },
                image = ImageBitmap.imageResource(id = R.drawable.achievements),
                title = stringResource(id = R.string.achievements)
            )
            ProfileSettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isVisibleContactsDialog = true },
                image = ImageBitmap.imageResource(id = R.drawable.phone),
                title = stringResource(id = R.string.our_contacts)
            )
            ProfileSettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToSettings() },
                image = ImageBitmap.imageResource(id = R.drawable.settings),
                title = stringResource(id = R.string.settings)
            )
            ProfileSettingsItem(
                modifier = Modifier.fillMaxWidth(),
                image = ImageBitmap.imageResource(id = R.drawable.about),
                title = stringResource(id = R.string.about_app)
            )
            Spacer(modifier = Modifier.padding(paddingValues = paddingValues))
        }
    }

    if (isVisibleContactsDialog) {
        val email = stringResource(id = R.string.email)
        OurContactsDialog(
            writeToEmail = { viewModel.writeEmail(context, email) },
            onDismiss = { isVisibleContactsDialog = !isVisibleContactsDialog },
            email = email
        )
    }
}