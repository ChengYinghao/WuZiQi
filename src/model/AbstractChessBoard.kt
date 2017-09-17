package model


interface AbstractChessboard {
	val rowCount:Int
	val columnCount:Int
	fun get(row:Int,column:Int): ChessType
}