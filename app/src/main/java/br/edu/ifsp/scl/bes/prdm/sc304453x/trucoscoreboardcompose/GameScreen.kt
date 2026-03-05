package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboardcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun GameScreen() {
    var usScore by remember { mutableStateOf(0) }
    var themScore by remember { mutableStateOf(0) }
    var roundValue by remember { mutableStateOf(RoundValue.ONE) }
    var gameState by remember { mutableStateOf(GameState.NORMAL) }

    val game = Game()

    val addPointTeamUs = {
        game.addPoint(Team.US)
        usScore = game.usScore
        themScore = game.themScore
        roundValue = game.roundValue
        gameState = game.state
    }

    val addPointTeamThem = {
        game.addPoint(Team.THEM)
        usScore = game.usScore
        themScore = game.themScore
        roundValue = game.roundValue
        gameState = game.state
    }


}