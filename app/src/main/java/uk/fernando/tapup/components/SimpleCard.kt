package uk.fernando.tapup.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.fernando.tapup.theme.light_blue
import uk.fernando.tapup.theme.light_blue2
import uk.fernando.tapup.theme.purple

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    titleColor: Color = purple,
    value: Int
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            color = titleColor
        )

        Text(
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(35))
                .background(Brush.verticalGradient(colors = listOf(light_blue, light_blue2)), RoundedCornerShape(35))
                .padding(horizontal = 80.dp, vertical = 6.dp),
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 40.sp,
            color = purple
        )
    }
}