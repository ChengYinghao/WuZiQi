package logic;

import model.ChessType;

public class Evaluator implements BaseEvaluator {
    //位置价值表
    int[][] posValue = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                         {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
                         {0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
                         {0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
                         {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};


    @Override
    public int evaluate(ChessType[][] board, boolean isWhiteTurn) {
        return 0;
    }

    @Override
    public int analysisHorizon(ChessType[][] board, int row, int column) {
        return 0;
    }

    @Override
    public int analysisVertical(ChessType[][] board, int row, int column) {
        return 0;
    }

    @Override
    public int analysisLeftOblique(ChessType[][] board, int row, int column) {
        return 0;
    }

    @Override
    public int analysisRightOblique(ChessType[][] board, int row, int column) {
        return 0;
    }

    public SituationType analysisLine(ChessType[] line, ChessPos pos, ChessType type){
        return SituationType.FIVE;
    }
}
