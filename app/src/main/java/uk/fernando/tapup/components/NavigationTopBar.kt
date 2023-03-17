package uk.fernando.tapup.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uk.fernando.tapup.R
import uk.fernando.util.component.MyIconButton

@Composable
fun NavigationTopBar(
    onBackClick: (() -> Unit)? = null,
    title: String? = null,
    rightIcon: (@Composable BoxScope.() -> Unit)? = null
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {

        if (onBackClick != null)
            MyIconButton(
                icon = R.drawable.ic_arrow_back,
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBackClick,
                tint = MaterialTheme.colorScheme.onBackground
            )

        title?.let {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (rightIcon != null)
            rightIcon()
    }
}