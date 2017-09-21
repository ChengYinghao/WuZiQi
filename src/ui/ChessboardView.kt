package ui

import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import logic.chessboard.ChessType
import logic.chessboard.Chessboard

class ChessboardView() : Pane() {
	
	init {
		this.setOnMouseClicked(this::onClick)
	}
	
	//ui
	private var boxSize = 20.0
	private val edgeSize get() = boxSize
	private var radius = boxSize / 3.0
	fun update() {
		updateSizes()
		updateChildren()
	}
	private fun updateChildren() {
		//clear
		children.clear()
		
		//background
		children += Rectangle(width, height, Color.BURLYWOOD)
		
		//draw mesh
		for (row in 0 until rowCount) {
			val y = edgeSize + boxSize * row
			children += Line(edgeSize, y, width - edgeSize, y).apply { strokeWidth = 3.0 }
		}
		for (column in 0 until columnCount) {
			val x = edgeSize + boxSize * column
			children += Line(x, edgeSize, x, height - edgeSize).apply { strokeWidth = 3.0 }
		}
		
		//draw chess
		for (row in 0 until rowCount) {
			for (column in 0 until columnCount) {
				
				val x = edgeSize + boxSize * column
				val y = edgeSize + boxSize * row
				
				when (chessboard!!.get(row, column)) {
					ChessType.BLACK -> children += Circle(x, y, radius, Color.BLACK)
					ChessType.WHITE -> children += Circle(x, y, radius, Color.WHITE)
					else -> { }
				}
				
			}
		}
	}
	private fun updateSizes() {
		//computation
		boxSize = Math.min(width / (columnCount + 1), height / (columnCount + 1))
		radius = boxSize / 3.0
		
		//update size
		width = (columnCount + 1) * boxSize
		height = (rowCount + 1) * boxSize
	}
	
	var onMovementListener: ((row: Int, column: Int) -> Unit)? = null
	private fun onClick(event: MouseEvent) {
		val column = Math.round((event.x - edgeSize) / boxSize).toInt()
		val row = Math.round((event.y - edgeSize) / boxSize).toInt()
		if (column !in 0 until columnCount || row !in 0 until rowCount) return
		onMovementListener?.invoke(row, column)
	}
	
	
	//logic
	constructor(chessboard: Chessboard):this(){
		this.chessboard=chessboard
	}
	var chessboard: Chessboard? = null
		set(value) {
			field = value
			update()
		}
	private val rowCount get() = chessboard?.rowCount ?: 0
	private val columnCount get() = chessboard?.columnCount ?: 0
	
}
