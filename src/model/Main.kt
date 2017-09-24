package model

import javafx.application.Platform
import logic.ai.ChessAI
import logic.ai.ChessPos
import logic.chessboard.*
import ui.ChessboardUI
import ui.ZKLChessboardUI
import kotlin.concurrent.thread

fun main(args: Array<String>) {
	MainSession().launch()
}

class MainSession {
	
	//logic
	private var isPlaying = true
	private val chessboardSize = 15
	private val chessboard: Chessboard = CYHChessboard(chessboardSize, chessboardSize)
	
	//ai
	private val playerAIMap:Map<ChessType,ChessAI?> = mapOf(ChessType.BLACK to null, ChessType.WHITE to null)
	private val playingAI get() = playerAIMap[holdingChess]
	
	//ui
	private val chessboardUI: ChessboardUI = ZKLChessboardUI(chessboard).also { ui ->
		ui.onMovementListener = { row, column ->
			if (playingAI == null && isPlaying) makeMovement(ChessPos(row, column))
		}
	}
	private val holdingChess get() = chessboard.holdingChess
	
	
	//session
	private fun makeMovement(chessPos: ChessPos) {
		val movementResult = chessboard.makeMovement(chessPos.row, chessPos.column)
		when (movementResult) {
			is MovementResult.Illegal -> {
				chessboardUI.message =
					if (playingAI == null) "You can't do this."
					else "AI of $holdingChess action illegally!"
			}
			is MovementResult.Legal -> {
				val newGameState = movementResult.newGameState
				when (newGameState) {
					is GameState.Win -> chessboardUI.message = "${newGameState.winner} win !!!"
					is GameState.Draw -> chessboardUI.message = "Game Over! Nobody win..."
					is GameState.Playing -> playingAI.let {
						if (it != null) aiMovement(it)
						else manMovement()
					}
				}
				isPlaying = newGameState is GameState.Playing
				chessboardUI.update()
			}
		}
	}
	private fun aiMovement(ai: ChessAI) {
		Platform.runLater {
			chessboardUI.message = "AI of $holdingChess is thinking..."
			thread {
				val pos = ai.nextMovement(chessboard)
				Platform.runLater {
					makeMovement(pos)
				}
			}
		}
	}
	private fun manMovement(isFirst: Boolean = false) {
		chessboardUI.message = if (isFirst) "$holdingChess first" else "$holdingChess next"
	}
	
	fun launch() {
		chessboardUI.update()
		chessboardUI.show()
		playingAI.let {
			if (it != null) aiMovement(it)
			else manMovement(true)
		}
	}
	
}