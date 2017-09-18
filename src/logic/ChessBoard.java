package logic;

import model.AbstractChessboard;
import model.ChessType;
import org.jetbrains.annotations.NotNull;

public class ChessBoard implements AbstractChessboard {
    protected static final int ROW_COUNT = 15;
    protected static final int COLUMN_COUNT = 15;
    boolean isWhiteTurn = false;

    //棋盘
    ChessType[][] board = new ChessType[COLUMN_COUNT][ROW_COUNT];

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
