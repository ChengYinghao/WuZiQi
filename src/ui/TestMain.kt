package ui

import model.AbstractChessboard
import model.ChessType

fun main(args: Array<String>) {
	val chessboard = object : AbstractChessboard {
		override val rowCount: Int = 10
		override val columnCount: Int = 10
		override fun get(row: Int, column: Int): ChessType {
			return when ((row + column) % 3) {
				0 -> ChessType.BLACK
				1 -> ChessType.WHITE
				else -> ChessType.NONE
			}
		}
	}
	
	val ui = ZKLChessboardUI(chessboard)
	ui.show()
	ui.update()
	ui.onMovementListener = { row,column->
		println("row $row , column $column")
	}
}