package uk.fernando.tapup.screen

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.fernando.tapup.BuildConfig
import uk.fernando.tapup.R
import uk.fernando.tapup.components.NavigationTopBar
import uk.fernando.tapup.theme.purple
import uk.fernando.tapup.theme.purple_light
import uk.fernando.tapup.theme.yellow
import uk.fernando.tapup.viewmodel.SettingsViewModel
import uk.fernando.uikit.component.MyTextField
import uk.fernando.uikit.ext.clickableSingle

@Composable
fun SettingsPage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Column {

        NavigationTopBar(
            title = stringResource(R.string.settings_title),
            onBackClick = { navController.popBackStack() }
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            CustomEditPlayerName(
                id = viewModel.playerID.value,
                name = viewModel.playerName.value,
                onNameChange = viewModel::updateName
            )

            CustomSettingsResourcesCard(
                modifier = Modifier.padding(vertical = 20.dp),
                text = R.string.sound,
                subText = R.string.sound_subtext,
                isChecked = viewModel.isSoundEnable.value,
                onCheckedChange = viewModel::updateSound
            )

            CustomSettingsResourcesCard(
                modifierRow = Modifier
                    .clickableSingle {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://funmath-5c418.web.app/memory-privacy-policy.html"))
                        context.startActivity(browserIntent)
                    },
                text = R.string.privacy_policy,
                isChecked = false,
                onCheckedChange = {},
                showArrow = true
            )

            Spacer(Modifier.weight(0.9f))

            Text(
                text = stringResource(id = R.string.version, BuildConfig.VERSION_NAME),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                textAlign = TextAlign.Center
            )

            if (BuildConfig.BUILD_TYPE == "debug") {
                Text(
                    text = "Dev Build",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun CustomEditPlayerName(id: String, name: String, onNameChange: (String) -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }

    Box(
        Modifier
            .shadow(4.dp, RoundedCornerShape(30))
            .background(Brush.verticalGradient(colors = listOf(purple, purple_light)), RoundedCornerShape(30))
    ) {

        Box(Modifier.padding(6.dp)) {

            if (!isEditMode) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = name.plus(" ($id)"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )

                    TextButton(onClick = { isEditMode = true }) {
                        Text(
                            text = stringResource(id = R.string.edit_action),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            } else {
                var isValidName by remember { mutableStateOf(true) }
                var playerName by remember { mutableStateOf(name) }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    MyTextField(
                        modifier = Modifier.weight(1f),
                        value = playerName,
                        onValueChange = {
                            playerName = it
                            isValidName = it.length in 1..12
                        }
                    )

                    MyImageButton(
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(horizontal = 4.dp),
                        image = R.drawable.bt_yellow,
                        text = stringResource(R.string.done_action),
                        fontSize = 13.sp,
                        textColor = purple,
                        enabled = isValidName,
                        onClick = {
                            onNameChange(playerName)
                            isEditMode = false
                        }
                    )
                }
            }
        }
    }
}

//@Composable
//private fun CustomSettingsResourcesCard(
//    modifier: Modifier = Modifier,
//    modifierRow: Modifier = Modifier,
//    @StringRes text: Int
//) {
//    Box(
//        modifier = modifier
//            .shadow(4.dp, RoundedCornerShape(30))
//            .background(Brush.verticalGradient(colors = listOf(purple, purple_light)), RoundedCornerShape(30))
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = modifierRow.padding(16.dp)
//        ) {
//
//            Text(
//                text = stringResource(id = text),
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.White,
//                modifier = Modifier
//                    .padding(end = 20.dp)
//                    .weight(1f),
//            )
//
//            Icon(
//                painter = painterResource(id = R.drawable.ic_arrow_forward),
//                contentDescription = null,
//                tint = Color.White
//            )
//
//        }
//    }
//}

@Composable
private fun CustomSettingsResourcesCard(
    modifier: Modifier = Modifier,
    modifierRow: Modifier = Modifier,
    @StringRes text: Int,
    @StringRes subText: Int? = null,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    showArrow: Boolean? = null
) {
    Box(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(30))
            .background(Brush.verticalGradient(colors = listOf(purple, purple_light)), RoundedCornerShape(30))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifierRow.padding(16.dp)
        ) {

            Column(
                Modifier
                    .padding(end = 20.dp)
                    .weight(1f),
            ) {

                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(end = 5.dp)
                )

                subText?.let {
                    Text(
                        text = stringResource(id = subText),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (showArrow == null)
                Switch(
                    checked = isChecked,
                    onCheckedChange = { onCheckedChange() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedThumbColor = yellow,
                        checkedTrackColor = yellow,
                        uncheckedTrackColor = Color.White
                    )
                )
            else if (showArrow)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = Color.White
                )

        }
    }
}