package logic.chessboard


interface Chessboard {
	
	//chess gird
	/**
	 * 棋盘的行数
	 */
	val rowCount:Int
	/**
	 * 棋盘的列数
	 */
	val columnCount:Int
	/**
	 * 获取棋盘指定的棋子
	 */
	operator fun get(row:Int,column:Int): ChessType
	/**
	 * 修改棋盘指定位置的棋子
	 */
	operator fun set(row: Int,column: Int,chess: ChessType)
	/**
	 * 清空棋盘
	 */
	fun clear(){
		for (row in 0 until rowCount) {
			for (column in 0 until columnCount) {
				this[row, column]=ChessType.NONE
			}
		}
	}
	
	//game logic
	/**
	 * 正在下棋的棋手
	 */
	var holdingChess:ChessType
	/**
	 * 落子。
	 * 若落子成功，会交换棋手
	 * @param row 落子的行数
	 * @param column 落子的列数
	 * @return 落子的结果（是否合法、是否获胜等）
	 */
	fun makeMovement(row: Int, column: Int): MovementResult
	
}

sealed class GameState{
	class Playing(val holdingChess:ChessType) : GameState()
	class Win(val winner: ChessType) : GameState()
	class Draw : GameState()
}
sealed class MovementResult{
	class Illegal:MovementResult()
	class Legal(val newGameState:GameState):MovementResult()
}
