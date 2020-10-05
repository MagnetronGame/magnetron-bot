package magnetron_bot

import magnetron_game_kotlin.magnetron_state.MagAction
import magnetron_game_kotlin.magnetron_state.MagState
import mcts.MctsPlayer

class MagMctsPlayer(moveSimulationCount: Int = 10) : MagPlayer {

    private val mctsPlayer = MctsPlayer(moveSimulationCount, useNodeStates = true)
    private val stateManager = MagnetronStateManager()

    override fun pickAction(state: MagState): MagAction {
        return mctsPlayer.chooseAction(state, stateManager) as MagAction
    }
}