package magnetron_bot

import magnetron_game_kotlin.MagGame
import magnetron_game_kotlin.magnetron_state.MagState
import kotlin.test.Test

class MagnetronMctsTest {

    @Test fun magnetronMcts() {
        (0 until 10)
                .forEach {_ ->
                    val states = playGame()
                    val terminalState = states.last()
                    println("""
                        Game length: ${states.size}
                        Winners: ${terminalState.lifecycleState.avatarIndicesWon}
                        Coins: ${terminalState.avatars.map { it.avatarData.coins }}
                        
                    """.trimIndent())
                }
    }

    private fun playGame(): List<MagState> {
        val players = listOf(
                MagMctsPlayer(
                        moveSimulationCount = 500
                ),
                MagMctsPlayer(
                        moveSimulationCount = 100
                ),
                MagRandomPlayer(),
                MagMctsPlayer(
                        moveSimulationCount = 300
                )
        )

        val initialState = MagGame.createInitialState()
        var states = mutableListOf(initialState)
        do {
            val currState = states.last()
            val playerTurn = currState.playPhase.nextAvatarIndex
            val player = players[playerTurn]
            val action = player.pickAction(currState)
            val nextState = MagGame.performAction(currState, action)
            states.add(nextState)
        } while (!nextState.lifecycleState.isTerminal)
        return states
    }
}