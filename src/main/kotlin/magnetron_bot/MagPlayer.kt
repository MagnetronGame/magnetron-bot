package magnetron_bot

import magnetron_game_kotlin.magnetron_state.MagAction
import magnetron_game_kotlin.magnetron_state.MagState

interface MagPlayer {
    fun pickAction(state: MagState): MagAction
}