package com.company;


import javax.swing.*;
import java.awt.*;
import java.io.File;

// 储存某个格子的坐标和棋子
public class Position {
    int x, y;
    Piece piece = null;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*-----------5月2日新增-------*/
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void draw(Graphics g , JPanel jPanel){
        String path = "pic" + File.separator + "落子位置框" + GamePanel.suffix;
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        g.drawImage(img ,GamePanel.CHESSBOARD_LEFTSIDE+GamePanel.CHESS_OFFSET*(x-1),
                GamePanel.CHESSBOARD_UPSIDE+GamePanel.CHESS_OFFSET*(y-1),
                GamePanel.CHESS_OFFSET, GamePanel.CHESS_OFFSET,jPanel);
    }
}