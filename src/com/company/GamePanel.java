package com.company;

import javafx.geometry.Pos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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

    private AudioClip chess_noise;                //


    private JLabel bg_image;

    public AGame currentGame = null;

    private ArrayList<Piece> pieces = new ArrayList<>();
    private Board board = new Board();
    private StoreBoard storeBoard = new StoreBoard();

    public Piece selectedPiece;
    public Position MouseAt;
    private int currentPlayer;                            //标记轮到谁下棋了（黑0	白1）
    private int round;
    private int GameOver = -1;
    private boolean lock = false;
    private int roundTime = 20;

    public boolean backGround =true;

    public String backString ;

    public boolean isPvE;
    public int depth;

    public boolean inReview =false;



    @Override
    public void paint(Graphics g){
        super.paint(g);//清除
//        System.out.println("paint");
        String bg1 = "pic"+File.separator+"gamePage2.png";
        Image bgimg1=Toolkit.getDefaultToolkit().getImage(bg1);

        String bg2 = "pic"+File.separator+"gamePage22.png";
        Image bgimg2=Toolkit.getDefaultToolkit().getImage(bg2);

        if(backGround){
            g.drawImage(bgimg1,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
        }else{
            g.drawImage(bgimg2,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
        }


//        String BWin = "pic"+File.separator+"BWin.png";
//        Image BWinImg = Toolkit.getDefaultToolkit().getImage(BWin);
//
//        String WWin = "pic"+File.separator+"WWin.png";
//        Image WWinImg = Toolkit.getDefaultToolkit().getImage(WWin);

        String BWin = "pic"+File.separator+"0.png";
        Image BWinImg = Toolkit.getDefaultToolkit().getImage(BWin);

        String WWin = "pic"+File.separator+"1.png";
        Image WWinImg = Toolkit.getDefaultToolkit().getImage(WWin);


        String Draw = "pic"+File.separator+"Isdraw.png";
        Image DrawImg = Toolkit.getDefaultToolkit().getImage(Draw);

        String sideBlack = "pic"+File.separator+ "side0.png";
        Image sideBlackImg = Toolkit.getDefaultToolkit().getImage(sideBlack);

        String sideWhite = "pic" +File.separator+"side1.png";
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
            case 0:
                g.drawImage(BWinImg,0,0,this);
                lock=true;
                break;
            case 1:
                g.drawImage(WWinImg,0,0,this);
                lock=true;
                break;
            case 2:
                g.drawImage(DrawImg,0,0,this);
                lock=true;
                break;
            case -1:
                break;
        }

//        if(lock){
//            g.drawImage(islockImg,CHESSBOARD_LEFTSIDE,CHESSBOARD_UPSIDE,this);
//        }
    }

    private void drawPieces(Graphics g) {
        for (Piece item : pieces) {
            if(item != null) {
                item.draw(g,this);
            }
        }
    }


    public GamePanel(boolean isPvE) {

        URL url1 = GamePanel.class.getResource("/audios/Button23.wav");
        chess_noise =Applet.newAudioClip(url1);
        backGroundPanel();
        loadChessboard();
        this.isPvE = isPvE;

//        roundTimer(this);

        if (!isPvE) {
            if (!lock) {
                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        //鼠标移动时触发
                        Point p = getPointFromXY(e.getX(), e.getY());
                        if (p != null) {
                            MouseAt = new Position(p.x, p.y);
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
                                chess_noise.play();
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
                                        chess_noise.play();
                                    } else {
                                        System.out.println("吃子");


                                        if (selectedPiece.findValidMovement().contains(p1)) {
                                            //记录行动
                                            System.out.println("成功吃子" + c.getName());
                                            pieces.remove(c);
                                            System.out.println(selectedPiece.getP());
                                            GameOver = Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board).isOver;    // 之前出发和目的地好像反了
//
//                                            board.positions[selectedPiece.x][selectedPiece.y].piece = null;
//                                            board.positions[p1.x][p1.y].piece = selectedPiece;
                                            currentPlayer = currentPlayer != 1 ? 1 : 0;
                                            selectedPiece = null;
                                            chess_noise.play();
                                            round++;
                                            System.out.println(round);
                                            storeBoard.addInBoard(board, round, currentPlayer);
                                        } else {
                                            System.out.println("不合法吃子");
                                        }
                                    }
                                } else {

                                    System.out.println("移动");
                                    if (selectedPiece.findValidMovement().contains(p1)) {
                                        GameOver = Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board).isOver;    // 之前出发地和目的地好像反了

                                        System.out.println("成功移动");
                                        //记录
                                        System.out.println(selectedPiece.getP());

                                        currentPlayer = currentPlayer != 1 ? 1 : 0;
                                        selectedPiece = null;
                                        chess_noise.play();
                                        round++;
                                        System.out.println(round);
                                        storeBoard.addInBoard(board, round, currentPlayer);
                                    } else {
                                        System.out.println("不合法移动");
                                    }
                                }
                            }
                            repaint();
                            System.out.println(currentPlayer);
                        }
                    }
                });
            }
        }else{
            if (!lock) {
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
                    System.out.println("is");
                    System.out.println("点击棋盘的坐标为：x=" + e.getX() + ",y=" + e.getY());
                    Point p = getPointFromXY(e.getX(), e.getY());
                    if (p != null) {
//                        Position p1 = new Position(p.x, p.y);
                        Position p1 = board.positions[p.x][p.y];    // 这一行我改了一下哦
                        System.out.println("点击棋盘的网格坐标对象为：p===" + p);
                        if (selectedPiece == null) {
                            selectedPiece = getChessByP(p);
                            chess_noise.play();
                            if (selectedPiece != null && selectedPiece.getSide() != 1) {
                                selectedPiece = null;
                                System.out.println("wrong side!");
                            }
                        } else {
                            Piece c = getChessByP(p);
                            if (c != null) {
                                if (c.getSide() == selectedPiece.getSide()) {
                                    System.out.println("重新选择");
                                    selectedPiece = c;
                                    chess_noise.play();
                                } else {
                                    System.out.println("吃子");
                                    if (selectedPiece.findValidMovement().contains(p1)) {
                                        //记录行动
                                        System.out.println("成功吃子" + c.getName());
                                        pieces.remove(c);
//                                        selectedPiece.setP(p);
                                        System.out.println(selectedPiece.getP());
//                                        GameOver = Play.movePiece(selectedPiece, selectedPiece.getPosition(), p1, board, storeBoard).isOver;
                                        GameOver = Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board).isOver;    // 之前出发和目的地好像反了
//                                        Play.updatePositions(pieces, board);
//                                        board.positions[selectedPiece.x][selectedPiece.y].piece = null;
//                                        board.positions[p1.x][p1.y].piece = selectedPiece;
//                                        currentPlayer = currentPlayer != 1 ? 1 : 0;
                                        selectedPiece = null;
                                        chess_noise.play();
//                                        board=Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board, storeBoard).board;
//                                        System.out.println(board);
                                        round++;
                                        System.out.println(round);
                                        storeBoard.addInBoard(board, round,1);
                                        repaint();



                                        System.out.println("计算机行棋");
                                        AIMovement aiMovement = Play.maxMin(depth,0,board,Integer.MAX_VALUE,Integer.MIN_VALUE,null,null);
                                        if(aiMovement.destination.piece != null){
                                            Point point = getPointFromPosition(aiMovement.destination);
                                            Piece c1 = getChessByP(point);
                                            pieces.remove(c1);
                                        }
//                                        GameOver = Play.movePiece(aiMovement.piece, aiMovement.destination, aiMovement.startPlace, board).isOver;
                                        System.out.println("AI最终的返回值：" + aiMovement.piece.name + " " + aiMovement.piece.x + " " + aiMovement.piece.y);
                                        GameOver = Play.movePiece(board.positions[aiMovement.piece.x][aiMovement.piece.y].piece,
                                                board.positions[aiMovement.destination.x][aiMovement.destination.y],
                                                board.positions[aiMovement.startPlace.x][aiMovement.startPlace.y],
                                                board).isOver;
//                                        board.positions[aiMovement.startPlace.x][aiMovement.startPlace.y].piece = null;
//                                        board.positions[aiMovement.destination.x][aiMovement.destination.y].piece = aiMovement.piece;
//                                        currentPlayer = currentPlayer != 1 ? 1 : 0;
                                        round++;

                                        chess_noise.play();
                                        storeBoard.addInBoard(board,round,0);
                                        repaint();
                                    } else {
                                        System.out.println("不合法吃子");
                                    }
                                }
                            }else {

                                System.out.println("移动");
                                if (selectedPiece.findValidMovement().contains(p1)) {

//                                    GameOver = Play.movePiece(selectedPiece, selectedPiece.getPosition(), p1, board, storeBoard).isOver;
                                    GameOver = Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board).isOver;    // 之前出发地和目的地好像反了
//                                    Play.updatePositions(pieces, board);
                                    System.out.println("成功移动");
                                    //记录
                                    System.out.println(selectedPiece.getP());
//                                    selectedPiece.setP(p);

//                                    currentPlayer = currentPlayer != 1 ? 1 : 0;
                                    selectedPiece = null;
                                    chess_noise.play();
//                                    board=Play.movePiece(selectedPiece, p1, selectedPiece.getPosition(), board, storeBoard).board;
                                    round++;
                                    System.out.println(round);
                                    storeBoard.addInBoard(board, round, 1);
                                    repaint();

                                    System.out.println("计算机行棋");

                                    AIMovement aiMovement = Play.maxMin(depth,0,board,Integer.MAX_VALUE,Integer.MIN_VALUE,null,null);
                                    if(aiMovement.destination.piece != null){
                                        Point point = getPointFromPosition(aiMovement.destination);
                                        Piece c1 = getChessByP(point);
                                        pieces.remove(c1);
                                    }
//                                    GameOver = Play.movePiece(aiMovement.piece, aiMovement.destination, aiMovement.startPlace, board).isOver;
                                    System.out.println("返回值：" + aiMovement.piece.x + " " + aiMovement.piece.y);
                                    GameOver = Play.movePiece(board.positions[aiMovement.piece.x][aiMovement.piece.y].piece,
                                            board.positions[aiMovement.destination.x][aiMovement.destination.y],
                                            board.positions[aiMovement.startPlace.x][aiMovement.startPlace.y],
                                            board).isOver;
//                                    board.positions[aiMovement.startPlace.x][aiMovement.startPlace.y].piece = null;
//                                    board.positions[aiMovement.destination.x][aiMovement.destination.y].piece = aiMovement.piece;
//                                    currentPlayer = currentPlayer != 1 ? 1 : 0;
                                    round++;

//
                                    chess_noise.play();
                                    storeBoard.addInBoard(board,round,0);
                                    repaint();

                                } else {
                                    System.out.println("不合法移动");
                                }
                            }
                        }
                        repaint();
                        System.out.println(currentPlayer);
                    }
                }
            });
        }

        }

