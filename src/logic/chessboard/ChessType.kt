package logic.chessboard

enum class ChessType {
	BLACK, WHITE, NONE;
	
	internal fun opposite() = when (this) {
		BLACK -> WHITE
		WHITE -> BLACK
		else -> NONE
	}
	
}
