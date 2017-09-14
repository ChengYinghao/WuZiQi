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

    public SituationType analysisLine(ChessType[] line, int pos, ChessType type) {
        int leftEdge = pos;
        int rightEdge = pos;
        while (leftEdge > 0) {
            if (line[leftEdge - 1] != type) {
                break;
            }
            leftEdge--;
        }
        while (rightEdge < ChessBoard.ROW_COUNT) {
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
        while (rightEdge < ChessBoard.ROW_COUNT) {
            if (line[rightEdge + 1] != ChessType.NONE && line[leftRange] != type) {
                break;
            }
            leftRange--;
        }
        if (rightRange - leftRange < 4) {
            return SituationType.MEANINGLESS;
        }
        if (rightEdge - leftEdge > 3) {
            return SituationType.FIVE;
        }
        if (rightEdge - leftEdge == 3) {
            boolean leftLimit = true;
            if (leftEdge > 0) {
                if (line[leftEdge - 1] == ChessType.NONE) {
                    leftLimit = false;
                }
                if (rightEdge < ChessBoard.ROW_COUNT) {
                    if (line[rightEdge + 1] == ChessType.NONE) {
                        if (!leftLimit) {
                            return SituationType.FOUR;
                        } else {
                            return SituationType.SFOUR;
                        }
                    } else {
                        if (!leftLimit) {
                            return SituationType.SFOUR;
                        } else {
                            return SituationType.MEANINGLESS;
                        }
                    }
                }
            }
        }
        if (rightEdge - leftEdge == 2) {
            boolean leftLimit = true;
            SituationType leftSituation = SituationType.MEANINGLESS;
            if (leftEdge > 0) {
                if (line[leftEdge - 1] == ChessType.NONE) {
                    if (leftEdge > 1 && line[leftEdge - 2] == type) {
                        leftSituation = SituationType.SFOUR;
                    } else {
                        leftLimit = false;
                    }
                }
            }
            if (rightEdge < ChessBoard.ROW_COUNT) {
                if (line[rightEdge + 1] == ChessType.NONE) {
                    if (rightEdge < ChessBoard.ROW_COUNT - 1 && line[rightEdge + 2] == type) {
                        if (leftSituation == SituationType.SFOUR) {
                            return SituationType.FOUR;
                        } else {
                            return SituationType.SFOUR;
                        }
                    } else if (leftSituation == SituationType.SFOUR) {
                        return SituationType.SFOUR;
                    } else if (!leftLimit) {
                        return SituationType.THREE;
                    } else {
                        return SituationType.STHREE;
                    }
                } else if (leftSituation == SituationType.SFOUR) {
                    return SituationType.SFOUR;
                } else if (!leftLimit) {
                    return SituationType.STHREE;
                } else {
                    return SituationType.MEANINGLESS;
                }
            }
        }
        if (rightEdge - leftEdge == 1) {
            SituationType leftSituation = SituationType.MEANINGLESS;
            boolean leftLimit = true;
            if(leftEdge>0){
                if(line[leftEdge-1]==ChessType.NONE){
                    leftLimit=false;
                    if(leftEdge>2&&line[leftEdge-3]==type&&line[leftEdge-2]==type){
                        return SituationType.SFOUR;
                    }
                    else if(leftEdge>1&&line[leftEdge-2]==type){
                        leftSituation=SituationType.STHREE;
                    }
                    else {
                        leftSituation=SituationType.STWO;
                    }
                }
            }
            if(rightEdge<ChessBoard.ROW_COUNT){
                if(line[rightEdge+1]==ChessType.NONE){
                    if(rightEdge<ChessBoard.ROW_COUNT-2&&line[rightEdge+2]==type&&line[rightEdge+3]==type){
                        return SituationType.SFOUR;
                    }
                    else if(rightEdge<ChessBoard.ROW_COUNT-1&&line[rightEdge+2]==type){
                        if(leftSituation==SituationType.SFOUR){
                            return SituationType.SFOUR;
                        }
                        else if (!leftLimit){
                            return SituationType.STHREE;
                        }
                        else {
                            return SituationType.MEANINGLESS;
                        }
                    }
                    else {
                        if(leftSituation==SituationType.SFOUR){
                            return SituationType.SFOUR;
                        }
                        else if(leftSituation==SituationType.STHREE){
                            return SituationType.STHREE;
                        }
                        else if(!leftLimit){
                            return SituationType.TWO;
                        }
                        else {
                            return SituationType.STWO;
                        }
                    }
                }
                else {
                    if(leftSituation==SituationType.SFOUR){
                        return SituationType.SFOUR;
                    }
                    else if(leftSituation==SituationType.STHREE){
                        return SituationType.STHREE;
                    }
                    else if(!leftLimit){
                        return SituationType.STWO;
                    }
                    else {
                        return SituationType.MEANINGLESS;
                    }
                }
            }
        }
        return SituationType.MEANINGLESS;
    }
}
