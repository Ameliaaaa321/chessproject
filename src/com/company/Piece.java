package com.company;
import javafx.geometry.Pos;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

// 多态（继承、重写、向上转型）：Piece是父类，包括6个子类，即所有6种棋子
// 每个棋子对象里面，储存了坐标和黑白方，拥有可以判断下一步可以走哪的函数

public abstract class Piece {
    int x, y;    // 坐标1-8
    int side;    // 0代表黑方，1代表白方
    Board board;
    String name;


    /*一些修改
     * 加一个绘制自身的方法*/
    public void draw(Graphics g, JPanel panel){
        String path = "pic" + File.separator + getName() + side + GamePanel.suffix;
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        g.drawImage(img, GamePanel.CHESSBOARD_LEFTSIDE+GamePanel.CHESS_OFFSET*(x-1),
                GamePanel.CHESSBOARD_UPSIDE+GamePanel.CHESS_OFFSET*(y-1),
                GamePanel.CHESS_OFFSET ,GamePanel.CHESS_OFFSET, panel);
    }
    /*
     * 绘制被选中的黑边*/
    public void drawPick(Graphics g,JPanel panel) {
        String path = "pic" + File.separator + "选择框" + GamePanel.suffix;
        Image img = Toolkit.getDefaultToolkit().getImage(path);
//        System.out.println("paint heikuang");
        g.drawImage(img ,GamePanel.CHESSBOARD_LEFTSIDE+GamePanel.CHESS_OFFSET*(x-1),
                GamePanel.CHESSBOARD_UPSIDE+GamePanel.CHESS_OFFSET*(y-1),
                GamePanel.CHESS_OFFSET, GamePanel.CHESS_OFFSET,panel);
    }
    /* 绘制可移动格 */
    public void drawSteps(Graphics g,JPanel jPanel,ArrayList<Position> positions){
        for (int i=0;i< positions.size();i++) {
            String path = "pic" + File.separator + "合法落子框" + GamePanel.suffix;
            Image img = Toolkit.getDefaultToolkit().getImage(path);
            g.drawImage(img, GamePanel.CHESSBOARD_LEFTSIDE + GamePanel.CHESS_OFFSET * (positions.get(i).getX()-1),
                    GamePanel.CHESSBOARD_UPSIDE + GamePanel.CHESS_OFFSET * (positions.get(i).getY()- 1),
                    GamePanel.CHESS_OFFSET, GamePanel.CHESS_OFFSET, jPanel);
        }
        if(positions ==null){
            System.out.println("无法移动");
        }
    }
    /*写一个获取坐标*/
    public Point getP(){
        Point p = new Point(x,y);
        return p;
    }
    /*来个getside*/
    public int getSide(){
        return side;
    }
    public void setP(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    public String getName(){
        return name;
    }
    public Position getPosition(){
//        Position position = new Position(x,y);
//        return position;
        return board.positions[x][y];    // 原来是新建一个位置，我改成了返回board上的位置
    }
    /*----------以上5月2日修改---------*/

    public Piece(int x, int y, int side, Board board) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.board = board;
    }

    // 判断某位置是否在棋盘上
    boolean isOnBoard(Position p) {
        if (1 <= p.x && p.x <= 8 && 1 <= p.y && p.y <= 8) {
            return true;
        }else {
            return false;
        }
    }

    // 查询该棋子下一步可以走的位置
    abstract ArrayList<Position> findValidMovement();
}

// 王
class K extends Piece {
    public K(int x, int y, int side, Board board) {
        super(x, y, side, board);
        name = "K";
    }

