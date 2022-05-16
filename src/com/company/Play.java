package com.company;
import java.util.ArrayList;

// 还没写王车易位，和棋判定没写三次重复和50次规则

public class Play {

    public static AGame initializeGame(){
        ArrayList<Piece> pieces = new ArrayList<>();
        Board board = new Board();
        StoreBoard storeBoard = new StoreBoard(board);
        store(storeBoard, board);
        for (Piece item:initialize(board, 0, 1, 2)){
            pieces.add(item);
        }
        for(Piece item:initialize(board, 1, 8, 7)){
            pieces.add(item);
        }
        AGame game = new AGame(storeBoard,board,pieces,1,1);
        return game;
    }



    static void store(StoreBoard storeBoard, Board board) {
        storeBoard.z++;
        for (int i = 1; i <= 8; i ++) {
            for (int j = 1; j <= 8; j++) {
                int k;
                if (board.positions[i][j].piece instanceof K) {
                    if (board.positions[i][j].piece.side == 0) {
                        k = 0;
                    }else {
                        k = 6;
                    }
                }else if (board.positions[i][j].piece instanceof Q) {
                    if (board.positions[i][j].piece.side == 0) {
                        k = 1;
                    }else {
                        k = 7;
                    }
                }else if (board.positions[i][j].piece instanceof R) {
                    if (board.positions[i][j].piece.side == 0) {
                        k = 2;
                    }else {
                        k = 8;
                    }
                }else if (board.positions[i][j].piece instanceof B) {
                    if (board.positions[i][j].piece.side == 0) {
                        k = 3;
                    }else {
                        k = 9;
                    }
                }else if (board.positions[i][j].piece instanceof N) {
                    if (board.positions[i][j].piece.side == 0) {
                        k = 4;
                    }else {
                        k = 10;
                    }
                }else if (board.positions[i][j].piece instanceof P) {
                    if (board.positions[i][j].piece.side == 0) {
                        k = 5;
                    }else {
                        k = 11;
                    }
                }else {
                    k = -1;
                }
                storeBoard.stored[i][j][storeBoard.z] = k;
            }
        }
    }

    // Board就用main方法里创建的那个board就行，整局游戏一个Board
    // 初始时在棋盘的默认位置上创建棋子, row1是王所在行，row2是兵所在行
    // 整数side储存黑方or白方，0代表黑方，1代表白方
    // 返回长度为16的Piece数组，储存某一方的棋子；索引0~7对应从左到右的车马象后王象马车，索引8~15对应从左到右的八个兵
    static Piece[] initialize(Board board, int side, int row1, int row2) {
        Piece[] pieces = new Piece[16];

        Piece r1 = new R(1, row1, side, board);
        board.positions[1][row1].piece = r1;
        pieces[0] = r1;
        Piece n1 = new N(2, row1, side, board);
        board.positions[2][row1].piece = n1;
        pieces[1] = n1;
        Piece b1 = new B(3, row1, side, board);
        board.positions[3][row1].piece = b1;
        pieces[2] = b1;
        Piece q = new Q(4, row1, side, board);
        board.positions[4][row1].piece = q;
        pieces[3] = q;
        Piece k = new K(5, row1, side, board);
        board.positions[5][row1].piece = k;
        pieces[4] = k;
        Piece b2 = new B(6, row1, side, board);
        board.positions[6][row1].piece = b2;
        pieces[5] = b2;
        Piece n2 = new N(7, row1, side, board);
        board.positions[7][row1].piece = n2;
        pieces[6] = n2;
        Piece r2 = new R(8, row1, side, board);
        board.positions[8][row1].piece = r2;
        pieces[7] = r2;

        for (int x = 1; x <= 8; x++) {
            Piece p = new P(x, row2, side, board);
            board.positions[x][row2].piece = p;
            pieces[x+7] = p;
        }

        if (side == 0) {
            board.k0 = k;
        }else if (side == 1) {
            board.k1 = k;
        }

        return pieces;
    }

    // 若当前棋格中有子，返回该棋子可走的位置，否则返回null(这个没用上）
    static ArrayList findValidMovement(Position position,Board board) {
        if (position.piece == null) {
            return null;
        }else {
            return position.piece.findValidMovement();
        }
    }

    // 移动棋子，同时会自动调用各个函数，判断是否胜负已定，是否有子被吃，是否是和棋，是否必须进行兵的升变
    // 返回结果是对象MoveResult，包含以上各函数的结果
    static MoveResult movePiece(Piece piece, Position destination, Position startPlace, Board board, StoreBoard storeBoard) {
        boolean isDraw = isDraw(piece, board);
        Piece k = piece.side == 0 ? board.k1 : board.k0;
        int isOver = isOver(isDraw, board, k);
        Piece eaten = isEaten(piece, destination, startPlace, board);
        boolean isPromotion = isPromotion(piece, destination);

        destination.piece = piece;
        startPlace.piece = null;

        MoveResult result = new MoveResult(isOver, eaten, isDraw, isPromotion);

        // 这里标记一下这个兵它是否已经走过第一步了，是否是过路兵
        if (piece instanceof P) {
            ((P) piece).isFirstStep = false;
            ((P) piece).countSteps = Math.abs(startPlace.y - destination.y);
        }

        store(storeBoard, board);

        return result;
    }

