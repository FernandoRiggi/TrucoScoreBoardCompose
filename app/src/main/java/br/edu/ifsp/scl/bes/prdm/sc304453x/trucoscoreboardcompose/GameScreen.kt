package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboardcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val game = remember { Game() }

    var usScore by remember { mutableStateOf(game.usScore) }
    var themScore by remember { mutableStateOf(game.themScore) }
    var roundValue by remember { mutableStateOf(RoundValue.ONE) }
    var gameState by remember { mutableStateOf(GameState.NORMAL) }

    val isGameFinished = usScore >= 12 || themScore >= 12

    var isTrucoEnabled by remember { mutableStateOf(true) }

    val addPointTeamUs = {
        game.addPoint(Team.US)
        usScore = game.usScore
        roundValue = game.roundValue
        gameState = game.state
    }

    val addPointTeamThem = {
        game.addPoint(Team.THEM)
        themScore = game.themScore
        roundValue = game.roundValue
        gameState = game.state
    }

    val callTruco = {
        game.callTruco()
        roundValue = game.roundValue
        gameState = game.state
    }

    val reset = {
        game.resetGame()
        roundValue = game.roundValue
        gameState = game.state
        usScore = game.usScore
        themScore = game.themScore
        isTrucoEnabled = true
    }

    if (gameState == GameState.HAND_OF_ELEVEN) {
        isTrucoEnabled = false
    }

    val pointButtonnModifier = Modifier
        .width(110.dp)
        .height(80.dp)

    val centerButtonModifier = Modifier
        .width(220.dp)
        .height(70.dp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nós", style = MaterialTheme.typography.bodyLarge, fontSize = 40.sp)
                Text("${usScore}", style = MaterialTheme.typography.bodyMedium, fontSize = 70.sp)
                Button(
                    onClick = addPointTeamUs,
                    enabled = !isGameFinished,
                    modifier = pointButtonnModifier,
                    contentPadding = PaddingValues(0.dp)
                )  {
                    Text("+${roundValue.points}", fontSize = 34.sp)
                }
            }

            Spacer(modifier = Modifier.width((16.dp)))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Eles", style = MaterialTheme.typography.bodyLarge, fontSize = 40.sp)
                Text("${themScore}", style = MaterialTheme.typography.bodyMedium, fontSize = 70.sp)
                Button(
                    onClick = addPointTeamThem,
                    enabled = !isGameFinished,
                    modifier = pointButtonnModifier,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("+${roundValue.points}", fontSize = 34.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Valendo: ${roundValue.points}", fontSize = 26.sp)
            Button(
                onClick = callTruco,
                enabled = !isGameFinished && isTrucoEnabled,
                modifier = centerButtonModifier
            ) {
                Text("Truco", fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = reset,
                modifier = centerButtonModifier
            ) {
                Text("Resetar", fontSize = 22.sp)
            }
        }
    }

    if (gameState == GameState.HAND_OF_ELEVEN) {
        HandOfElevenDialog(
            onPlay = {
                game.resolveHandOfEleven(true)
                usScore = game.usScore
                themScore = game.themScore
                roundValue = game.roundValue
                gameState = game.state
                isTrucoEnabled = false
            },
            onQuit = {
                game.resolveHandOfEleven(false)
                usScore = game.usScore
                themScore = game.themScore
                roundValue = game.roundValue
                gameState = game.state
                isTrucoEnabled = false
            }
        )
    }
}

@Composable
fun HandOfElevenDialog(onPlay: () -> Unit, onQuit: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { },
        title = { Text("Mão de 11") },
        text = { Text("Deseja jogar ou correr?") },
        confirmButton = {
            Button(onClick = onPlay) {
                Text("Jogar")
            }
        },
        dismissButton = {
            Button(onClick = onQuit) {
                Text("Correr")
            }
        }
    )
}