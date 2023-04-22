package uk.fernando.tapup.screen

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
import uk.fernando.tapup.theme.light_blue
import uk.fernando.tapup.theme.light_blue2
import uk.fernando.tapup.theme.purple
import uk.fernando.tapup.theme.yellow
import uk.fernando.tapup.viewmodel.SettingsViewModel
import uk.fernando.uikit.component.MyButton
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
                name = viewModel.playerName.value,
                onNameChange = viewModel::updateName
            )

            CustomSettingsResourcesCard(
                modifier = Modifier.padding(vertical = 30.dp),
                modifierRow = Modifier
                    .clickableSingle {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://funmath-5c418.web.app/memory-privacy-policy.html"))
                        context.startActivity(browserIntent)
                    },
                text = R.string.privacy_policy
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
private fun CustomEditPlayerName(name: String, onNameChange: (String) -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }

    Box(
        Modifier
            .shadow(4.dp, RoundedCornerShape(30))
            .background(Brush.verticalGradient(colors = listOf(light_blue, light_blue2)), RoundedCornerShape(30))
    ) {

        Box(Modifier.padding(6.dp)) {

            if (!isEditMode) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = purple,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )

                    TextButton(onClick = { isEditMode = true }) {
                        Text(
                            text = stringResource(id = R.string.edit_action),
                            style = MaterialTheme.typography.bodySmall,
                            color = purple
                        )
                    }
                }
            } else {
                var isValidName by remember { mutableStateOf(true) }
                var playerName by remember { mutableStateOf(name) }

                MyTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = playerName,
                    onValueChange = {
                        playerName = it
                        isValidName = it.length in 1..12
                    },
                    trailingIcon = {
                        MyButton(
                            modifier = Modifier.padding(end = 4.dp),
                            text = stringResource(R.string.done_action),
                            fontSize = 13.sp,
                            enabled = isValidName,
                            onClick = {
                                onNameChange(playerName)
                                isEditMode = false
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CustomSettingsResourcesCard(
    modifier: Modifier = Modifier,
    modifierRow: Modifier = Modifier,
    @StringRes text: Int
) {
    Box(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(30))
            .background(Brush.verticalGradient(colors = listOf(light_blue, light_blue2)), RoundedCornerShape(30))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifierRow.padding(16.dp)
        ) {

            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.bodyMedium,
                color = purple,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .weight(1f),
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = null,
                tint = purple
            )

        }
    }
}