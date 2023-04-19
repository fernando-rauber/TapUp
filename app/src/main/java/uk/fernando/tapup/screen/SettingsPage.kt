package uk.fernando.tapup.screen

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.fernando.tapup.BuildConfig
import uk.fernando.tapup.R
import uk.fernando.tapup.components.NavigationTopBar
import uk.fernando.util.ext.clickableSingle

@Composable
fun SettingsPage(navController: NavController = NavController(LocalContext.current)) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {

        NavigationTopBar(
            title = stringResource(R.string.settings_title),
            onBackClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .align(Center)
                .fillMaxWidth(.6f),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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
private fun CustomSettingsResourcesCard(
    modifier: Modifier = Modifier,
    modifierRow: Modifier = Modifier,
    @StringRes text: Int,
    @StringRes subText: Int? = null,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showArrow: Boolean? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(25),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp
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

                Row {

                    Text(
                        text = stringResource(id = text),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }

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
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedThumbColor = Color.White,
                    )
                )
            else if (showArrow)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

        }
    }
}