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
	fun makeMovement(row: Int, column: Int): MovementResult {
		//如果原来有棋子了，就不能再往上面下
		if (this[row, column] != ChessType.NONE) return MovementResult.Illegal()
		
		//往上面放棋子
		val oldHoldingChess = holdingChess
		this[row, column] = oldHoldingChess
		
		//交换棋手
		val newHoldingChess = oldHoldingChess.opposite()
		holdingChess = newHoldingChess
		
		//判断局势
		val win = checkMovementWin(row, column)
		val draw = !win && checkDraw()
		val newGameState = when {
			win -> GameState.Win(oldHoldingChess)
			draw -> GameState.Draw()
			else -> GameState.Playing(newHoldingChess)
		}
		
		return MovementResult.Legal(newGameState)
	}
	/**
	 * 检查是否因为某个落子而获胜
	 * @param row 落子的行数
	 * @param column 落子的列数
	 * @return 是否因为该落子而胜利
	 */
	fun checkMovementWin(row: Int, column: Int):Boolean
	/**
	 * 检查棋盘是否下满了（平手）。
	 * @return 棋盘是否下满了（平手）。
	 */
	fun checkDraw():Boolean
	
	/**
	 * 不基于某个棋子，检查当前棋局状态。
	 * 注意则此方法在同时有多个玩家同时胜利的情况下不一定能准确判断
	 * @return 当前棋局的状态
	 */
	fun checkGameState():GameState{
		var draw=true
		for (row in 0 until rowCount) {
			for (column in 0 until columnCount) {
				val chess = this[row, column]
				//检查该棋子位是否赢了
				if(checkMovementWin(row, column)) return GameState.Win(chess)
				//检查是否下满棋盘（平局）了
				draw = draw && chess != ChessType.NONE
			}
		}
		return if (draw) GameState.Draw() else GameState.Playing(holdingChess)
	}
	
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
