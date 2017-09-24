package model

import javafx.application.Platform
import logic.ai.CYHChessAI
import logic.ai.ChessAI
import logic.chessboard.*
import ui.ChessboardUI
import ui.ZKLChessboardUI
import kotlin.concurrent.thread

fun main(args: Array<String>) {
	
	val chessBoard: Chessboard = CYHChessboard(15, 15)
	val chessboardUI: ChessboardUI = ZKLChessboardUI(chessBoard)
	
	val chessAI: ChessAI = CYHChessAI()
	val playerMap = mapOf(ChessType.BLACK to false, ChessType.WHITE to true)
	
	fun isAI() = playerMap[chessBoard.holdingChess] == true
	fun makeMovement(row: Int, column: Int) {
		val movementResult = chessBoard.makeMovement(row, column)
		when (movementResult) {
			is MovementResult.Illegal -> chessboardUI.message = if (isAI()) "AI action illegally!" else "You can't do this."
			is MovementResult.Legal -> {
				val newGameState = movementResult.newGameState
				when (newGameState) {
					is GameState.Win -> chessboardUI.message = "${newGameState.winner.name} win !!!"
					is GameState.Draw -> chessboardUI.message = "Game Over! Nobody win..."
					is GameState.Playing -> {
						if (!isAI()) {
							chessboardUI.message = "${newGameState.holdingChess}"
						} else {
							chessboardUI.message = "AI is thinking..."
							thread {
								val pos = chessAI.nextMovement(chessBoard)
								Platform.runLater { makeMovement(pos.row, pos.column) }
							}
						}
					}
				}
				chessboardUI.update()
			}
		}
	}
	
	chessboardUI.onMovementListener = {row, column -> if(!isAI()) makeMovement(row, column) }
	chessboardUI.message = "BLACK first"
	chessboardUI.update()
	chessboardUI.show()
	
}