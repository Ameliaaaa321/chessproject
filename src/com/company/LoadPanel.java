package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class LoadPanel extends JPanel{
    JButton save =null;
    JButton load =null;
    public LoadPanel(){
        save =new JButton("存档");
        load=new JButton("读档");
        this.setLayout(new GridLayout(2,1));
        this.add(save);
        this.add(load);
        this.setBounds(400, 200, 100, 100);
        this.setVisible(true);

        save.addActionListener(new Saver());
        load.addActionListener(new Loader());

    }
    public class Saver implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc=new JFileChooser();
            jfc.showSaveDialog(new JLabel());
            File file=jfc.getSelectedFile();
            if(file.isFile()){ //是文件
                System.out.println("文件:"+file.getAbsolutePath());
                //TODO: do sth
            }
            System.out.println(jfc.getSelectedFile().getName());
        }
    }
    public class Loader implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc=new JFileChooser();
            jfc.showOpenDialog(new JLabel());
            File file=jfc.getSelectedFile();
            if(file.isFile()){ //是文件
                System.out.println("文件:"+file.getAbsolutePath());
                //TODO: do sth
            }
            System.out.println(jfc.getSelectedFile().getName());
        }
    }


//public void load(){
//
//    ImageIcon buttonImages[][] = new ImageIcon[2][4];
//    for (int i = 0; i < 3; i++) {
//        for (int j = 0; j < 2; j++) {
//            ImageIcon img = new ImageIcon("pic" + File.separator + "back1" + j + i + ".png");
//            img.setImage(img.getImage().getScaledInstance(MainFrame_LD.WIDTH / 8, MainFrame_LD.HEIGHT * 10 / 72, Image.SCALE_DEFAULT));
//            buttonImages[j][i] = img;
//        }
//    }
//
//
//    JPanel loadMenu = new JPanel();
//    loadMenu.setOpaque(false);         //透明度
//    loadMenu.setBounds((int)(WIDTH * 7/16), (int)(HEIGHT / 2), 160, 200);
//
//
//
//    JButton buttonLoad1 = new MenuButton();
//    buttonLoad1.setBounds(0, 0, 100, 100);
//    buttonLoad1.setIcon(buttonImages[0][0]);
//    buttonLoad1.setVisible(true);
//    buttonLoad1.addMouseListener(new MouseListener() {
//
//        @Override
//        public void mouseReleased(MouseEvent e) {           //松开
//            // TODO Auto-generated method stub
//            buttonLoad1.setIcon(buttonImages[0][1]);
//            MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "new game");
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {            //按下
//            // TODO Auto-generated method stub
//            buttonLoad1.setIcon(buttonImages[0][1]);
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {             //移开
//            // TODO Auto-generated method stub
//            buttonLoad1.setIcon(buttonImages[0][0]);
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {            //移入
//            // TODO Auto-generated method stub
//            buttonLoad1.setIcon(buttonImages[0][1]);
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//
//    JButton buttonLoad2 = new MenuButton();
//    buttonLoad2.setBounds(0, 0, 100, 100);
//    buttonLoad2.setIcon(buttonImages[0][0]);
//    buttonLoad2.setVisible(true);
//    buttonLoad2.addMouseListener(new MouseListener() {
//
//        @Override
//        public void mouseReleased(MouseEvent e) {           //松开
//            // TODO Auto-generated method stub
//            buttonLoad2.setIcon(buttonImages[0][1]);
//            MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "new game");
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {            //按下
//            // TODO Auto-generated method stub
//            buttonLoad2.setIcon(buttonImages[0][1]);
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {             //移开
//            // TODO Auto-generated method stub
//            buttonLoad2.setIcon(buttonImages[0][0]);
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {            //移入
//            // TODO Auto-generated method stub
//            buttonLoad2.setIcon(buttonImages[0][1]);
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//
//    JButton buttonLoad3 = new MenuButton();
//    buttonLoad3.setBounds(0, 0, 100, 100);
//    buttonLoad3.setIcon(buttonImages[0][0]);
//    buttonLoad3.setVisible(true);
//    buttonLoad3.addMouseListener(new MouseListener() {
//
//        @Override
//        public void mouseReleased(MouseEvent e) {           //松开
//            // TODO Auto-generated method stub
//            buttonLoad3.setIcon(buttonImages[0][1]);
//            MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "new game");
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {            //按下
//            // TODO Auto-generated method stub
//            buttonLoad3.setIcon(buttonImages[0][1]);
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {             //移开
//            // TODO Auto-generated method stub
//            buttonLoad3.setIcon(buttonImages[0][0]);
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {            //移入
//            // TODO Auto-generated method stub
//            buttonLoad3.setIcon(buttonImages[0][1]);
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            // TODO Auto-generated method stub
//
//        }
//    });
//
//    loadMenu.setVisible(true);
//    loadMenu.setLayout(new FlowLayout());
//    loadMenu.add(buttonLoad1);
//    loadMenu.add(buttonLoad2);
//    loadMenu.add(buttonLoad3);
//    this.add(loadMenu);

}