//
//        while(inReview){
//            System.out.println("kaishi");
//            flashTimer(this);
//
//        }

    }


    //导入棋盘
    public void loadChessboard(){
            board = new Board();
            currentGame = Play.initializeGame(board);

            pieces=currentGame.pieces;
            board=currentGame.board;

            round=currentGame.round;
            System.out.println(round);
            currentPlayer= currentGame.currentPlayer;
            storeBoard=currentGame.storeBoard;
            lock=false;
            GameOver =-1;


        storeBoard.addInBoard(board,round,currentPlayer);

    }

    public void loadChessboard(AGame currentGame){
        pieces=currentGame.pieces;
        board=currentGame.board;
        round=currentGame.round;
        currentPlayer= currentGame.currentPlayer;
        storeBoard=currentGame.storeBoard;
//        System.out.println(board.positions[0][0]);
        storeBoard.addInBoard(board,round,currentPlayer);
    }

    public void backGroundPanel() {

        //退出按钮和其他交互

        //图片传入
        ImageIcon buttonImages[][] = new ImageIcon[6][2];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                ImageIcon img = new ImageIcon("pic" + File.separator + "gp" + i + j + ".png");
                img.setImage(img.getImage().getScaledInstance(160, 80, Image.SCALE_DEFAULT));
                buttonImages[i][j] = img;
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
        buttonExit1.setBounds(0, 0, 160, 80);
        buttonExit1.setIcon(buttonImages[0][0]);
        buttonExit1.setVisible(true);
        bg_image.add(buttonExit1);
        buttonExit1.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit1.setIcon(buttonImages[0][0]);
                loadChessboard();
                MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "主界面");
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
        JButton buttonreset = new MenuButton();          //冲开游戏按键
        buttonreset.setBounds(1280-160, 160, 160, 80);
        buttonreset.setIcon(buttonImages[5][0]);
        buttonreset.setVisible(true);
        bg_image.add(buttonreset);
        buttonreset.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreset.setIcon(buttonImages[5][0]);
                selectedPiece=null;
                loadChessboard();
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreset.setIcon(buttonImages[5][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreset.setIcon(buttonImages[5][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreset.setIcon(buttonImages[5][1]);
            }

        });

        JButton buttonSave = new MenuButton();          //保存游戏按键
        buttonSave.setBounds(0, 80, 160, 80);
        buttonSave.setIcon(buttonImages[1][0]);
        buttonSave.setVisible(true);
        bg_image.add(buttonSave);
        buttonSave.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                try {
                    buttonSave.setIcon(buttonImages[1][0]);
                    currentGame.board=board;
                    currentGame.pieces=pieces;
                    currentGame.storeBoard=storeBoard;
                    currentGame.currentPlayer=currentPlayer;
                    currentGame.round=round;
                    FileLoad fileLoad =new FileLoad();
                    fileLoad.gameSave(currentGame.save());
                    MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "主界面");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonSave.setIcon(buttonImages[1][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonSave.setIcon(buttonImages[1][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonSave.setIcon(buttonImages[1][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        JButton buttonreturn = new MenuButton();          //悔棋
        buttonreturn.setBounds(0, 160, 160, 80);
        buttonreturn.setIcon(buttonImages[4][0]);
        buttonreturn.setVisible(true);
        bg_image.add(buttonreturn);
        buttonreturn.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreturn.setIcon(buttonImages[4][0]);
                selectedPiece = null;
                if(storeBoard.stored.size()!=1){
                    storeBoard.stored.remove(storeBoard.stored.size()-1);
                }
                currentGame = AGame.huiqi(storeBoard.getstring());
                board = currentGame.board;
                pieces=currentGame.pieces;
                round = currentGame.round;
                currentPlayer = currentGame.currentPlayer;
                repaint();
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreturn.setIcon(buttonImages[4][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreturn.setIcon(buttonImages[4][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonreturn.setIcon(buttonImages[4][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });


        JButton buttonChangeBoard = new MenuButton();          //换棋盘
        buttonChangeBoard.setBounds(1280-160, 0, 160, 80);
        buttonChangeBoard.setIcon(buttonImages[2][0]);
        buttonChangeBoard.setVisible(true);
        bg_image.add(buttonChangeBoard);
        buttonChangeBoard.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[2][0]);
                backGround=!backGround;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[2][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[2][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeBoard.setIcon(buttonImages[2][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        JButton buttonChangeback = new MenuButton();          //换背景
        buttonChangeback.setBounds(1280-160, 80, 160, 80);
        buttonChangeback.setIcon(buttonImages[3][0]);
        buttonChangeback.setVisible(true);
        bg_image.add(buttonChangeback);
        buttonChangeback.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[3][0]);
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
                buttonChangeback.setIcon(buttonImages[3][1]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[3][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonChangeback.setIcon(buttonImages[3][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

//        JButton buttonView = new MenuButton();          //回放步骤
//        buttonView.setBounds(1280-160, 320, 160, 80);
//        buttonView.setIcon(buttonImages[3][0]);
//        buttonView.setVisible(true);
//        bg_image.add(buttonView);
//        buttonView.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                // TODO Auto-generated method stub
//                buttonView.setIcon(buttonImages[3][0]);
//               inReview=true;
//               System.out.println("keyi1");
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                // TODO Auto-generated method stub
//                buttonView.setIcon(buttonImages[3][1]);
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                // TODO Auto-generated method stub
//                buttonView.setIcon(buttonImages[3][0]);
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                // TODO Auto-generated method stub
//                buttonView.setIcon(buttonImages[3][1]);
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // TODO Auto-generated method stub
//
//            }
//        });



        this.add(bg_image);// 将背景添加到容器中
    }

    public static Point getPointFromXY(int x,int y){
        Point p = new Point();
        p.x = (x - CHESSBOARD_LEFTSIDE )/CHESS_OFFSET ;
        p.y = (y - CHESSBOARD_UPSIDE)/CHESS_OFFSET ;
        if(p.x<0||p.y<0||p.x>7||p.y>7){
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

    public void setCurrentPlayer(int i){

        this.currentPlayer = i;
    }

    public static Point getPointFromPosition(Position position){
        Point point = new Point();
        point.x = position.x;
        point.y = position.y;
    return point;
    }

    public int getRound(){
        return round;
    }
    public void setPieces(ArrayList<Piece> pieces){
        this.pieces=pieces;
    }
    public void setBoard(Board  board){
        this.board=board;
    }

    public void setRound(int i){

        this.round =i;
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

//    public void roundTimer(GamePanel gamePanel){
//
//            Timer timer = new Timer();
//            timer.schedule(new Task(gamePanel),10*1000,10*1000);
//        }

    public void flashTimer(GamePanel gamePanel){
        System.out.println("ok");
        Timer timer = new Timer();
        timer.schedule(new FlashTask(gamePanel),1*1000,1*1000);

    }





}
