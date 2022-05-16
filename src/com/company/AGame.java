package com.company;

import java.util.ArrayList;

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
        this.round=round;
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

    public void load(String s){


    }

    public void save(){

    }


}
