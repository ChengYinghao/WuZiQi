package logic.chessboard

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CYHChessboardTest {
	
	@Test
	fun emptyAfterInitialization() {
		val chessboard = CYHChessboard()
		for (row in 0 until chessboard.rowCount) {
			for (column in 0 until chessboard.columnCount) {
				val chess = chessboard[row, column]
				val message = "After initialization, not all the place is ChessType.NONE.(chessboard[$row,$column]=$chess)"
				assertEquals(chess, ChessType.NONE, message)
			}
		}
	}
	
	@Test
	fun setter() {
		val chessboard = CYHChessboard()
		repeat(1000) {
			val row = randomInt(chessboard.rowCount)
			val column = randomInt(chessboard.columnCount)
			val newChess = ChessType.values().run { get(randomInt(size)) }
			
			val oldChess = chessboard[row, column]
			chessboard[row, column] = newChess
			assertEquals(chessboard[row, column], newChess,
				"The set operation is failed. (oldChess=$oldChess,newChess=$newChess)")
		}
	}
	
	@Test
	fun clear() {
		val chessboard = CYHChessboard()
		repeat(100) {
			val row = randomInt(chessboard.rowCount)
			val column = randomInt(chessboard.columnCount)
			val newChess = ChessType.values().run { get(randomInt(size)) }
			
			val oldChess = chessboard[row, column]
			chessboard[row, column] = newChess
			assertEquals(chessboard[row, column], newChess,
				"The set operation is failed. (oldChess=$oldChess,newChess=$newChess)")
		}
		chessboard.clear()
		for (row in 0 until chessboard.rowCount) {
			for (column in 0 until chessboard.columnCount) {
				val chess = chessboard[row, column]
				val message = "After clear, not all the place is ChessType.NONE.(chessboard[$row,$column]=$chess)"
				assertEquals(chess, ChessType.NONE, message)
			}
		}
	}
	
}