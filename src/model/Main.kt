package model

import javafx.application.Platform
import logic.ai.CYHChessAI
import logic.ai.ChessAI
import logic.chessboard.*
import ui.ChessboardUI
import ui.ZKLChessboardUI
import kotlin.concurrent.thread

fun main(args: Array<String>) {
	MainSession().launch()
}

class MainSession {
	
	private val chessboardSize = 15
	private val chessboard: Chessboard = CYHChessboard(chessboardSize, chessboardSize)

	//ai
	private val playerAIMap:Map<ChessType,ChessAI?> = mapOf(ChessType.BLACK to null, ChessType.WHITE to CYHChessAI())
	private val playerNameMap:Map<ChessType,String> = mapOf(ChessType.BLACK to "黑棋", ChessType.WHITE to "白棋")
	private val playingAI get() = playerAIMap[holdingChess]
	
	//ui
	private val chessboardUI: ChessboardUI = ZKLChessboardUI(chessboard).also { ui ->
		ui.onMovementListener = { row, column ->
			if (playingAI == null && !chessboard.isGameOver) {
				makeMovement(row, column)
			}
		}
	}
	private val holdingChess get() = chessboard.holdingChess
	
	
	//session
	private fun makeMovement(row:Int,column:Int){
		val movementResult = chessboard.makeMovement(row, column)
		when (movementResult) {
			is MovementResult.Illegal -> {
				chessboardUI.message =
					if (playingAI == null) "你不能这么下！"
					else "${playerNameMap[holdingChess]}AI下的棋不符合规则！"
			}
			is MovementResult.Legal -> {
				val newGameState = movementResult.newGameState
				when (newGameState) {
					is GameState.Win -> chessboardUI.message = "${playerNameMap[newGameState.winner]}赢了！！"
					is GameState.Draw -> chessboardUI.message = "呃，平局……"
					is GameState.Playing -> playingAI.let {
						if (it != null) aiMovement(it)
						else manMovement()
					}
				}
				chessboardUI.update()
			}
		}
	}
	
	private fun aiMovement(ai: ChessAI) {
		Platform.runLater {
			chessboardUI.message = "${playerNameMap[holdingChess]}AI正在思考……"
			val aiThread = thread(false) {
				val pos = ai.nextMovement(chessboard)
				if (pos != null) {
					Platform.runLater {
						makeMovement(pos.row, pos.column)
					}
				} else {
					Platform.runLater {
						chessboardUI.message = "${playerNameMap[holdingChess]}AI弃子而逃！！"
					}
				}
			}
			aiThread.setUncaughtExceptionHandler { _, e ->
				Platform.runLater {
					Platform.exit()
					throw e
				}
			}
			aiThread.start()
		}
	}
	private fun manMovement(isFirst: Boolean = false) {
		chessboardUI.message = if (isFirst) "${playerNameMap[holdingChess]}先下" else "到${playerNameMap[holdingChess]}了"
	}
	
	fun launch() {
		chessboardUI.update()
		chessboardUI.show()
		playingAI.let {
			if (it != null) aiMovement(it)
			else manMovement(true)
		}
	}
	
}