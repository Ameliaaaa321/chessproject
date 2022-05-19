package com.company;

import java.util.ArrayList;
import java.util.List;

public class AGame {
    StoreBoard storeBoard;
    Board board;
    ArrayList<Piece> pieces;
    int currentPlayer;
    int round;

    public AGame(StoreBoard storeBoard,Board board,ArrayList<Piece> pieces,int round,int currentPlayer){
        this.board=board;
        this.storeBoard = storeBoard;
        this.pieces = pieces;
        this.currentPlayer=currentPlayer;
        this.round= round;
    }

    public ArrayList<Piece> getPieces(){
        return pieces;
    }
    public StoreBoard getStoreBoard(){
        return storeBoard;
    }
    public Board getBoard(){
        return board;
    }

//    public AGame load(List<List<String>> s){
//        Board currentBoard = new Board();
//        ArrayList<Piece> currentPieces = new ArrayList<>();
//        int currentPlayer;
//        int round;
//
//        for (int i=0;i<s.size();i++){
//            for (int j=0;j<10;j++){
//                for (int k=0;k<8;k++){
//                    switch(s.get(i).get(j).charAt(k)){
//                        case 'P'&'P':
//                            Piece r1 = new R(j,k , 0, board);
//                            board.positions[j][k].piece = r1;
//                            currentPieces.add(r1);
//                            break;
//
//                    }
//                }
//
//
//
//
//            }
//
//        }
//
//
//
//
//
//
//
//    }

    public String save(){
        StringBuilder str = new StringBuilder();
        str.append(round+"\n");
        for(Board item: storeBoard.stored){
            for(Position[] positions:item.positions){
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
                str.append("\n");
                }
            }
            str.append(currentPlayer);
            str.append("\n");
        }
       return str.toString();
    }


}
