package com.company;

    // 这里stored用的是固定长度的数组。。。


import java.util.ArrayList;

// 储存棋盘上的所有格子
    public class Board {
        Position[][] positions = new Position[8][8];
        Piece k0, k1;    // 双方的王，便于之后判断将军
        int val = -1;    // 某局面的价值，用于AI，等于棋盘上所有白子的权值和

        public Board() {
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    Position p = new Position(i, j);
                    positions[i][j] = p;
                }
            }
        }

        // 浅拷贝positions数组
        public Board shallowCopy() {
            Board temp = new Board();
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    if (this.positions[i][j].piece instanceof K) {
                        temp.positions[i][j].piece = new K(i, j, this.positions[i][j].piece.side, temp);
                    }else if (this.positions[i][j].piece instanceof Q) {
                        temp.positions[i][j].piece = new Q(i, j, this.positions[i][j].piece.side, temp);
                    }else if (this.positions[i][j].piece instanceof R) {
                        temp.positions[i][j].piece = new R(i, j, this.positions[i][j].piece.side, temp);
                    }else if (this.positions[i][j].piece instanceof B) {
                        temp.positions[i][j].piece = new B(i, j, this.positions[i][j].piece.side, temp);
                    }else if (this.positions[i][j].piece instanceof N) {
                        temp.positions[i][j].piece = new N(i, j, this.positions[i][j].piece.side, temp);
                    }else if (this.positions[i][j].piece instanceof P) {
                        temp.positions[i][j].piece = new P(i, j, this.positions[i][j].piece.side, temp);
                    }
                }
            }
            return temp;
        }
    }

    class StoreBoard {

        ArrayList<String> stored = new ArrayList<>();
        public StoreBoard() {
        }
        public void addInBoard(Board board,int round,int currentPlayer){
            StringBuilder str = new StringBuilder();
                str.append(round +"\n");
                for(Position[] positions:board.positions){
                    for (Position position:positions){
                        if(position.piece==null){
                            str.append("_");
                        }else{
                            switch (position.piece.getName()){
                                case "P":
                                    if(position.piece.getSide()==0){str.append("P");}
                                    else{str.append("p");}
                                    break;
                                case "B":
                                    if(position.piece.getSide()==0){str.append("B");}
                                    else{str.append("b");}
                                    break;
                                case "N":
                                    if(position.piece.getSide()==0){str.append("N");}
                                    else{str.append("n");}
                                    break;
                                case "K":
                                    if(position.piece.getSide()==0){str.append("K");}
                                    else{str.append("k");}
                                    break;
                                case "Q":
                                    if(position.piece.getSide()==0){str.append("Q");}
                                    else{str.append("q");}
                                    break;
                                case "R":
                                    if(position.piece.getSide()==0){str.append("R");}
                                    else{str.append("r");}
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + position.piece);
                            }
                        }

                    }
                    str.append("\n");
                }

                str.append(currentPlayer);
                str.append("\n");
            System.out.println(str);
            stored.add(str.toString());
        }
    }

