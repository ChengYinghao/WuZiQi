package logic.ai;

import logic.chessboard.Chessboard;

public interface ChessAI {
    ChessPos nextMovement(Chessboard chessboard);
}
