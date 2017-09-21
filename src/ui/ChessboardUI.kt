package ui

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import logic.chessboard.Chessboard

interface ChessboardUI {
	
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
	
	/**
	 * 显示的信息
	 */
	var message: String
	
	/**
	 * 与UI绑定的棋盘
	 */
	val chessboard: Chessboard
	
	/**
	 * 根据与UI绑定的棋盘更新内容
	 */
	fun update()
	
}

class ZKLChessboardUI(override val chessboard: Chessboard) : ChessboardUI {
	
	//visual
	init {
		ChessApplication.launch()
	}
	
	private var stage: Stage? = null
	private var chessboardView: ChessboardView? = null
	private var titleView: Label? = null
	@Synchronized private fun initStage(): Stage {
		val titleView = Label().also { this.titleView = it }
		titleView.text = "(nothing to show)"
		
		val chessboardView = ChessboardView(chessboard).also { this.chessboardView = it }
		chessboardView.onMovementListener = this.onMovementListener
		
		val rootPane = GridPane()
		rootPane.vgap = 10.0
		rootPane.hgap = 10.0
		rootPane.add(titleView, 0, 0)
		rootPane.add(chessboardView, 0, 1)
		
		val stage = Stage().also { this.stage = it }
		stage.title = "棋盘"
		stage.scene = Scene(chessboardView)
		stage.isResizable = false
		
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
	
	override var message: String = ""
		set(value) {
			field = value
			Platform.runLater { titleView?.text = value }
		}
	
	//logic
	override fun update() {
		Platform.runLater {
			chessboardView?.update()
			stage?.sizeToScene()
		}
	}
}

