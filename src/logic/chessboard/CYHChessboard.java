package logic.chessboard;

import logic.ai.Evaluator;
import org.jetbrains.annotations.NotNull;

public class CYHChessboard implements Chessboard {
    private static final int DEFAULT_COLUMN_COUNT = Evaluator.COLUMN_COUNT;
    private static final int DEFAULT_ROW_COUNT = Evaluator.ROW_COUNT;

    private boolean isWhiteTurn = false;

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
        return board[row][column];
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
    public void clear() {
        for (int i = 0; i < DEFAULT_ROW_COUNT; i++) {
            for (int j = 0; j < DEFAULT_COLUMN_COUNT; j++) {
                board[i][j] = ChessType.NONE;
            }
        }
    }

    @NotNull
    @Override
    public MovementResult makeMovement(int row, int column) {
        //如果原来有棋子了，就不能再往上面下
        if (this.get(row, column) != ChessType.NONE) return new MovementResult.Illegal();

        //往上面放棋子
        ChessType oldHoldingChess = getHoldingChess();
        this.set(row, column, oldHoldingChess);

        //交换棋手
        ChessType newHoldingChess = oldHoldingChess.opposite();
        setHoldingChess(newHoldingChess);

        //判断局势
        boolean win = checkMovementWin(row, column);
        boolean draw = !win && checkDraw();
        GameState newGameState;
        if (win) {
            newGameState = new GameState.Win(oldHoldingChess);
        } else if (draw) {
            newGameState = new GameState.Draw();
        } else {
            newGameState = new GameState.Playing(newHoldingChess);
        }

        return new MovementResult.Legal(newGameState);

    }

    /**
     * 检查是否因为某个落子而获胜
     *
     * @param row    落子的行数
     * @param column 落子的列数
     * @return 是否因为该落子而胜利
     */
    private boolean checkMovementWin(int row, int column) {
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

    /**
     * 检查棋盘是否下满了（平手）。
     *
     * @return 棋盘是否下满了（平手）。
     */
    private boolean checkDraw() {
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
