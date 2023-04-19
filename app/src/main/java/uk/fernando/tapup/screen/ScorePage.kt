package uk.fernando.tapup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.fernando.tapup.R
import uk.fernando.tapup.components.SimpleCard
import uk.fernando.tapup.model.ScoreModel
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.viewmodel.ScoreViewModel
import uk.fernando.util.component.MyButton
import uk.fernando.util.ext.safeNav

@Composable
fun ScorePage(
    navController: NavController = NavController(LocalContext.current),
    newScore: Int,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllScores(newScore)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {

        // global score
        Text(
            modifier = Modifier.padding(vertical = 30.dp),
            text = stringResource(R.string.global_score),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
        ) {
            item {
                Row(Modifier.padding(vertical = 10.dp)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.name),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = stringResource(id = R.string.score),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            items(viewModel.globalScore.value) { score ->
                ScoreCard(score)
            }

        }

        // my best score
        SimpleCard(title = R.string.my_score, value = viewModel.myBestScore.value)

        Spacer(modifier = Modifier.heightIn(30.dp))

        // my last score
        SimpleCard(title = R.string.score, value = viewModel.myScore.value)

        Spacer(modifier = Modifier.weight(1f))

        MyButton(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 50.dp),
            onClick = {
                navController.popBackStack()
                if (newScore > 0)
                    navController.safeNav(Directions.home.path)
            },
            text = stringResource(R.string.close_action)
        )
    }
}

@Composable
private fun ScoreCard(score: ScoreModel) {
    Row {
        Text(
            modifier = Modifier.weight(1f),
            text = score.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = score.score.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}