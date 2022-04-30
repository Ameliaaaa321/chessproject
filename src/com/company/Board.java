package com.company;

    // 这里stored用的是固定长度的数组。。。

    // 储存棋盘上的所有格子
    public class Board {
        Position[][] positions = new Position[9][9];
        Piece k0, k1;    // 双方的王，便于之后判断将军

        public Board() {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    Position p = new Position(i, j);
                    positions[i][j] = p;
                }
            }
        }
    }

    class StoreBoard {
        Board board;
        int[][][] stored = new int[9][9][31415];    // [坐标x][坐标y][每一次储存]，储存值为0~11，其中0~5依次代表黑方棋子种类，6~11数字代表白方
        int z = 0;    // z轴，代表每一次储存

        public StoreBoard(Board board) {
            this.board = board;
        }
    }

