package com.company;

    // 这里stored用的是固定长度的数组。。。


import java.util.ArrayList;

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

        ArrayList<Board> stored = new ArrayList<>();

        public StoreBoard() {
        }
        public void addInBoard(Board board){
            stored.add(board);
        }
    }

