package logic.ai;

import org.jetbrains.annotations.Nullable;

import logic.chessboard.Chessboard;

public interface ChessAI {
    @Nullable
    ChessPos nextMovement(Chessboard chessboard);
}