    public ArrayList<Position> findValidMovement() {
        ArrayList<Position> validMovement = new ArrayList<>();

        int i = x-1, j = y-1;    // 横纵坐标的指针，可以向周围移动一格
        labelK0:
        while (i <= x+1) {
            labelK1:
            while (j <= y+1) {
                if (i == x && j == y) {
                    j++;
                    continue labelK1;
                }

//                System.out.println("temp: " + i + " " + j);

                Position temp = new Position(i, j);
                boolean b1 = isOnBoard(temp);    // 在棋盘上

                if (b1) {
//                    System.out.println("b1!");
                    Position p = board.positions[i][j];
                    if (p.piece != null) {
//                        System.out.println("piece here: " + p.x + " " + p.y + " " + p.piece.name + " " + p.piece.x + " " + p.piece.y);
                    }

                    boolean b2 = p.piece == null || p.piece.side != this.side;    // 没有己方棋子

                    if (b2) {
//                        System.out.println("b2!");
                        boolean b3 = true;    // 和对方的王保持一格以上的距离

                        // 判断b3的值，即查询某位置周围一圈有没有王
                        int m = i-1, n = j-1;
                        labelK2:
                        while (m <= i+1) {
                            labelK3:
                            while (n <= j+1) {
                                if (m == i && n == j) {
                                    n++;
                                    continue labelK3;
                                }
//                                System.out.println("around temp: " + m + " " + n);
                                temp = new Position(m, n);
                                if (isOnBoard(temp)) {
                                    if (board.positions[m][n].piece instanceof K && board.positions[m][n].piece.side != this.side) {
                                        b3 = false;
                                        break labelK2;
                                    }
                                }
                                n++;
                            }
                            n = j-1;
                            m++;
                        }

                        if (b3) {
//                            System.out.println("b3!");
                            validMovement.add(p);
                        }
                    }
                }
                j++;
            }
            j = y-1;
            i++;
//            System.out.println("i: " + i);
        }

//        System.out.println("valid size: " + validMovement.size());
//        for (int q = 0; q < validMovement.size(); q++) {
//            System.out.print(validMovement.get(q).x + " " + validMovement.get(q).y + "\t");
//        }
//        System.out.println();
        return validMovement;
    }

    public String getName(){
        return name;
    }
}

// 后
class Q extends Piece {
    public Q(int x, int y, int side, Board board) {
        super(x, y, side, board);
        name = "Q";
    }

