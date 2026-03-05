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
    val game = Game()

    var usScore by remember { mutableStateOf(game.usScore) }
    var themScore by remember { mutableStateOf(game.themScore) }
    var roundValue by remember { mutableStateOf(RoundValue.ONE) }
    var gameState by remember { mutableStateOf(GameState.NORMAL) }


    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("")}

    val addPointTeamUs = {
        println("Estado do jogo na UI antes de adicionar pontos: usScore = $usScore, themScore = $themScore, roundValue = $roundValue, gameState = $gameState")
        game.addPoint(Team.US, usScore)
        println("Após adicionar ponto resultado UI: usScore = ${game.usScore}, themScore = ${game.themScore}, roundValue = ${game.roundValue}, gameState = ${game.state}")
        usScore = game.usScore
        roundValue = game.roundValue
        gameState = game.state
    }

    val addPointTeamThem = {
        println("Estado do jogo antes de adicionar pontos: usScore = $usScore, themScore = $themScore, roundValue = $roundValue, gameState = $gameState")
        game.addPoint(Team.THEM, themScore)
        themScore = game.themScore
        roundValue = game.roundValue
        gameState = game.state
    }

    Column(modifier = modifier) {
        Text("Pontuação - US: $usScore | THEM: $themScore", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height((16.dp)))

        Text("Estado do Jogo: $gameState", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height((16.dp)))

        Button(onClick = addPointTeamUs) {
            Text("Adicionar ponto para US")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = addPointTeamThem) {
            Text("Adicionar ponto para Them")
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
            },
            onQuit = {
                game.resolveHandOfEleven(false)
                usScore = game.usScore
                themScore = game.themScore
                roundValue = game.roundValue
                gameState = game.state
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