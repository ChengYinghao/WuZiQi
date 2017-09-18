package logic.chessboard

interface Chessboard {
	/**
	 * 棋盘应该有行数和列数
	 */
	val rowCount:Int
	/**
	 * 棋盘应该有行数和列数
	 */
	val columnCount:Int
	/**
	 * 可以获取棋盘任意位置的棋子
	 */
	fun get(row:Int,column:Int): ChessType
}