    public ArrayList<Position> findValidMovement() {
        ArrayList<Position> validMovement = new ArrayList<>();

        int i;
        // 以左上角为坐标原点
        // 向右
        i = 1;
        while (x < 8) {
            if (isValid(x+i, y, side, validMovement)) {
                break;
            }
            i++;
        }
        // 向左
        i = 1;
        while (x > 1) {
            if (isValid(x-i, y, side, validMovement)) {
                break;
            }
            i++;
        }
        // 向上
        i = 1;
        while (y > 1) {
            if (isValid(x, y-i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 向下
        i = 1;
        while (y < 8) {
            if (isValid(x, y+i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 左上
        i = 1;
        while (x > 1 && y > 1) {
            if (isValid(x-i, y-i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 左下
        i = 1;
        while (x > 1 && y < 8) {
            if (isValid(x-i, y+i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 右上
        i = 1;
        while (x < 8 && y > 0) {
            if (isValid(x+i, y-i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 右下
        i = 1;
        while (x < 8 && y < 8) {
            if (isValid(x+i, y+i, side, validMovement)) {
                break;
            }
            i++;
        }

        return validMovement;
    }

    // 判断某位置是否能落子，返回该位置的情况，同时如果能落子的话顺便添加到动态数组中
    private boolean isValid(int x, int y, int side, ArrayList<Position> validMovement) {
        boolean isBreakUp = false;
        int result;// 0代表该位置为空，1代表有己方棋或者超出棋盘了，-1代表有对方棋

        Position p = new Position(x, y);
        if (isOnBoard(p)) {
            if (board.positions[x][y].piece == null) {
                result = 0;
            }else {
                if (board.positions[x][y].piece.side == side) {
                    result = 1;
                }else {
                    result = -1;
                }
            }
        }else {
            result = 1;
        }

        switch (result) {
            case -1:
                validMovement.add(board.positions[x][y]);
                isBreakUp = true;
                break;
            case 0:
                validMovement.add(board.positions[x][y]);
                break;
            case 1:
                isBreakUp = true;
                break;
        }

        return isBreakUp;
    }

    public String getName(){
        return name;
    }
}

// 车
class R extends Piece {
    public R(int x, int y, int side, Board board) {
        super(x, y, side, board);
        name = "R";
    }

    public ArrayList<Position> findValidMovement() {
        ArrayList<Position> validMovement = new ArrayList<>();

        int i;
        // 以左上角为坐标原点
        // 向右
        i = 1;
        while (x < 8) {
            if (isValid(x+i, y, side, validMovement)) {
                break;
            }
            i++;
        }
        // 向左
        i = 1;
        while (x > 1) {
            if (isValid(x-i, y, side, validMovement)) {
                break;
            }
            i++;
        }
        // 向上
        i = 1;
        while (y > 1) {
            if (isValid(x, y-i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 向下
        i = 1;
        while (y < 8) {
            if (isValid(x, y+i, side, validMovement)) {
                break;
            }
            i++;
        }

        System.out.println();

        return validMovement;
    }

    // 判断某位置是否能落子，返回该位置的情况，同时如果能落子的话顺便添加到动态数组中
    private boolean isValid(int x, int y, int side, ArrayList<Position> validMovement) {
        boolean isBreakUp = false;
        int result;// 0代表该位置为空，1代表有己方棋或者超出棋盘了，-1代表有对方棋

        Position p = new Position(x, y);
        if (isOnBoard(p)) {
            if (board.positions[x][y].piece == null) {
                result = 0;
            }else {
                if (board.positions[x][y].piece.side == side) {
                    result = 1;
                }else {
                    result = -1;
                }
            }
        }else {
            result = 1;
        }

        System.out.print("(" + x + ", " + y + ", " + result + ")\t");
        if (result == 1) {
            if (!isOnBoard(p)) {
                System.out.print("NotOnBoard");
            }else {
                System.out.println(board.positions[x][y].piece.name);
            }
        }

        switch (result) {
            case -1:
                validMovement.add(board.positions[x][y]);
                isBreakUp = true;
                break;
            case 0:
                validMovement.add(board.positions[x][y]);
                break;
            case 1:
                isBreakUp = true;
                break;
        }

        return isBreakUp;
    }

    public String getName(){
        return name;
    }
}

// 象
class B extends Piece {
    public B(int x, int y, int side, Board board) {
        super(x, y, side, board);
        name ="B";
    }

    public ArrayList<Position> findValidMovement() {
        ArrayList<Position> validMovement = new ArrayList<>();

        int i;
        // 以左上角为坐标原点
        // 左上
        i = 1;
        while (x > 1 && y > 1) {
            if (isValid(x-i, y-i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 左下
        i = 1;
        while (x > 1 && y < 8) {
            if (isValid(x-i, y+i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 右上
        i = 1;
        while (x < 8 && y > 0) {
            if (isValid(x+i, y-i, side, validMovement)) {
                break;
            }
            i++;
        }
        // 右下
        i = 1;
        while (x < 8 && y < 8) {
            if (isValid(x+i, y+i, side, validMovement)) {
                break;
            }
            i++;
        }

        return validMovement;
    }

    // 判断某位置是否能落子，返回该位置的情况，同时如果能落子的话顺便添加到动态数组中
    private boolean isValid(int x, int y, int side, ArrayList<Position> validMovement) {
        boolean isBreakUp = false;
        int result;// 0代表该位置为空，1代表有己方棋或者超出棋盘了，-1代表有对方棋

        Position p = new Position(x, y);
        if (isOnBoard(p)) {
            if (board.positions[x][y].piece == null) {
                result = 0;
            }else {
                if (board.positions[x][y].piece.side == side) {
                    result = 1;
                }else {
                    result = -1;
                }
            }
        }else {
            result = 1;
        }

        switch (result) {
            case -1:
                validMovement.add(board.positions[x][y]);
                isBreakUp = true;
                break;
            case 0:
                validMovement.add(board.positions[x][y]);
                break;
            case 1:
                isBreakUp = true;
                break;
        }

        return isBreakUp;
    }

    public String getName(){
        return name;
    }
}

// 马
class N extends Piece {
    public N(int x, int y, int side, Board board) {
        super(x, y, side, board);
        name = "N";
    }

    public ArrayList<Position> findValidMovement() {
        ArrayList<Position> validMovement = new ArrayList<>();

        Position p;
        p = new Position(x+1, y+2);
        if (isOnBoard(p) && (board.positions[x+1][y+2].piece == null || board.positions[x+1][y+2].piece.side != this.side)) {
            validMovement.add(board.positions[x+1][y+2]);
        }
        p = new Position(x+2, y+1);
        if (isOnBoard(p) && (board.positions[x+2][y+1].piece == null || board.positions[x+2][y+1].piece.side != this.side)) {
            validMovement.add(board.positions[x+2][y+1]);
        }
        p = new Position(x+2, y-1);
        if (isOnBoard(p) && (board.positions[x+2][y-1].piece == null || board.positions[x+2][y-1].piece.side != this.side)) {
            validMovement.add(board.positions[x+2][y-1]);
        }
        p = new Position(x+1, y-2);
        if (isOnBoard(p) && (board.positions[x+1][y-2].piece == null || board.positions[x+1][y-2].piece.side != this.side)) {
            validMovement.add(board.positions[x+1][y-2]);
        }
        p = new Position(x-1, y-2);
        if (isOnBoard(p) && (board.positions[x-1][y-2].piece == null || board.positions[x-1][y-2].piece.side != this.side)) {
            validMovement.add(board.positions[x-1][y-2]);
        }
        p = new Position(x-2, y-1);
        if (isOnBoard(p) && (board.positions[x-2][y-1].piece == null || board.positions[x-2][y-1].piece.side != this.side)) {
            validMovement.add(board.positions[x-2][y-1]);
        }
        p = new Position(x-1, y+2);
        if (isOnBoard(p) && (board.positions[x-1][y+2].piece == null || board.positions[x-1][y+2].piece.side != this.side)) {
            validMovement.add(board.positions[x-1][y+2]);
        }
        p = new Position(x-2, y+1);
        if (isOnBoard(p) && (board.positions[x-2][y+1].piece == null || board.positions[x-2][y+1].piece.side != this.side)) {
            validMovement.add(board.positions[x-2][y+1]);
        }

        return validMovement;
    }

    public String getName(){
        return name;
    }
}

// 兵（包含了常规走法和吃过路兵）
class P extends Piece {
    boolean isFirstStep = true;    // 会在对兵调用Play.movePiece时更改此参数，判断是否还没走第一步
    int countSteps = 0;    // 会在对兵调用Play.movePiece时更改此参数，记录上次纵向移动的距离
    ArrayList<P> passerbys = new ArrayList<>();    // 把过路兵装进去，只有当首次遇到这个过路兵时才可以吃它
    boolean isEatPasserby = false;    // 有没有过路兵可吃

    public P(int x, int y, int side, Board board) {
        super(x, y, side, board);
        name = "P";
    }

    public boolean getIsEatPasserby(){
        return isEatPasserby;
    }

    public ArrayList<Position> findValidMovement() {
        isEatPasserby = false;
        ArrayList<Position> validMovement = new ArrayList<>();

        if (side == 0) {
            // 前进一步
            Position p = new Position(x, y+1);
            if (isOnBoard(p) && board.positions[x][y+1].piece == null) {
                validMovement.add(board.positions[x][y+1]);
            }
            // 第一次走可以前进两步
            if (isFirstStep) {
                if (isOnBoard(p) && board.positions[x][y+2].piece == null) {
                    validMovement.add(board.positions[x][y+2]);
                }
            }
            // 斜向前吃子
            p = new Position(x+1,y+1);
            if (isOnBoard(p) && board.positions[x+1][y+1].piece != null && board.positions[x+1][y+1].piece.side == 1) {
                validMovement.add(board.positions[x+1][y+1]);
            }
            p = new Position(x-1, y+1);
            if (isOnBoard(p) && board.positions[x-1][y+1].piece != null && board.positions[x-1][y+1].piece.side == 1) {
                validMovement.add(board.positions[x-1][y+1]);
            }
        }else if (side == 1) {
            // 前进一步
            Position p = new Position(x, y-1);
            if (isOnBoard(p) && board.positions[x][y-1].piece == null) {
                validMovement.add(board.positions[x][y-1]);
            }
            // 第一次走可以前进两步
            if (isFirstStep) {
                if (isOnBoard(p) && board.positions[x][y-2].piece == null) {
                    validMovement.add(board.positions[x][y-2]);
                }
            }
            // 斜向前吃子
            p = new Position(x+1,y-1);
            if (isOnBoard(p) && board.positions[x+1][y-1].piece != null && board.positions[x+1][y-1].piece.side == 0) {
                validMovement.add(board.positions[x+1][y-1]);
            }
            p = new Position(x-1, y-1);
            if (isOnBoard(p) && board.positions[x-1][y-1].piece != null && board.positions[x-1][y-1].piece.side == 0) {
                validMovement.add(board.positions[x-1][y-1]);
            }
        }

        // 吃过路兵
//        boolean eatPasserby1 = eatPasserby(x+1, y, validMovement, passerbys);
//        boolean eatPasserby2 = eatPasserby(x-1, y, validMovement, passerbys);
//        if (eatPasserby1 || eatPasserby2) {
//            isEatPasserby = true;
//        }

        return validMovement;
    }

    private boolean eatPasserby(int x, int y, ArrayList<Position> validMovement, ArrayList<P> passerbys) {
        Position p = new Position(x, y);
        if (isOnBoard(p) && board.positions[x][y].piece != null && board.positions[x][y].piece instanceof P
                && board.positions[x][y].piece.side != this.side && ((P) board.positions[x][y].piece).countSteps == 2
                && !passerbys.contains((P) board.positions[x][y].piece)) {
            if (side == 0) {
                validMovement.add(board.positions[x][y+1]);
            }else if (side == 1) {
                validMovement.add(board.positions[x][y-1]);
            }
            passerbys.add((P)board.positions[x][y].piece);
            return true;
        }else {
            return false;
        }
    }

    public String getName(){
        return name;
    }
}

