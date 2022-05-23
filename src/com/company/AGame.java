package com.company;

import java.awt.*;
import java.io.File;
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


    public static ArrayList<ArrayList<String>> splitString(String s){
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        String []  strings = s.split("\n");
        for (String St : strings){
            St.replace("\n","");
        }
        try {
            for (int i = 0; 10 * i < strings.length; i++) {
                ArrayList<String> aGameList = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    aGameList.add(strings[10 * i + j]);
                }
                lists.add(aGameList);
            }
        }catch (ArrayIndexOutOfBoundsException e){

            WrongDialog wrongDialog=new WrongDialog();
            TextField textField = new TextField();
            textField.setText("错误代码：103");
            wrongDialog.add(textField);
            wrongDialog.setVisible(true);
            System.out.println("下标异常");
            System.out.println("103");
        }catch (NullPointerException e){

            WrongDialog wrongDialog=new WrongDialog();
            TextField textField = new TextField();
            textField.setText("错误代码：103");
            wrongDialog.add(textField);
            wrongDialog.setVisible(true);
            System.out.println("空指针");
            System.out.println("103");
        }
        return lists;
    }

    public static AGame load(ArrayList<ArrayList<String>> s){
        Board currentBoard = new Board();
        ArrayList<Piece> currentPieces = new ArrayList<>();
        int currentPlayer =1;
        int round =0;
        StoreBoard storeBoard = new StoreBoard();
//        try {

            for (int i=0;i<s.size();i++){
                currentPieces.removeAll(currentPieces);
                try {
                    for (int j=0;j<10;j++){
                        if(j==0){
                            round =Integer.parseInt(String.valueOf(s.get(i).get(j).charAt(0)));
                            System.out.println(round);
                        }
                        if((j>0)&&(j<9)){
                            for (int k = 0; k < 8; k++) {
                                switch (s.get(i).get(j).charAt(k)) {
                                    case 'P':
                                        Piece P1;
                                        P1 = new P(j-1, k, 0, currentBoard);
                                        currentBoard.positions[j-1][k].piece = P1;
                                        currentPieces.add(P1);
                                        System.out.print("P");
                                        break;
                                    case 'p':
                                        Piece p1 = new P(j-1, k, 1, currentBoard);
                                        currentBoard.positions[j-1][k].piece = p1;
                                        currentPieces.add(p1);
                                        System.out.print("p");
                                        break;
                                    case 'R':
                                        Piece R1;
                                        R1 = new R(j-1, k, 0, currentBoard);
                                        currentBoard.positions[j-1][k].piece = R1;
                                        currentPieces.add(R1);
                                        System.out.print("R");
                                        break;
                                    case 'r':
                                        Piece r1 = new R(j-1, k, 1, currentBoard);
                                        currentBoard.positions[j-1][k].piece = r1;
                                        currentPieces.add(r1);
                                        System.out.print("r");
                                        break;
                                    case 'B':
                                        Piece B1 = new B(j-1, k, 0, currentBoard);
                                        currentBoard.positions[j-1][k].piece = B1;
                                        currentPieces.add(B1);
                                        System.out.print("B");
                                        break;
                                    case 'b':
                                        Piece b1 = new B(j-1, k, 1, currentBoard);
                                        currentBoard.positions[j-1][k].piece = b1;
                                        currentPieces.add(b1);
                                        System.out.print("b");
                                        break;
                                    case 'N':
                                        Piece N1 = new N(j-1, k, 0, currentBoard);
                                        currentBoard.positions[j-1][k].piece = N1;
                                        currentPieces.add(N1);
                                        System.out.print("N");
                                        break;
                                    case 'n':
                                        Piece n1 = new N(j-1, k, 1, currentBoard);
                                        currentBoard.positions[j-1][k].piece = n1;
                                        currentPieces.add(n1);
                                        System.out.print("n");
                                        break;
                                    case 'K':
                                        Piece K1 = new K(j-1, k, 0, currentBoard);
                                        currentBoard.positions[j-1][k].piece = K1;
                                        currentPieces.add(K1);
                                        System.out.print("K");
                                        break;
                                    case 'k':
                                        Piece k1 = new K(j-1, k, 1, currentBoard);
                                        currentBoard.positions[j-1][k].piece = k1;
                                        currentPieces.add(k1);
                                        System.out.print("k");
                                        break;
                                    case 'Q':
                                        Piece Q1 = new Q(j-1, k, 0, currentBoard);
                                        currentBoard.positions[j-1][k].piece = Q1;
                                        currentPieces.add(Q1);
                                        System.out.print("Q");
                                        break;
                                    case 'q':
                                        Piece q1 = new Q(j-1, k, 1, currentBoard);
                                        currentBoard.positions[j-1][k].piece = q1;
                                        currentPieces.add(q1);
                                        System.out.print("q");
                                        break;
                                    case '_':
                                        currentBoard.positions[j-1][k].piece=null;
                                        System.out.print("_");
                                        break;
                                    default:
                                        System.out.println("102");
                                        WrongDialog wrongDialog=new WrongDialog();
                                        Label label = new Label();
                                        label.setText("错误代码：102");
                                        wrongDialog.add(label);
                                        wrongDialog.setVisible(true);

//                                    throw new IllegalStateException("102: Unexpected value: " + s.get(i).get(j).charAt(k));
                                }
                            }
                        }
                        if (j==9){
                            currentPlayer = Integer.parseInt(String.valueOf(s.get(i).get(j).charAt(0)));
                            System.out.println(currentPlayer);
                        }
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("下标异常");
                    System.out.println("101");
                    WrongDialog wrongDialog=new WrongDialog();
                    Label label = new Label();
                    label.setText("错误代码：101");
                    wrongDialog.add(label);
                    wrongDialog.setVisible(true);
                }catch (NullPointerException e){
                    System.out.println("空指针");
                    System.out.println("101");
                    WrongDialog wrongDialog=new WrongDialog();
                    Label label = new Label();
                    label.setText("错误代码：101");
                    wrongDialog.add(label);
                    wrongDialog.setVisible(true);
                }
            }

        for (Piece piece :currentPieces){
            if (piece instanceof K){
                if(piece.getSide()==0){
                    currentBoard.k0 = piece;
                }else{
                    currentBoard.k1 = piece;
                }

            }
        }


        AGame LoadedGame =new AGame(storeBoard,currentBoard,currentPieces,round,currentPlayer);
        return LoadedGame;
    }

    public String save(){

        StringBuilder str = new StringBuilder();
        for (String string : storeBoard.stored){
            str.append(string);
        }
       return str.toString();
    }


    public static AGame huiqi(String s){
        AGame currentGame = load(splitString(s));

    return currentGame;
    }


}
