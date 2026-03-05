package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboardcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

    Column(modifier = modifier) {
        Text("Pontuação - US: $usScore | THEM: $themScore", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height((16.dp)))

        Spacer(modifier = Modifier.height((16.dp)))

        Button(onClick = addPointTeamUs, enabled = !isGameFinished) {
            Text("+${roundValue.points}")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = addPointTeamThem, enabled = !isGameFinished) {
            Text("+${roundValue.points}")
        }

        Text("Valendo: ${roundValue.points}")
        Button(onClick = callTruco, enabled = (!isGameFinished && isTrucoEnabled )) {
            Text("Truco")
        }

        Button(onClick = reset) {
            Text("Resetar")
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