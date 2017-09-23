package model

import logic.chessboard.CYHChessboard
import logic.chessboard.Chessboard
import logic.chessboard.GameState
import logic.chessboard.MovementResult
import ui.ChessboardUI
import ui.ZKLChessboardUI

fun main(args: Array<String>) {
	
	val chessBoard: Chessboard = CYHChessboard()
	
	val ui: ChessboardUI = ZKLChessboardUI(chessBoard)
	ui.onMovementListener = {row, column ->
		val movementResult = chessBoard.makeMovement(row, column)
		when(movementResult){
			is MovementResult.Illegal -> ui.message="You can't do this."
			is MovementResult.Legal -> {
				val newGameState = movementResult.newGameState
				when(newGameState){
					is GameState.Win -> ui.message="${newGameState.winner.name} win !!!"
					is GameState.Draw -> ui.message="Game Over! Nobody win..."
					is GameState.Playing -> ui.message="${newGameState.holdingChess}"
				}
			}
		}
	}
	ui.message = "BLACK first"
	ui.show()
	
}