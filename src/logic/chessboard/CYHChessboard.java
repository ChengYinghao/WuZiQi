package logic.chessboard;

import org.jetbrains.annotations.NotNull;

import logic.ai.Evaluator;

public class CYHChessboard implements Chessboard {
    public static final int DEFAULT_COLUMN_COUNT = Evaluator.COLUMN_COUNT;
    public static final int DEFAULT_ROW_COUNT = Evaluator.ROW_COUNT;
    
    boolean isWhiteTurn = false;

    //棋盘
    ChessType[][] board = new ChessType[Evaluator.COLUMN_COUNT][Evaluator.ROW_COUNT];

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @NotNull
    @Override
    public ChessType get(int row, int column) {
        return null;
    }
}