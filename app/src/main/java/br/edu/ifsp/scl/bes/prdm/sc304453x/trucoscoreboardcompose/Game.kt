package br.edu.ifsp.scl.bes.prdm.sc304453x.trucoscoreboardcompose

class Game(private val target: Int = 12) {
    var usScore = 0
        private set

    var themScore = 0
        private set

    var roundValue = RoundValue.ONE
        private set

    var state = GameState.NORMAL
        private set

    fun addPoint(team: Team, points: Int) {
        println("Tentando adicionar ponto classe Game: points: $points, usScore = $usScore, themScore = $themScore, roundValue = $roundValue, state = $state")

        if (state == GameState.FINISHED) {
            println("O jogo já acabou!")
            return
        }

        // Adiciona pontos ao time dependendo de qual time foi passado
        when (team) {
            Team.US -> usScore = roundValue.points + points
            Team.THEM -> themScore += roundValue.points + points
        }

        println("Após adicionar ponto classe Game: usScore = $usScore, themScore = $themScore")

        // Finaliza a rodada e avalia o estado
        endRound()
    }
    fun callTruco() {
        if (state != GameState.NORMAL) return
        roundValue = roundValue.next()
    }

    fun resolveHandOfEleven(play: Boolean) {
        if (state != GameState.HAND_OF_ELEVEN) return

        if (!play) {
            if (usScore == 11) themScore += 1
            else usScore += 1
            evaluateState()
            return
        }

        roundValue = RoundValue.THREE
        state = GameState.NORMAL
    }

    fun resetGame() {
        usScore = 0
        themScore = 0
        state = GameState.NORMAL
    }

    private fun endRound() {
        roundValue = RoundValue.ONE
        evaluateState()
    }

    private fun evaluateState() {
        when {
            usScore >= target || themScore >= target -> {
                state = GameState.FINISHED
            }

            usScore == 11 && themScore == 11 -> {
                roundValue = RoundValue.THREE
                state = GameState.NORMAL
            }

            usScore == 11 && themScore < 11 -> {
                state = GameState.HAND_OF_ELEVEN
            }

            themScore == 11 && usScore < 11 -> {
                state = GameState.HAND_OF_ELEVEN
            }

            else -> {
                state = GameState.NORMAL
            }
        }
    }
}