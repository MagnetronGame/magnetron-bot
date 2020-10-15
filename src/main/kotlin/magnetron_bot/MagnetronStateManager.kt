package magnetron_bot

import magnetron_game_kotlin.MagGame
import magnetron_game_kotlin.magnetron_state.MagAction
import magnetron_game_kotlin.magnetron_state.MagState
import mcts.Action
import mcts.State
import mcts.StateManager


class MagnetronStateManager : StateManager {
    val initialState =  MagGame.createInitialState()

    override fun getInitialState(): State = initialState

    override fun getNextPlayer(state: State): Int {
        state as MagState
        return state.playPhase.nextAvatarIndex
    }

    override fun getPlayerCount(): Int {
        return initialState.staticState.avatarCount
    }

    override fun getPlayersEvaluation(state: State): List<Float> {
        state as MagState
        return if (state.lifecycleState.isTerminal) {
            val playersWonIndices = state.lifecycleState.avatarIndicesWon
            val playersWonCount = playersWonIndices.size
            val playerCount = state.avatars.size
            val playersEval = (0 until playerCount).map {
                val winScore = if (playersWonIndices.contains(it)) 1f else 0f
                val sharedWinScore = winScore / playersWonCount
                sharedWinScore
            }
            playersEval
        } else {
            val avatarCoins = state.avatars.map {
                it.avatarData.coins
            }
            val totalPickedCoins = avatarCoins.sum()
            val eval = avatarCoins.map {
                if (totalPickedCoins != 0)
                    it.toFloat() / totalPickedCoins
                else
                    0f
            }
            eval
        }
    }

    override fun getPossibleActions(state: State): Collection<Action> {
        state as MagState
        return MagGame.getPossibleActions(state)
    }

    override fun isTerminalState(state: State): Boolean {
        state as MagState
        return state.lifecycleState.isTerminal
    }

    override fun performAction(state: State, action: Action): State {
        state as MagState
        action as MagAction
        return MagGame.performAction(state, action)
    }
}