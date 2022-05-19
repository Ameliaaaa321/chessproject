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
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {



    public static final int CHESSBOARD_SIZE = 480;                        //棋盘图片大小
    public static final int BORDER_SIZE = 8;                            //棋盘横向和纵向能下子的个数
    public static final int CHESS_OFFSET = 60;          //棋子位置偏移量
    public static final int CHESSBOARD_LEFTSIDE = 400;
    public static final int CHESSBOARD_UPSIDE = 120;
    public static final String suffix = ".png";

    private AudioClip chess_chose;                //选择
    private AudioClip chess_place;              //放置
    private AudioClip chess_out;                //被吃


    private JLabel bg_image;

    private AGame currentGame = null;

    private ArrayList<Piece> pieces = new ArrayList<>();
    private Board board = new Board();
    private StoreBoard storeBoard = new StoreBoard(board);

    public Piece selectedPiece;
    public Position MouseAt;
    private int currentPlayer;                            //标记轮到谁下棋了（黑0	白1）
    private int round;
    private int GameOver = -1;
    private boolean lock = false;
    private int roundTime = 20;

    public boolean backGround =true;

    public String backString ;



    @Override
    public void paint(Graphics g){
        super.paint(g);//清除
//        System.out.println("paint");
        String bg1 = "pic"+File.separator+"gamePage2.png";
        Image bgimg1=Toolkit.getDefaultToolkit().getImage(bg1);

        String bg2 = "pic"+File.separator+"gamePage21.png";
        Image bgimg2=Toolkit.getDefaultToolkit().getImage(bg2);

        if(backGround){
            g.drawImage(bgimg1,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
        }else{
            g.drawImage(bgimg2,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
        }


        String BWin = "pic"+File.separator+"BWin.png";
        Image BWinImg = Toolkit.getDefaultToolkit().getImage(BWin);

        String WWin = "pic"+File.separator+"WWin.png";
        Image WWinImg = Toolkit.getDefaultToolkit().getImage(WWin);

        String Draw = "pic"+File.separator+"Draw.png";
        Image DrawImg = Toolkit.getDefaultToolkit().getImage(Draw);

        String sideBlack = "pic"+File.separator+ "sideBlack.png";
        Image sideBlackImg = Toolkit.getDefaultToolkit().getImage(sideBlack);

        String sideWhite = "pic" +File.separator+"sideWhite.png";
        Image sideWhiteImg = Toolkit.getDefaultToolkit().getImage(sideWhite);

        String islock = "pic"+File.separator+"lockboard.png";
        Image islockImg = Toolkit.getDefaultToolkit().getImage(islock);


        //行棋方显示
        if(currentPlayer==1){
            g.drawImage(sideWhiteImg,CHESSBOARD_LEFTSIDE+CHESSBOARD_SIZE+60,CHESSBOARD_UPSIDE+CHESSBOARD_SIZE-120,this);
        }else{
            g.drawImage(sideBlackImg,CHESSBOARD_LEFTSIDE-300,CHESSBOARD_UPSIDE,this);
        }
        //画棋子
        drawPieces(g);

        if (selectedPiece != null) {
            selectedPiece.drawPick(g ,this);
            selectedPiece.drawSteps(g,this,selectedPiece.findValidMovement());
//            System.out.println("paint successfully");
        }
        //鼠标所在位置
        if(MouseAt!=null){
            MouseAt.draw(g,this);
        }


        //输赢平
        switch (GameOver){
            case 1:
                g.drawImage(BWinImg,0,0,this);
                lock=true;
                break;
            case 0:
                g.drawImage(WWinImg,0,0,this);
                lock=true;
                break;
            case 2:
                g.drawImage(DrawImg,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
                lock=true;
                break;
            case -1:
                break;
        }

        if(lock){
            g.drawImage(islockImg,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
        }
    }

    private void drawPieces(Graphics g) {
        for (Piece item : pieces) {
            if(item != null) {
                item.draw(g,this);
            }
        }
    }


    public GamePanel() {
        backGroundPanel();
        loadChessboard();
//        roundTimer(this);

        if(!lock) {
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    //鼠标移动时触发
                    Point p = getPointFromXY(e.getX(), e.getY());
                    if (p != null) {
                        MouseAt = new Position(p.x, p.y);
//                    System.out.println(MouseAt.x+""+MouseAt.y);
                        repaint();
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("点击棋盘的坐标为：x=" + e.getX() + ",y=" + e.getY());
                    Point p = getPointFromXY(e.getX(), e.getY());
                    if (p != null) {
//                        Position p1 = new Position(p.x, p.y);
                        Position p1 = board.positions[p.x][p.y];    // 这一行我改了一下哦
                        System.out.println("点击棋盘的网格坐标对象为：p===" + p);
                        if (selectedPiece == null) {
                            selectedPiece = getChessByP(p);
                            System.out.println(selectedPiece.name);
                            if (selectedPiece != null && selectedPiece.getSide() != currentPlayer) {
                                selectedPiece = null;
                                System.out.println("wrong side!");
                            }
                        } else {
                            Piece c = getChessByP(p);
                            if (c != null) {
                                if (c.getSide() == selectedPiece.getSide()) {
                                    System.out.println("重新选择");
                                    selectedPiece = c;
                                } else {
                                    System.out.println("吃子");


                                    if (selectedPiece.findValidMovement().contains(p1)) {
                                        //记录行动
                                        System.out.println("成功吃子" + c.getName());
                                        pieces.remove(c);
                                        selectedPiece.setP(p);
                                        System.out.println(selectedPiece.getP());
//                                        GameOver = Play.movePiece(selectedPiece, selectedPiece.getPosition(), p1, board, storeBoard).isOver;
                                        GameOver = Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board, storeBoard).isOver;    // 之前出发和目的地好像反了
                                        Play.updatePositions(pieces, board);
                                        currentPlayer = currentPlayer !=1?1:0;
                                        selectedPiece = null;
                                    } else {
                                        System.out.println("不合法吃子");
                                    }

//                                    System.out.println("成功吃子" + c.getName());
//                                    pieces.remove(c);
//                                    selectedPiece.setP(p);
//                                    System.out.println(selectedPiece.getP());
//                                     currentPlayer = currentPlayer !=1?1:0;
//                                    selectedPiece = null;
////                                    GameOver = 2;


                                    //一些修改

                                }
                            } else {

                                System.out.println("移动");
                                if(selectedPiece.findValidMovement().contains(p1)) {

//                                    GameOver = Play.movePiece(selectedPiece, selectedPiece.getPosition(), p1, board, storeBoard).isOver;
                                    GameOver = Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board, storeBoard).isOver;    // 之前出发地和目的地好像反了
                                    Play.updatePositions(pieces, board);
                                    System.out.println("成功移动");
                                    //记录
                                    System.out.println(selectedPiece.getP());
                                    selectedPiece.setP(p);
                                    currentPlayer = currentPlayer !=1?1:0;
                                    selectedPiece=null;
                                }else{
                                    System.out.println("不合法移动");
                                }

//                                System.out.println("成功移动");
//                                //记录
//                                System.out.println(selectedPiece.getP());
//                                selectedPiece.setP(p);
//                                currentPlayer = currentPlayer !=1?1:0;
//                                selectedPiece = null;
////                                GameOver = 1;

                            }
                        }
                        repaint();
                        System.out.println(currentPlayer);
                    }
                }
            });



        }

    }

    //导入棋盘
    public void loadChessboard(){
        currentGame = Play.initializeGame(board);
        pieces=currentGame.pieces;
        board=currentGame.board;
        round=currentGame.round;
        currentPlayer= currentGame.currentPlayer;
        storeBoard=currentGame.storeBoard;
    }
    //重载导入
    public void loadChessboard(AGame aGame){
        currentGame = aGame;
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
        String back1 = "pic"+File.separator+"gamePage1.png";
        String back2 ="pic"+File.separator+"gamePage11.png";
        backString = back1;
        ImageIcon bggame = new ImageIcon(backString);
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
                buttonExit1.setIcon(buttonImages[0][0]);
                MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "主界面");
//                try {
//                    buttonExit1.setIcon(buttonImages[0][0]);
//
////				socket = new Socket("cn-zz-bgp-2.natfrp.cloud", 55129);
////                    OutputStream os = OnlinebattlePanel.socket.getOutputStream();
////                    os.write(ServerStart.TYPE_OFFLINE.getBytes());				// 发送下线消息
////                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
////                    objectOutputStream.writeObject(OnlinebattlePanel.admin);	// 发送离线用户
//
//
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }

            }

            @Override
            public void mouseClicked(MouseEvent e) {

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

        });

        JButton buttonSave = new MenuButton();          //保存游戏按键
        buttonSave.setBounds(0, 100, 100, 100);
        buttonSave.setIcon(buttonImages[0][0]);
        buttonSave.setVisible(true);
        bg_image.add(buttonSave);
        buttonSave.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                try {
                    buttonSave.setIcon(buttonImages[0][0]);

//				socket = new Socket("cn-zz-bgp-2.natfrp.cloud", 55129);
//                    OutputStream os = OnlinebattlePanel.socket.getOutputStream();
//                    os.write(ServerStart.TYPE_OFFLINE.getBytes());				// 发送下线消息
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
//                    objectOutputStream.writeObject(OnlinebattlePanel.admin);	// 发送离线用户

                    MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "读档界面");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonSave.setIcon(buttonImages[0][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonSave.setIcon(buttonImages[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonSave.setIcon(buttonImages[0][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        JButton buttonstart = new MenuButton();          //暂停游戏按键
        buttonstart.setBounds(0, 200, 100, 100);
        buttonstart.setIcon(buttonImages[0][0]);
        buttonstart.setVisible(true);
        bg_image.add(buttonstart);
        buttonstart.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonstart.setIcon(buttonImages[0][0]);
                lock=!lock;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonstart.setIcon(buttonImages[0][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonstart.setIcon(buttonImages[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonstart.setIcon(buttonImages[0][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });


        JButton buttonChangeBoard = new MenuButton();          //换棋盘
        buttonChangeBoard.setBounds(0, 300, 100, 100);
        buttonChangeBoard.setIcon(buttonImages[0][0]);
        buttonChangeBoard.setVisible(true);
        bg_image.add(buttonChangeBoard);
        buttonChangeBoard.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[0][0]);
                backGround=!backGround;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[0][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[0][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        JButton buttonChangeback = new MenuButton();          //换背景
        buttonChangeback.setBounds(0, 400, 100, 100);
        buttonChangeback.setIcon(buttonImages[0][0]);
        buttonChangeback.setVisible(true);
        bg_image.add(buttonChangeback);
        buttonChangeback.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[0][0]);
               if(backString==back1){
                   backString=back2;
                   ImageIcon bggame = new ImageIcon(backString);
                   bggame.setImage(bggame.getImage().getScaledInstance(MainFrame_LD.WIDTH,MainFrame_LD.HEIGHT,Image.SCALE_DEFAULT));
                   bg_image.setIcon(bggame);
               }else{
                   backString=back1;
                   ImageIcon bggame = new ImageIcon(backString);
                   bggame.setImage(bggame.getImage().getScaledInstance(MainFrame_LD.WIDTH,MainFrame_LD.HEIGHT,Image.SCALE_DEFAULT));
                   bg_image.setIcon(bggame);
               }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[0][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[0][1]);
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
        p.y = (y - CHESSBOARD_UPSIDE)/CHESS_OFFSET +1;
        if(p.x<=0||p.y<=0||p.x>=9||p.y>=9){
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

    public void setCurrentPlayer(){
        currentPlayer=currentPlayer!=1?1:0;
    }

    public void setRound(){
        round++;
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public void roundTimer(GamePanel gamePanel){

            Timer timer = new Timer();
            timer.schedule(new Task(gamePanel),10*1000,10*1000);
        }





}
