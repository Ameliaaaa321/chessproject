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

    public  static AGame load(List<List<String>> s){
        Board currentBoard = new Board();
        ArrayList<Piece> currentPieces = new ArrayList<>();
        int currentPlayer =1;
        int round =0;
        StoreBoard storeBoard = new StoreBoard();

        for (int i=0;i<s.size();i++){
            currentPieces.removeAll(currentPieces);
            try {
                for (int j=0;j<10;j++){
                    if(j==0){
                        round =s.get(i).get(j).charAt(0);
                    }
                    if((j>0)&&(j<9)){
                        for (int k = 0; k < 8; k++) {
                            switch (s.get(i).get(j).charAt(k)) {
                                case 'P' & 'P':
                                    Piece p1 = new P(j, k, 0, currentBoard);
                                    currentBoard.positions[j][k].piece = p1;
                                    currentPieces.add(p1);
                                    break;
                                case 'R' & 'r':
                                    Piece r1 = new R(j, k, 0, currentBoard);
                                    currentBoard.positions[j][k].piece = r1;
                                    currentPieces.add(r1);
                                    break;
                                case 'B' & 'b':
                                    Piece b1 = new B(j, k, 0, currentBoard);
                                    currentBoard.positions[j][k].piece = b1;
                                    currentPieces.add(b1);
                                    break;
                                case 'N' & 'n':
                                    Piece n1 = new N(j, k, 0, currentBoard);
                                    currentBoard.positions[j][k].piece = n1;
                                    currentPieces.add(n1);
                                    break;
                                case 'K' & 'k':
                                    Piece k1 = new K(j, k, 0, currentBoard);
                                    currentBoard.positions[j][k].piece = k1;
                                    currentPieces.add(k1);
                                    break;
                                case 'Q' & 'q':
                                    Piece q1 = new Q(j, k, 0, currentBoard);
                                    currentBoard.positions[j][k].piece = q1;
                                    currentPieces.add(q1);
                                    break;
                                case '_':
                                    currentBoard.positions[j][k].piece=null;
                                    break;
                                default:
                                    throw new IllegalStateException("102: Unexpected value: " + s.get(i).get(j).charAt(k));
                            }
                        }
                    }
                    if (j==9){
                        currentPlayer = s.get(i).get(j).charAt(0);
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("下标异常");
            }catch (NullPointerException e){
                System.out.println("空指针");
            }
        }
        AGame LoadedGame =new AGame(storeBoard,currentBoard,currentPieces,round,currentPlayer);
        return LoadedGame;
    }

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
