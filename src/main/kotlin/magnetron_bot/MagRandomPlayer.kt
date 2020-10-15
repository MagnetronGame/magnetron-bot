package magnetron_bot

import magnetron_game_kotlin.MagGame
import magnetron_game_kotlin.magnetron_state.MagAction
import magnetron_game_kotlin.magnetron_state.MagState

class MagRandomPlayer : MagPlayer {
    override fun pickAction(state: MagState): MagAction {
        val actions = MagGame.getPossibleActions(state)
        return actions.random()
    }
}