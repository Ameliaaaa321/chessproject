package com.company;

import java.util.ArrayList;

public class AGame {
    StoreBoard storeBoard;
    Board board;
    ArrayList<Piece> pieces;



    public AGame(StoreBoard storeBoard,Board board,ArrayList<Piece> pieces){
        this.board=board;
        this.storeBoard = storeBoard;
        this.pieces = pieces;
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


}
