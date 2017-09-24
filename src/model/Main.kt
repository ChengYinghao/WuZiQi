package model

import javafx.application.Platform
import logic.ai.CYHChessAI
import logic.chessboard.*
import ui.ChessboardUI
import ui.ZKLChessboardUI
import kotlin.concurrent.thread

fun main(args: Array<String>) {
	MainSession().launch()
}

class MainSession {
	
	private val chessboardSize = 15
	val chessboard: Chessboard = CYHChessboard(chessboardSize,chessboardSize)
	val chessboardUI: ChessboardUI = ZKLChessboardUI(chessboard).apply {
		onMovementListener = {row, column -> if(playingAI==null) makeMovement(row, column) }
	}
	val playerAIMap = mapOf(ChessType.BLACK to null, ChessType.WHITE to CYHChessAI())
	
	private val holdingChess get() = chessboard.holdingChess
	private val playingAI get() = playerAIMap[holdingChess]
	
	fun launch(){
		chessboardUI.message = "BLACK first"
		chessboardUI.update()
		chessboardUI.show()
	}
	
	private fun makeMovement(row: Int, column: Int) {
		val movementResult = chessboard.makeMovement(row, column)
		when (movementResult) {
			is MovementResult.Illegal -> {
				chessboardUI.message =
					if (playingAI == null) "You can't do this."
					else "AI of $holdingChess action illegally!"
			}
			is MovementResult.Legal -> {
				val newGameState = movementResult.newGameState
				when (newGameState) {
					is GameState.Win -> chessboardUI.message = "${newGameState.winner.name} win !!!"
					is GameState.Draw -> chessboardUI.message = "Game Over! Nobody win..."
					is GameState.Playing -> {
						val playingAI=playingAI
						if (playingAI==null) {
							chessboardUI.message = "${newGameState.holdingChess}"
						} else {
							chessboardUI.message = "AI is thinking..."
							thread {
								val pos = playingAI.nextMovement(chessboard)
								Platform.runLater { makeMovement(pos.row, pos.column) }
							}
						}
					}
				}
				chessboardUI.update()
			}
		}
	}
	
}