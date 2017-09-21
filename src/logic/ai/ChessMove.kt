package logic.ai

data class ChessMove(
	/**考虑棋子位置不考虑局势的分数*/
	var pos:ChessPos,
	var score: Int,
	var layer: Int,
	var isWhiteTurn: Boolean

)
