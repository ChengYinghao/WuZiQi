package ui

import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import model.AbstractChessboard

interface ChessboardUI {
	
	/**
	 * 与UI绑定的棋盘
	 */
	val chessboard: AbstractChessboard
	/**
	 * 根据与UI绑定的棋盘更新内容
	 */
	fun update()
	
	/**
	 * UI窗口是否打开了
	 */
	var isShowing: Boolean
	/**
	 * 打开UI窗口
	 */
	fun show() {
		isShowing = true
	}
	/**
	 * 关闭UI窗口
	 */
	fun close() {
		isShowing = false
	}
	
	/**
	 * 用户对棋盘的落子操作的侦听
	 */
	var onMovementListener: ((row: Int, column: Int) -> Unit)?
}

class ZKLChessboardUI(override val chessboard: AbstractChessboard) : ChessboardUI {
	
	//visual
	init {
		ChessApplication.launch()
	}
	
	private var stage: Stage? = null
	private var chessboardView: ChessboardView? = null
	@Synchronized private fun initStage(): Stage {
		val chessboardView = ChessboardView(chessboard)
		chessboardView.onMovementListener = this.onMovementListener
		
		val stage = Stage()
		stage.title = "棋盘"
		stage.scene = Scene(chessboardView)
		stage.isResizable = false
		
		this.chessboardView=chessboardView
		this.stage=stage
		
		return stage
	}
	
	override var isShowing: Boolean = false
		get() = stage?.isShowing == true
		set(value) {
			Platform.runLater {
				when (value) {
					true -> stage?.show() ?: initStage().show()
					false -> stage?.close()
				}
				field = value
			}
		}
	
	override var onMovementListener: ((row: Int, column: Int) -> Unit)? = null
		set(value) {
			chessboardView?.onMovementListener = value
			field = value
		}
	
	
	//logic
	override fun update() {
		Platform.runLater {
			chessboardView?.update()
			stage?.sizeToScene()
		}
	}
}

