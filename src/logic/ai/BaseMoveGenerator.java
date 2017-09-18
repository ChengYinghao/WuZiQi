package logic.ai;

import java.util.ArrayList;

import logic.chessboard.ChessType;

public interface BaseMoveGenerator {
    ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn);


}
