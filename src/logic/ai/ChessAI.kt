package logic.ai

import logic.chessboard.Chessboard

interface ChessAI {
	fun nextMovement(chessboard: Chessboard): ChessPos?
}
