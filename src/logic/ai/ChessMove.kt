package logic.ai

data class ChessMove(
	/**考虑棋子位置不考虑局势的分数*/
	val score: Int,
	val layer: Int,
	val isWhiteTurn: Boolean
)
