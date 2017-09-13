package ui

import javafx.scene.Parent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class ChessBoardView:Parent(){
	
	fun update(chessBoardScanner:(row:Int,column:Int)->ChessType){
		children.clear()
	}
}

class ChessView(chessType: ChessType):Circle(){
	var chessType: ChessType = chessType
		set(value) {
			this.fill = when (chessType) {
				ChessType.BLACK -> Color.BLACK
				ChessType.WHITE -> Color.LIGHTGRAY
				ChessType.NONE -> Color.TRANSPARENT
			}
		}
}