package ui

import javafx.application.Platform
import javafx.scene.layout.Pane
import javafx.stage.Stage


class ChessBoardUI(val rowCount: Int, val columnCount: Int) {
	
	var stage: Stage? = null
	var rootPane: Pane? = null
	
	fun show() {
		ChessApplication.launch()
		Platform.runLater {
			this.stage = Stage().apply {
				title = "棋盘"
				//todo add scene
				show()
			}
		}
	}
	private fun prepareStage(stage: Stage){
	
	}
	
}

