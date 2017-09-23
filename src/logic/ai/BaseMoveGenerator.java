package logic.ai;

import logic.chessboard.ChessType;

import java.util.ArrayList;

interface BaseMoveGenerator {
    ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn);


}
