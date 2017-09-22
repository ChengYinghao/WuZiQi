package logic.chessboard;

import logic.ai.Evaluator;
import org.jetbrains.annotations.NotNull;

public class CYHChessboard implements Chessboard {
    public static final int DEFAULT_COLUMN_COUNT = Evaluator.COLUMN_COUNT;
    public static final int DEFAULT_ROW_COUNT = Evaluator.ROW_COUNT;

    boolean isWhiteTurn = false;

    //棋盘
    public ChessType[][] board = new ChessType[Evaluator.COLUMN_COUNT][Evaluator.ROW_COUNT];

    @Override
    public int getRowCount() {
        return DEFAULT_ROW_COUNT;
    }

    @Override
    public int getColumnCount() {
        return DEFAULT_COLUMN_COUNT;
    }

    @NotNull
    @Override
    public ChessType get(int row, int column) {
        ChessType type = board[row][column];
        return type;
    }

    @Override
    public void set(int row, int column, @NotNull ChessType chess) {
        board[row][column] = chess;
    }

    @NotNull
    @Override
    public ChessType getHoldingChess() {
        if (isWhiteTurn) {
            return ChessType.WHITE;
        } else {
            return ChessType.BLACK;
        }
    }

    @Override
    public void setHoldingChess(@NotNull ChessType chessType) {
        isWhiteTurn = chessType != ChessType.BLACK;
    }

    @Override
    public boolean checkMovementWin(int row, int column) {
        int count = 1;
        //检查横向左边
        for (int i = 1; i < 5; i++) {
            if (column - i >= 0) {
                if (board[row][column - i] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        //检查横向右边
        for (int i = 1; i < 5; i++) {
            if (column + i < DEFAULT_COLUMN_COUNT) {
                if (board[row][column + i] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查纵向上边
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0) {
                if (board[row - i][column] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        //检查纵向下边
        for (int i = 1; i < 5; i++) {
            if (row + i < DEFAULT_ROW_COUNT) {
                if (board[row + i][column] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查左上角
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0 && column - i >= 0) {
                if (board[row - i][column - i] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        //检查右下角
        for (int i = 1; i < 5; i++) {
            if (row + i < DEFAULT_ROW_COUNT && column + i < DEFAULT_COLUMN_COUNT) {
                if (board[row + i][column + i] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查左下角
        for (int i = 1; i < 5; i++) {
            if (row + i < DEFAULT_ROW_COUNT && column - i >= 0) {
                if (board[row + i][column - i] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        //检查右上角
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0 && column + i < DEFAULT_COLUMN_COUNT) {
                if (board[row - i][column + i] != board[row][column]) {
                    break;
                }
            }
            count++;
        }
        return count >= 5;
    }

    @Override
    public boolean checkDraw() {
        boolean isTie = true;
        for (int i = 0; i < DEFAULT_ROW_COUNT; i++) {
            for (int j = 0; j < DEFAULT_COLUMN_COUNT; j++) {
                if (board[i][j] == ChessType.NONE) {
                    isTie = false;
                }
            }
        }
        return isTie;
    }
}