    // 判断是否有棋子被吃，有的话返回值为被吃的棋子，没有的话返回null
    static Piece isEaten(Piece piece, Position destination, Position startPlace, Board board) {
        // 除了吃过路兵以外，吃子都吃的是落子处的
        if (destination.piece == null) {
            if (piece instanceof P && ((P) piece).isEatPasserby &&
                    Math.abs(destination.x - startPlace.x) == 1) {
                return board.positions[destination.x][startPlace.y].piece;
            }
            return null;
        }else {
            return destination.piece;
        }
    }

    // 判断是否胜负已定，-1代表棋局继续，0代表黑方胜，1代表白方胜, 2代表和棋
    // 包括：被将军且无法避免；和棋
    static int isOver(boolean isDraw, Board board, Piece k) {
        // 被将军且无法避免
        boolean canAvoid = false;
        if (isChecked(k, board)) {
            lable:
            for (int i = 1; i <= 8; i ++) {
                for (int j = 1; j <= 8; j++) {
                    if (board.positions[i][j].piece == null || board.positions[i][j].piece.side != k.side) {
                        continue;
                    }else {
                        ArrayList<Position> validPositions = board.positions[i][j].piece.findValidMovement();
                        for (int m = 0; m < validPositions.size(); m++) {
                            int x = validPositions.get(m).x;
                            int y = validPositions.get(m).y;
                            Piece temp0 = board.positions[i][j].piece;
                            Piece temp1 = board.positions[x][y].piece;
                            board.positions[x][y].piece = temp0;
                            board.positions[i][j].piece = null;
                            if (!isChecked(k, board)) {
                                canAvoid = true;
                                board.positions[x][y].piece = temp1;
                                board.positions[i][j].piece = temp0;
                                break lable;
                            }else {
                                board.positions[x][y].piece = temp1;
                                board.positions[i][j].piece = temp0;
                            }
                        }
                    }
                }
            }
            if (!canAvoid) {
                return k.side == 1 ? 0 : 1;
            }
        }

        // 和棋
        if (isDraw) {
            return 2;
        }

        return -1;
    }

    // 判断是否被将军，输入k为需要被判断的王
    static boolean isChecked(Piece k, Board board) {
        int side = k.side == 0 ? 1 : 0;
        for (int i = 1; i <= 8; i ++) {
            for (int j = 1; j <= 8; j++) {
                if (board.positions[i][j].piece == null || board.positions[i][j].piece.side != side) {
                    continue;
                }else {
                    ArrayList<Position> positions = board.positions[i][j].piece.findValidMovement();
                    for (int m = 0; m < positions.size(); m++) {
                        if (positions.get(m).x == k.x && positions.get(m).y == k.y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // 判断是否是和棋
    // 包括：某方无子可走，但又没有被将；三次重复（包括了长将？）；（连续50次没有吃子或兵的移动？）
    static boolean isDraw(Piece piece, Board board) {
        int side = piece.side == 0 ? 1 : 0;
        // 判断对方是否无子可走
        boolean cannotMove = true;
        lable:
        for (int i = 1; i <= 8; i ++) {
            for (int j = 1; j <= 8; j++) {
                if (board.positions[i][j].piece == null || board.positions[i][j].piece.side != side) {
                    continue;
                }else {
                    ArrayList<Position> positions = board.positions[i][j].piece.findValidMovement();
                    if (positions.size() > 0) {
                        cannotMove = false;
                        break lable;
                    }
                }
            }
        }
        if (cannotMove) {
            return true;
        }

        // 判断是否有三次重复棋局


        return false;
    }

    // 判断是否须进行兵的升变
    static boolean isPromotion(Piece piece, Position destination) {
        if (piece instanceof P) {
            if ((piece.side == 0 && destination.y == 1) || (piece.side == 1 && destination.y == 8)) {
                return true;
            }
        }
        return false;
    }

    // 进行兵的升变
    static void promotion(Piece p, Piece aim, StoreBoard storeBoard, Board board) {
        p = aim;
        store(storeBoard, board);
    }
}

class MoveResult {
    int isOver;
    Piece eaten;
    boolean isDraw;
    boolean isPromotion;

    public MoveResult(int isOver, Piece eaten, boolean isDraw, boolean isPromotion) {
        this.isOver = isOver;
        this.eaten = eaten;
        this.isDraw = isDraw;
        this.isPromotion = isPromotion;
    }
}