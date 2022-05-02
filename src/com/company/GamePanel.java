package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private int p1_color;        //右/己方执棋颜色(1黑		-1白)
    private int p2_color;        //左/联机执棋颜色


    public static final int CHESSBOARD_SIZE = 480;                        //棋盘图片大小
    public static final int BORDER_SIZE = 8;                            //棋盘横向和纵向能下子的个数
    public static final int CHESS_OFFSET = 60;          //棋子位置偏移量
    public static final int CHESSBOARD_LEFTSIDE = 400;
    public static final int CHESSBOARD_UPSIDE = 120;
    public static final String suffix = ".png";

//    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
//    private final ClickController clickController = new ClickController(this);


    private BufferedImage chess_select;            //选择框图片


//    private int select_x = -1;                    //选择框横索引
//    private int select_y = -1;                    //选择框纵索引

    private AudioClip chess_chose;                //选择
    private AudioClip chess_place;              //放置
    private AudioClip chess_out;                //被吃

//    public boolean is_offline;						//标记是否为本地对战
//    public boolean online_state = false;           //是否开始网络对战
//    public int online_round = 3;                //标记网络对战的回合（1 房主	2玩家	3不能下棋）

    private JLabel bg_image;

    private ArrayList<Piece> pieces = new ArrayList<>();
    public Piece selectedPiece;
    private int round;                            //标记轮到谁下棋了（黑0	白1）


    private void drawPieces(Graphics g) {
        for (Piece item : pieces) {
            if(item != null) {
                item.draw(g,this);
            }
        }
    }



    @Override
    public void paint(Graphics g){
        String bg = "pic"+File.separator+"gamePage2.png";
        Image bgimg=Toolkit.getDefaultToolkit().getImage(bg);
        g.drawImage(bgimg,0,0,this);

       drawPieces(g);


    }

    public GamePanel() {
//        backGroundPanel();
        initiateEmptyChessboard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击棋盘的坐标为：x=" + e.getX() + ",y=" + e.getY());
                Point p = getPointFromXY(e.getX(), e.getY());
                Position p1 = new Position(p.x,p.y);
                System.out.println("点击棋盘的网格坐标对象为：p===" + p);
                if(selectedPiece == null){
                    selectedPiece = getChessByP(p);

                    if(selectedPiece != null&&selectedPiece.getSide()!=round) {
                        selectedPiece = null;
                        System.out.println("wrong side!");
                    }
                }else{
                    Piece c =getChessByP(p);
                    if(c != null){
                        if(c.getSide() == selectedPiece.getSide()){
                            System.out.println("重新选择");
                            selectedPiece = c;
                        }else{
                            System.out.println("吃子");
                            if(selectedPiece.findValidMovement().contains(p1)){
                                //记录行动
                                pieces.remove(c);
                                selectedPiece.setP(p);
                                round = round&1;
                            }
                        }
                    }else{
                        System.out.println("移动");
                        if(selectedPiece.findValidMovement().contains(p1)){
                            //记录

                            selectedPiece.setP(p);
                            round = round&1;
                        }
                    }
                }
                System.out.println("点击棋子为："+selectedPiece);
                repaint();
            }
        });
    }



//绘制初始棋盘
    public void initiateEmptyChessboard(){
        pieces = Play.initializeGame();

    }


    public void backGroundPanel() {

        //退出按钮和其他交互

        //图片传入
        ImageIcon buttonImages[][] = new ImageIcon[2][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                ImageIcon img = new ImageIcon("pic" + File.separator + "back1" + j + i + ".png");
                img.setImage(img.getImage().getScaledInstance(MainFrame_LD.WIDTH / 8, MainFrame_LD.HEIGHT * 10 / 72, Image.SCALE_DEFAULT));
                buttonImages[j][i] = img;
            }
        }
        //棋盘页面
        bg_image = new JLabel();
        bg_image.setSize(MainFrame_LD.WIDTH, MainFrame_LD.HEIGHT);
        //棋盘背景
        ImageIcon bggame = new ImageIcon("pic"+File.separator+"gamePage1.png");
        bggame.setImage(bggame.getImage().getScaledInstance(MainFrame_LD.WIDTH,MainFrame_LD.HEIGHT,Image.SCALE_DEFAULT));
        bg_image.setIcon(bggame);
        bg_image.setVisible(true);

        JButton buttonExit1 = new MenuButton();          //退出游戏按键
        buttonExit1.setBounds(0, 0, 100, 100);
        buttonExit1.setIcon(buttonImages[0][0]);
        buttonExit1.setVisible(true);
        bg_image.add(buttonExit1);
        buttonExit1.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                try {
                    buttonExit1.setIcon(buttonImages[0][0]);

//				socket = new Socket("cn-zz-bgp-2.natfrp.cloud", 55129);
//                    OutputStream os = OnlinebattlePanel.socket.getOutputStream();
//                    os.write(ServerStart.TYPE_OFFLINE.getBytes());				// 发送下线消息
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
//                    objectOutputStream.writeObject(OnlinebattlePanel.admin);	// 发送离线用户

                    MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "主界面");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit1.setIcon(buttonImages[0][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit1.setIcon(buttonImages[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit1.setIcon(buttonImages[0][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
        this.add(bg_image);// 将背景添加到容器中
    }

    public static Point getPointFromXY(int x,int y){
        Point p = new Point();
        p.x = (x - CHESSBOARD_LEFTSIDE )/CHESS_OFFSET + 1;
        p.y = (y - CHESSBOARD_LEFTSIDE)/CHESS_OFFSET +1;
        if(x<=0||y<=0||x>=9||y>=9){
            return  null;
        }
        return p;
    }

    public Piece getChessByP(Point p) {
        for (Piece item : pieces) {
            if (item != null && item.getP().equals(p)) {
                return item;
            }
        }

        return null;
    }



}
