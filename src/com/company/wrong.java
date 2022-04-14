//package com.company;
//
//public class wrong { setBounds(MainFrame_LD.WIDTH/2-CHESSBOARD_SIZE/2,MainFrame_LD.HEIGHT/2-CHESSBOARD_SIZE/2,CHESSBOARD_SIZE,CHESSBOARD_SIZE);
//
////        try {
////            chess_border = ImageIO.read(GamePanel.class.getResource("pic" + File.separator + "gamePage1.png"));
////            chess_black = ImageIO.read(GamePanel.class.getResource("pic" + File.separator + "buttonLocalGame1.png"));
////            chess_white = ImageIO.read(GamePanel.class.getResource("pic" + File.separator + "buttonLocalGame1.png"));
////            chess_select = ImageIO.read(GamePanel.class.getResource("pic" + File.separator + "buttonLocalGame1.png"));
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////
////        addMouseMotionListener(new MouseMotionAdapter() {
////            @Override
////            public void mouseMoved(MouseEvent e) {
////                //鼠标移动时触发
////                select_x = (e.getX() - CHESS_OFFSET) / CHESS_SIZE;
////                select_y = (e.getY() - CHESS_OFFSET) / CHESS_SIZE;
////                repaint();
////            }
////        });
////
////        addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseExited(MouseEvent e) {
////                //鼠标移出时触发
////                select_x = -1;
////                select_y = -1;
////                repaint();
////            }
////        });
////        GamePanel message = this;
////
////        is_offline = true;
//
////        addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseReleased(MouseEvent e) {
////                //点击后释放时触发
////                if(is_offline){
////                    if(round==1){
////
////
////                    }
////
////
////                } else {
////                    try {
////
////                        //网络对战时
////                        if(online_state)
////                        {
////                            if(online_round==1)
////                            {
////
////                                }
////                            }
////                            else if(online_round == 2)
////                            {
////
////
////
////                        }
////                        else
////                        {
////                            //进入房间但是还没开始
////                        }
////
////
////
////
////                    } catch (IOException e1) {
////                        e1.printStackTrace();
////                    }
////                }
////            }
////        });
////    }
//
////    public void reset(;){
////        //清空棋盘
////
////
////
////        //判断谁是先手
////        if(p1_color == 1){
////            round = 2;
////        }
////        else if(p2_color == -1){
////            round = 1;
////        }
////    }
//}
