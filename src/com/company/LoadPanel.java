package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class LoadPanel extends JPanel{
//    JButton save =null;

    JButton buttonLoad = null;


    public LoadPanel(){

        ImageIcon images ;
        ImageIcon img = new ImageIcon("pic"+File.separator+"load"+".png");
        img.setImage(img.getImage());
        images = img;
        buttonLoad = new MenuButton();//读档游戏按键
        buttonLoad.setBounds(0, 0, 160, 80);
        buttonLoad.setIcon(images);
        buttonLoad.setVisible(true);
        this.add(buttonLoad);
        this.setLayout(new GridLayout(2,1));

//        save =new JButton("存档");
//        load=new JButton("读档");
//        this.setLayout(new GridLayout(2,1));
//        this.add(save);
//        this.add(load);
//        this.setBounds(220, 220, 200, 100);
//        this.setVisible(true);

//        save.addActionListener(new Saver());
        buttonLoad.addActionListener(new Loader());

    }
//    public class Saver implements ActionListener
//    {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFileChooser jfc=new JFileChooser();
//            jfc.showSaveDialog(new JLabel());
//            File file=jfc.getSelectedFile();
//            if(file.isFile()){ //是文件
//                System.out.println("文件:"+file.getAbsolutePath());
//                //TODO: do sth
//            }
//            System.out.println(jfc.getSelectedFile().getName());
//        }
//    }
    public class Loader implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc=new JFileChooser();
            jfc.showOpenDialog(new JLabel());
            File file=jfc.getSelectedFile();
            if(file.isFile()){ //是文件
                System.out.println("文件:"+file.getAbsolutePath());
                String extension = "";
                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = file.getName().substring(i+1);
                }
//...
                if("txt".equals(extension)){
                    try {
                        StringBuffer buffer = new StringBuffer();
                        BufferedReader bf= new BufferedReader(new FileReader(file));
                        String s = null;
                        while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
                            buffer.append(s.trim());
                            buffer.append("\n");
                        }
                        String string = buffer.toString();

                        GamePanel gamePanel = new GamePanel();
                        gamePanel.currentGame = AGame.load(AGame.splitString(string));
                        gamePanel.loadChessboard(gamePanel.currentGame);
                        MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "new game" );

//                    gamePanel.currentGame=AGame.load(AGame.splitString(string));
                    } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
                        WrongDialog wrongDialog=new WrongDialog();
                        TextField textField = new TextField();
                        textField.setText("错误代码：104");
                        wrongDialog.add(textField);
                        wrongDialog.setVisible(true);
                        System.out.println("104");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("104.");
                        WrongDialog wrongDialog=new WrongDialog();
                        TextField textField = new TextField();
                        textField.setText("错误代码：104");
                        wrongDialog.add(textField);
                        wrongDialog.setVisible(true);
                        System.out.println("104");
                    }
                }else{
                    WrongDialog wrongDialog=new WrongDialog();
                    TextField textField = new TextField();
                    textField.setText("错误代码：104");
                    wrongDialog.add(textField);
                    wrongDialog.setVisible(true);
                    System.out.println("104");
                }


                //TODO: do sth
            }
            System.out.println(jfc.getSelectedFile().getName());
        }
    }


}

