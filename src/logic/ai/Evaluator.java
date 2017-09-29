package logic.ai;

import logic.chessboard.CYHChessboard;
import logic.chessboard.ChessType;


public class Evaluator implements BaseEvaluator {
    CYHChessboard cyhChessboard = new CYHChessboard(15, 15);
    final int ROW_COUNT = cyhChessboard.getRowCount();
    final int COLUMN_COUNT = cyhChessboard.getColumnCount();

    //位置价值表
    int[][] posValue = getPosValue(ROW_COUNT, COLUMN_COUNT);

    SituationType[][][] analysis = new SituationType[ROW_COUNT][COLUMN_COUNT][4];
    int[][] analysisRecord = new int[2][9];


    @Override
    public int evaluate(ChessType[][] board, boolean isWhiteTurn) {
        int WValue = 0;
        int BValue = 0;
        clearAnalysis();
        clearAnalysisRecord();
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                if (board[row][column] != ChessType.NONE) {
                    if (analysis[row][column][0] == SituationType.UNANALYZED) {
                        analysisHorizon(board, new ChessPos(row, column));
                    }
                    if (analysis[row][column][1] == SituationType.UNANALYZED) {
                        analysisVertical(board, new ChessPos(row, column));
                    }
                    if (analysis[row][column][2] == SituationType.UNANALYZED) {
                        analysisLeftOblique(board, new ChessPos(row, column));
                    }
                    if (analysis[row][column][3] == SituationType.UNANALYZED) {
                        analysisRightOblique(board, new ChessPos(row, column));
                    }
                }
            }
        }
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                for (int k = 0; k < 4; k++) {
                    if (analysis[row][column][k] != null) {
                        if (board[row][column] == ChessType.WHITE) {
                            System.out.println(analysis[row][column][k]);
                            switch (analysis[row][column][k]) {
                                case FIVE:
                                    analysisRecord[0][SituationType.FIVE.ordinal()]++;
                                case FOUR:
                                    analysisRecord[0][SituationType.FOUR.ordinal()]++;
                                case SFOUR:
                                    analysisRecord[0][SituationType.SFOUR.ordinal()]++;
                                case THREE:
                                    analysisRecord[0][SituationType.THREE.ordinal()]++;
                                case STHREE:
                                    analysisRecord[0][SituationType.STHREE.ordinal()]++;
                                case TWO:
                                    analysisRecord[0][SituationType.TWO.ordinal()]++;
                                case STWO:
                                    analysisRecord[0][SituationType.STWO.ordinal()]++;
                            }
                        } else if (board[row][column] == ChessType.BLACK) {
                            switch (analysis[row][column][k]) {
                                case FIVE:
                                    analysisRecord[1][SituationType.FIVE.ordinal()]++;
                                case FOUR:
                                    analysisRecord[1][SituationType.FOUR.ordinal()]++;
                                case SFOUR:
                                    analysisRecord[1][SituationType.SFOUR.ordinal()]++;
                                case THREE:
                                    analysisRecord[1][SituationType.THREE.ordinal()]++;
                                case STHREE:
                                    analysisRecord[1][SituationType.STHREE.ordinal()]++;
                                case TWO:
                                    analysisRecord[1][SituationType.TWO.ordinal()]++;
                                case STWO:
                                    analysisRecord[1][SituationType.STWO.ordinal()]++;
                            }
                        }
                    }
                }
            }
        }
        if (isWhiteTurn) {
            if (analysisRecord[0][SituationType.FIVE.ordinal()] > 0) {
                return 9999;
            } else if (analysisRecord[1][SituationType.FIVE.ordinal()] > 0) {
                return -9999;
            }
        } else {
            if (analysisRecord[0][SituationType.FIVE.ordinal()] > 0) {
                return -9999;
            } else if (analysisRecord[1][SituationType.FIVE.ordinal()] > 0) {
                return 9999;
            }
        }
        if (analysisRecord[0][SituationType.SFOUR.ordinal()] > 1) {
            analysisRecord[0][SituationType.FOUR.ordinal()]++;
        }
        if (analysisRecord[1][SituationType.SFOUR.ordinal()] > 1) {
            analysisRecord[1][SituationType.FOUR.ordinal()]++;
        }
        if (isWhiteTurn) {
            if (analysisRecord[0][SituationType.FOUR.ordinal()] > 0) {
                return 9990;
            } else if (analysisRecord[0][SituationType.SFOUR.ordinal()] > 0) {
                return 9980;
            } else if (analysisRecord[1][SituationType.FOUR.ordinal()] > 0) {
                return -9970;
            } else if (analysisRecord[1][SituationType.SFOUR.ordinal()] > 0 && analysisRecord[1][SituationType.THREE.ordinal()] > 0) {
                return -9960;
            } else if (analysisRecord[0][SituationType.THREE.ordinal()] > 0 && analysisRecord[1][SituationType.SFOUR.ordinal()] == 0) {
                return 9950;
            } else if (analysisRecord[1][SituationType.THREE.ordinal()] > 1 &&
                    analysisRecord[0][SituationType.FOUR.ordinal()] == 0 &&
                    analysisRecord[0][SituationType.SFOUR.ordinal()] == 0 &&
                    analysisRecord[0][SituationType.THREE.ordinal()] == 0) {
                return -9940;
            }
            if (analysisRecord[0][SituationType.THREE.ordinal()] > 1) {
                WValue += 2000;
            }
            if (analysisRecord[0][SituationType.THREE.ordinal()] == 0) {
                WValue += 200;
            }
            if (analysisRecord[1][SituationType.THREE.ordinal()] > 1) {
                BValue += 500;
            }
            if (analysisRecord[1][SituationType.THREE.ordinal()] == 0) {
                BValue += 100;
            }
            WValue += 10 * analysisRecord[0][SituationType.STHREE.ordinal()];

            BValue += 10 * analysisRecord[1][SituationType.STHREE.ordinal()];
            WValue += 4 * analysisRecord[0][SituationType.TWO.ordinal()];

            BValue += 4 * analysisRecord[1][SituationType.STWO.ordinal()];
            WValue += analysisRecord[0][SituationType.STWO.ordinal()];
            BValue += analysisRecord[1][SituationType.STWO.ordinal()];
        } else {
            if (analysisRecord[1][SituationType.FOUR.ordinal()] > 0) {
                return 9990;
            } else if (analysisRecord[1][SituationType.SFOUR.ordinal()] > 0) {
                return 9980;
            } else if (analysisRecord[0][SituationType.FOUR.ordinal()] > 0) {
                return -9970;
            } else if (analysisRecord[0][SituationType.SFOUR.ordinal()] > 0 && analysisRecord[0][SituationType.THREE.ordinal()] > 0) {
                return -9960;
            } else if (analysisRecord[1][SituationType.THREE.ordinal()] > 0 && analysisRecord[0][SituationType.SFOUR.ordinal()] == 0) {
                return 9950;
            } else if (analysisRecord[0][SituationType.THREE.ordinal()] > 1 &&
                    analysisRecord[1][SituationType.FOUR.ordinal()] == 0 &&
                    analysisRecord[1][SituationType.SFOUR.ordinal()] == 0 &&
                    analysisRecord[1][SituationType.THREE.ordinal()] == 0) {
                return -9940;
            }
            if (analysisRecord[1][SituationType.THREE.ordinal()] > 1) {
                WValue += 2000;
            }
            if (analysisRecord[1][SituationType.THREE.ordinal()] == 0) {
                WValue += 200;
            }
            if (analysisRecord[0][SituationType.THREE.ordinal()] > 1) {
                BValue += 500;
            }
            if (analysisRecord[0][SituationType.THREE.ordinal()] == 0) {
                BValue += 100;
            }
            WValue += 10 * analysisRecord[0][SituationType.STHREE.ordinal()];
            BValue += 10 * analysisRecord[1][SituationType.STHREE.ordinal()];
            WValue += 4 * analysisRecord[0][SituationType.TWO.ordinal()];
            BValue += 4 * analysisRecord[1][SituationType.STWO.ordinal()];
            WValue += analysisRecord[0][SituationType.STWO.ordinal()];
            BValue += analysisRecord[1][SituationType.STWO.ordinal()];
        }
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                if (board[row][column] != ChessType.NONE) {
                    if (board[row][column] == ChessType.BLACK) {
                        BValue += posValue[row][column];
                    } else {
                        WValue += posValue[row][column];
                    }
                }
            }
        }
        if (isWhiteTurn) {
            return (WValue - BValue);
        } else {
            return (BValue - WValue);
        }
    }

    private void clearAnalysisRecord() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                analysisRecord[i][j] = 0;
            }
        }
    }

    private void clearAnalysis() {
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                for (int k = 0; k < 4; k++) {
                    analysis[row][column][k] = SituationType.UNANALYZED;
                }
            }
        }
    }


    @Override
    public void analysisHorizon(ChessType[][] board, ChessPos pos) {
        ChessType[] analysisLine = new ChessType[COLUMN_COUNT];
        for (int i = 0; i < COLUMN_COUNT; i++) {
            analysisLine[i] = board[pos.getRow()][i];
        }
        SituationType[] linRecord = analysisLine(analysisLine, pos.getColumn());
        for (int i = 0; i < linRecord.length; i++) {
            analysis[pos.getRow()][i][0] = linRecord[i];
        }
    }

    @Override
    public void analysisVertical(ChessType[][] board, ChessPos pos) {
        ChessType[] analysisLine = new ChessType[ROW_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            analysisLine[i] = board[i][pos.getColumn()];
        }
        SituationType[] lineRecord = analysisLine(analysisLine, pos.getRow());
        for (int i = 0; i < lineRecord.length; i++) {
            analysis[i][pos.getColumn()][1] = lineRecord[i];
        }
    }

    @Override
    public void analysisLeftOblique(ChessType[][] board, ChessPos pos) {
        int length = ROW_COUNT - Math.max(pos.getRow(), pos.getColumn()) + Math.min(pos.getRow(), pos.getColumn());
        ChessType[] analysisLine = new ChessType[length];
        for (int k = Math.min(pos.getColumn(), pos.getRow()); k >= 0; k--) {
            analysisLine[Math.min(pos.getColumn(), pos.getRow()) - k] = board[pos.getRow() - k][pos.getColumn() - k];
        }
        for (int k = 1; k < ROW_COUNT - Math.max(pos.getColumn(), pos.getRow()); k++) {
            analysisLine[Math.min(pos.getColumn(), pos.getRow()) + k] = board[pos.getRow() + k][pos.getColumn() + k];
        }
        SituationType[] lineRecord = analysisLine(analysisLine, Math.min(pos.getColumn(), pos.getRow()));
        for (int i = 0; i <= Math.min(pos.getRow(), pos.getColumn()); i++) {
            analysis[pos.getRow() + i - Math.min(pos.getRow(), pos.getColumn())][pos.getColumn() + i - Math.min(pos.getRow(), pos.getColumn())][2] = lineRecord[i];
        }
        for (int i = Math.min(pos.getRow(), pos.getColumn()) + 1; i < lineRecord.length; i++) {
            analysis[pos.getRow() + i - Math.min(pos.getRow(), pos.getColumn())][pos.getColumn() + i - Math.min(pos.getRow(), pos.getColumn())][2] = lineRecord[i];
        }
    }

    @Override
    public void analysisRightOblique(ChessType[][] board, ChessPos pos) {
        int posIndex = Math.min(COLUMN_COUNT - 1 - pos.getRow(), pos.getColumn());
        int length = posIndex + 1 + Math.min(pos.getRow(), COLUMN_COUNT - 1 - pos.getColumn());
        ChessType[] analysisLine = new ChessType[length];
        for (int k = posIndex; k >= 0; k--) {
            analysisLine[posIndex - k] = board[pos.getRow() + k][pos.getColumn() - k];
        }
        for (int k = 1; k <= Math.min(pos.getRow(), COLUMN_COUNT - 1 - pos.getColumn()); k++) {
            analysisLine[posIndex + k] = board[pos.getRow() - k][pos.getColumn() + k];
        }
        SituationType[] lineRecord = analysisLine(analysisLine, posIndex);
        for (int i = 0; i < length; i++) {
            analysis[pos.getRow() + posIndex - i][pos.getColumn() + i - posIndex][3] = lineRecord[i];
        }
    }

    public SituationType[] analysisLine(ChessType[] line, int pos) {
        SituationType[] lineRecord = new SituationType[line.length];
        if (line.length < 4) {
            for (int i = 0; i < line.length; i++) {
                lineRecord[i] = SituationType.ANALYZED;
            }
            return lineRecord;
        }else{
            for (int i = 0; i < line.length; i++) {
                lineRecord[i] = SituationType.UNANALYZED;
            }
        }
        ChessType type = line[pos];
        //左边界和右边界用来记录已经有多少个同类型棋在一条线上
        int leftEdge = pos;
        int rightEdge = pos;
        while (leftEdge > 0) {
            if (line[leftEdge - 1] != type) {
                break;
            }
            leftEdge--;
        }
        while (rightEdge < line.length - 1) {
            if (line[rightEdge + 1] != type) {
                break;
            }
            rightEdge++;
        }
        int leftRange = leftEdge;
        int rightRange = rightEdge;
        while (leftRange > 0) {
            if (line[leftRange - 1] != ChessType.NONE && line[leftRange] != type) {
                break;
            }
            leftRange--;
        }
        while (rightRange < line.length - 1) {
            if (line[rightRange + 1] != ChessType.NONE && line[rightRange + 1] != type) {
                break;
            }
            rightRange++;
        }
        //该行不可能五子连珠
        if (rightRange - leftRange < 4) {
            for (int i = leftEdge; i <= rightEdge; i++) {
                lineRecord[i] = SituationType.ANALYZED;
            }
        } else if (rightEdge - leftEdge > 3) {
            for (int i = leftEdge; i <= rightEdge; i++) {
                lineRecord[i] = SituationType.ANALYZED;
            }
            lineRecord[pos] = SituationType.FIVE;
        } else if (rightEdge - leftEdge == 3) {
            boolean leftLimit = true;
            if (leftEdge > 0) {
                if (line[leftEdge - 1] == ChessType.NONE) {
                    leftLimit = false;
                }
                if (rightEdge < line.length - 1) {
                    if (line[rightEdge + 1] == ChessType.NONE) {
                        if (!leftLimit) {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.FOUR;
                        } else {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.SFOUR;
                        }
                    } else {
                        if (!leftLimit) {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.SFOUR;
                        } else {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                        }
                    }
                }
            }
        } else if (rightEdge - leftEdge == 2) {
            boolean leftLimit = true;
            SituationType leftSituation = SituationType.ANALYZED;
            if (leftEdge > 0) {
                if (line[leftEdge - 1] == ChessType.NONE) {
                    if (leftEdge > 1 && line[leftEdge - 2] == type) {
                        leftSituation = SituationType.SFOUR;
                    } else {
                        leftLimit = false;
                    }
                }
            }
            if (rightEdge < line.length - 1) {
                if (line[rightEdge + 1] == ChessType.NONE) {
                    if (rightEdge < line.length - 2 && line[rightEdge + 2] == type) {
                        lineRecord[rightEdge + 2] = SituationType.ANALYZED;
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        if (leftSituation == SituationType.SFOUR) {
                            lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                            lineRecord[pos] = SituationType.FOUR;
                        } else {
                            lineRecord[pos] = SituationType.SFOUR;
                        }
                    } else if (leftSituation == SituationType.SFOUR) {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                        lineRecord[pos] = SituationType.SFOUR;
                    } else if (!leftLimit) {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        lineRecord[pos] = SituationType.THREE;
                    } else {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        lineRecord[pos] = SituationType.STHREE;
                    }
                } else if (leftSituation == SituationType.SFOUR) {
                    lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                    for (int i = leftEdge; i <= rightEdge; i++) {
                        lineRecord[i] = SituationType.ANALYZED;
                    }
                    lineRecord[pos] = SituationType.SFOUR;
                } else if (!leftLimit) {
                    for (int i = leftEdge; i <= rightEdge; i++) {
                        lineRecord[i] = SituationType.ANALYZED;
                    }
                    lineRecord[pos] = SituationType.STHREE;
                } else {
                    for (int i = leftEdge; i <= rightEdge; i++) {
                        lineRecord[i] = SituationType.ANALYZED;
                    }
                }
            }
        } else if (rightEdge - leftEdge == 1) {
            SituationType leftSituation = SituationType.ANALYZED;
            boolean leftLimit = true;
            if (leftEdge > 0) {
                if (line[leftEdge - 1] == ChessType.NONE) {
                    leftLimit = false;
                    if (leftEdge > 2 && line[leftEdge - 3] == type && line[leftEdge - 2] == type) {
                        leftSituation = SituationType.SFOUR;
                    } else if (leftEdge > 1 && line[leftEdge - 2] == type) {
                        leftSituation = SituationType.STHREE;
                    } else {
                        leftSituation = SituationType.STWO;
                    }
                }
            }
            if (rightEdge < line.length- 1) {
                if (line[rightEdge + 1] == ChessType.NONE) {
                    if (rightEdge < line.length - 3 && line[rightEdge + 2] == type && line[rightEdge + 3] == type) {
                        if (leftSituation == SituationType.SFOUR) {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                            lineRecord[leftEdge - 3] = SituationType.ANALYZED;
                            lineRecord[rightEdge + 2] = SituationType.ANALYZED;
                            lineRecord[rightEdge + 3] = SituationType.ANALYZED;
                            lineRecord[pos] = SituationType.FOUR;
                        } else {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[rightEdge + 2] = SituationType.ANALYZED;
                            lineRecord[rightEdge + 3] = SituationType.ANALYZED;
                            lineRecord[pos] = SituationType.SFOUR;
                        }
                    } else if (rightEdge < line.length - 2 && line[rightEdge + 2] == type) {
                        if (leftSituation == SituationType.SFOUR && leftEdge > 2) {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                            lineRecord[leftEdge - 3] = SituationType.ANALYZED;
                            lineRecord[pos] = SituationType.SFOUR;
                        } else if (!leftLimit && leftEdge > 2) {
                            lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                            lineRecord[leftEdge - 3] = SituationType.ANALYZED;
                            lineRecord[pos] = SituationType.STHREE;
                        } else {
                            lineRecord[pos] = SituationType.ANALYZED;
                        }
                    } else {
                        if (leftSituation == SituationType.SFOUR && leftEdge > 2) {
                            lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                            lineRecord[leftEdge - 3] = SituationType.ANALYZED;
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.SFOUR;
                        } else if (leftSituation == SituationType.STHREE && leftEdge > 1) {
                            lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.STHREE;
                        } else if (!leftLimit) {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.TWO;
                        } else {
                            for (int i = leftEdge; i <= rightEdge; i++) {
                                lineRecord[i] = SituationType.ANALYZED;
                            }
                            lineRecord[pos] = SituationType.STWO;
                        }
                    }
                } else {
                    if (leftSituation == SituationType.SFOUR && leftEdge > 2) {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        lineRecord[leftEdge - 3] = SituationType.ANALYZED;
                        lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                        lineRecord[pos] = SituationType.SFOUR;
                    } else if (leftSituation == SituationType.STHREE && leftEdge > 1) {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        lineRecord[leftEdge - 2] = SituationType.ANALYZED;
                        lineRecord[pos] = SituationType.STHREE;
                    } else if (!leftLimit) {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                        lineRecord[pos] = SituationType.STWO;
                    } else {
                        for (int i = leftEdge; i <= rightEdge; i++) {
                            lineRecord[i] = SituationType.ANALYZED;
                        }
                    }
                }
            }
        }
        for (int i = leftEdge; i <= rightEdge; i++) {
            lineRecord[i] = SituationType.ANALYZED;
        }
        return lineRecord;
    }

    public int[][] getPosValue(int rowCount, int columnCount) {
        int[][] posValue = new int[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                int minimum = Math.min(i, j);
                int value = Math.min(rowCount - i - 1, columnCount - i - 1);
                if (value < minimum) {
                    minimum = value;
                }
                posValue[i][j] = minimum;
            }
        }
        return posValue;
    }
}
