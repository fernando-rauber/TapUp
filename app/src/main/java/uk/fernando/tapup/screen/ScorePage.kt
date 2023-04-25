package uk.fernando.tapup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.fernando.advertising.component.AdBanner
import uk.fernando.tapup.R
import uk.fernando.tapup.components.SimpleCard
import uk.fernando.tapup.model.ScoreModel
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.theme.*
import uk.fernando.tapup.viewmodel.ScoreViewModel
import uk.fernando.uikit.component.MyImageButton

@Composable
fun ScorePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: ScoreViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllScores()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = CenterHorizontally
    ) {

        AdBanner(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .defaultMinSize(minHeight = 50.dp),
            unitId = R.string.ad_banner_score
        )

        LeadingBoard(
            modifier = Modifier.weight(2f),
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.weight(0.2f))

        // my best score
        SimpleCard(
            title = R.string.my_record,
            titleColor = Color.White.copy(0.8f),
            value = viewModel.myBestScore.value
        )

        Spacer(modifier = Modifier.weight(0.3f))

        MyImageButton(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .defaultMinSize(minHeight = 60.dp)
                .padding(bottom = 16.dp),
            image = R.drawable.bt_yellow,
            text = stringResource(R.string.close_action).uppercase(),
            textColor = purple,
            onClick = {
                if (navController.previousBackStackEntry?.destination?.route == Directions.game.path)
                    navController.popBackStack()
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun LeadingBoard(modifier: Modifier, viewModel: ScoreViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(6))
            .background(Brush.radialGradient(colors = listOf(light_blue, light_blue2)), RoundedCornerShape(6), alpha = 0.75f)
    ) {

        Column(Modifier.padding(16.dp)) {

            // global score
            Row(
                modifier = Modifier.align(CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.global_score),
                    fontFamily = myFontKaph,
                    fontSize = 22.sp,
                    color = purple
                )

                Icon(
                    modifier = Modifier
                        .size(52.dp)
                        .padding(start = 10.dp),
                    painter = painterResource(R.drawable.ic_trophy),
                    contentDescription = null,
                    tint = purple
                )
            }

            if (viewModel.globalScore.value.isEmpty()) {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Center),
                        text = stringResource(R.string.internet_required),
                        fontFamily = myFontKaph,
                        fontSize = 16.sp,
                        color = purple,
                        textAlign = TextAlign.Center
                    )
                }
            } else
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                ) {
                    items(viewModel.globalScore.value) { score ->
                        ScoreCard(score)
                    }
                }
        }
    }
}

@Composable
private fun ScoreCard(score: ScoreModel) {
    Row(
        Modifier
            .padding(3.dp)
            .shadow(4.dp, RoundedCornerShape(35))
            .background(Brush.verticalGradient(colors = listOf(purple, purple)), RoundedCornerShape(35))
            .padding(6.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = score.name.plus(" (${score.uuid.take(6)})"),
            fontFamily = myFontKaph,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = score.score.toString(),
            fontFamily = myFontKaph,
            fontSize = 15.sp,
            color = yellow.copy(.7f)
        )
    }
}