package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class MainFrame_LD extends JFrame{

    public static final int WIDTH = 1280;                   // 窗体宽度
    public static final int HEIGHT = 720;                   // 窗体高度
    public static final String TITLE = "name";              // 窗体标题

    public static CardLayout cardLayout = new CardLayout();

    public static JPanel mainPanel = new JPanel();              //封面
    private JPanel contentPane = new JPanel();                  //中间界面
    private GamePanel gamePanel = new GamePanel();              //游戏主界面
//    private RulePanel rulePanel = new RulePanel();
//    private OnlineBattlePanel onlineBattlePanel = new OnlineBattlePanel();

//尝试新建一个窗口并使其出现
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame_LD frm = new MainFrame_LD();
                    frm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
//        frm.add(new Coverpanel());
    //封面窗口设定
    public MainFrame_LD(){
        setIconImage(Toolkit.getDefaultToolkit().getImage("pic"+ File.separator+"图标1.png"));            //图标
        setResizable(false);                        //窗体不可改变大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         //关闭键结束运行
        setTitle(TITLE);                    // 窗口标题显示


        setSize(WIDTH,HEIGHT);          //设置窗口大小
        setLocationRelativeTo(null);            //设置窗口出现位置 null = 居中
        contentPane.setSize(WIDTH, HEIGHT);     //设置中间界面出现大小？？
        contentPane.setLayout(null);            //设置中间界面出现位置？

        mainPanel.setLayout(cardLayout);
        mainPanel.add(contentPane);

/*封面按键？
* */
        ImageIcon images[][] = new  ImageIcon[2][4];
        for (int i =0;i<3;i++){
            for (int j =0;j<2;j++){
              ImageIcon img = new ImageIcon("pic"+File.separator+ j + i +".png");
              img.setImage(img.getImage().getScaledInstance(WIDTH/8,HEIGHT*10/72,Image.SCALE_DEFAULT));
              images[j][i] = img;
            }
        }

        mainPanel.add(contentPane, "主界面");         //添加页面及索引？
        mainPanel.add(gamePanel, "new game");

        setContentPane(mainPanel);

        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setSize(WIDTH,HEIGHT);
        ImageIcon imageIcon = new ImageIcon("pic"+ File.separator+"coverPage1.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_DEFAULT));
        lblNewLabel.setIcon(imageIcon);

        JPanel panelMenu = new JPanel();
        panelMenu.setOpaque(false);         //透明度
        panelMenu.setBounds((int)(WIDTH * 7/16), (int)(HEIGHT / 2), 160, 200);

        panelMenu.setVisible(true);
        contentPane.add(panelMenu);
        panelMenu.setLayout(new GridLayout(2,1,0,0));

        JButton buttonLocalGame = new MenuButton();         //新游戏按键
        buttonLocalGame.setPreferredSize(new Dimension(WIDTH/8,HEIGHT*10/36));

        buttonLocalGame.setIcon(images[0][0]);
        panelMenu.add(buttonLocalGame);
        buttonLocalGame.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {           //松开
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][1]);
                cardLayout.show(mainPanel, "new game");
            }

            @Override
            public void mousePressed(MouseEvent e) {            //按下
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][1]);
            }

            @Override
            public void mouseExited(MouseEvent e) {             //移开
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {            //移入
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][2]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
        JButton buttonExit = new MenuButton();          //退出游戏按键
        buttonExit.setIcon(images[1][0]);
        panelMenu.add(buttonExit);
        buttonExit.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[1][0]);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[1][2]);
                dispose();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[1][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[1][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        contentPane.add(lblNewLabel);




    }
}
