package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;


public class MainFrame_LD extends JFrame{

    public static final int WIDTH = 1280;                   // 窗体宽度
    public static final int HEIGHT = 720;                   // 窗体高度
    public static final String TITLE = "name";              // 窗体标题

    public static CardLayout cardLayout = new CardLayout();

    public static JPanel mainPanel = new JPanel();              //封面
    private JPanel contentPane = new JPanel();                  //中间界面
    public GamePanel gamePanel = new GamePanel(false);               //游戏主界面
    public GamePanel aigamePanel =new GamePanel(true);



//尝试新建一个窗口并使其出现
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame_LD frm = new MainFrame_LD();
                    frm.setVisible(true);
                    Music test = new Music("audio"+File.separator+"Sergei Prokofiev - Dance Of The Knights_01.wav");
//                    test.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

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



/*封面按键？
* */
        ImageIcon images[][] = new  ImageIcon[4][3];
        for (int i =0;i<4;i++){
            for (int j =0;j<3;j++){
              ImageIcon img = new ImageIcon("pic"+File.separator+"botton0"+ i + j +".png");
              img.setImage(img.getImage().getScaledInstance(WIDTH/8,HEIGHT*10/72,Image.SCALE_DEFAULT));
              images[i][j] = img;
            }
        }

        mainPanel.add(contentPane, "主界面");         //添加页面及索引
        mainPanel.add(gamePanel, "new game");

        mainPanel.add(aigamePanel,"人机对战");

        setContentPane(mainPanel);

        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setSize(WIDTH,HEIGHT);
        ImageIcon imageIcon = new ImageIcon("pic"+ File.separator+"coverPage1.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_AREA_AVERAGING));
        lblNewLabel.setIcon(imageIcon);

        JPanel panelMenu = new JPanel();
        panelMenu.setOpaque(false);         //透明度
        panelMenu.setBounds((int)(WIDTH * 7/16), (int)(HEIGHT / 2), 160, 320);

        panelMenu.setVisible(true);
        contentPane.add(panelMenu);
        panelMenu.setLayout(new GridLayout(4,1,0,0));

        JButton buttonLocalGame = new MenuButton();         //新游戏按键
        buttonLocalGame.setPreferredSize(new Dimension(WIDTH/8,HEIGHT*10/36));
        buttonLocalGame.setIcon(images[0][0]);
        panelMenu.add(buttonLocalGame);
        buttonLocalGame.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {           //松开
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][0]);
                cardLayout.show(mainPanel, "new game");
                System.out.println(gamePanel.isPvE);
            }

            @Override
            public void mousePressed(MouseEvent e) {            //按下
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][2]);
            }

            @Override
            public void mouseExited(MouseEvent e) {             //移开
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {            //移入
                // TODO Auto-generated method stub
                buttonLocalGame.setIcon(images[0][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        JButton buttonPvEGame1 = new MenuButton();         //人机简单按键
        buttonPvEGame1.setPreferredSize(new Dimension(WIDTH/8,HEIGHT*10/36));
        buttonPvEGame1.setIcon(images[1][0]);
        panelMenu.add(buttonPvEGame1);
        buttonPvEGame1.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {           //松开
                // TODO Auto-generated method stub
                buttonPvEGame1.setIcon(images[1][0]);
                cardLayout.show(mainPanel, "人机对战");
                System.out.println("ispve");
                aigamePanel.depth= 1;
            }

            @Override
            public void mousePressed(MouseEvent e) {            //按下
                // TODO Auto-generated method stub
                buttonPvEGame1.setIcon(images[1][2]);
            }

            @Override
            public void mouseExited(MouseEvent e) {             //移开
                // TODO Auto-generated method stub
                buttonPvEGame1.setIcon(images[1][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {            //移入
                // TODO Auto-generated method stub
                buttonPvEGame1.setIcon(images[1][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });


        JButton buttonPvEGame2 = new MenuButton();         //人机难按键
        buttonPvEGame2.setPreferredSize(new Dimension(WIDTH/8,HEIGHT*10/36));
        buttonPvEGame2.setIcon(images[2][0]);
        panelMenu.add(buttonPvEGame2);
        buttonPvEGame2.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {           //松开
                // TODO Auto-generated method stub
                buttonPvEGame2.setIcon(images[2][0]);
                cardLayout.show(mainPanel, "人机对战");
                System.out.println("ispve");
                aigamePanel.depth = 3;


            }

            @Override
            public void mousePressed(MouseEvent e) {            //按下
                // TODO Auto-generated method stub
                buttonPvEGame2.setIcon(images[2][2]);
            }

            @Override
            public void mouseExited(MouseEvent e) {             //移开
                // TODO Auto-generated method stub
                buttonPvEGame2.setIcon(images[2][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {            //移入
                // TODO Auto-generated method stub
                buttonPvEGame2.setIcon(images[2][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });



        JButton buttonExit = new MenuButton();          //读档游戏按键
        buttonExit.setIcon(images[3][0]);
        panelMenu.add(buttonExit);
        buttonExit.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[3][0]);
//                cardLayout.show(mainPanel, "读档界面");
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
                            gamePanel.currentGame = AGame.load(AGame.splitString(string));
                            gamePanel.loadChessboard(gamePanel.currentGame);
                            MainFrame_LD.cardLayout.show(MainFrame_LD.mainPanel, "new game" );

//                    gamePanel.currentGame=AGame.load(AGame.splitString(string));
                        } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
                            WrongDialog wrongDialog=new WrongDialog();
                            Label label = new Label();
                            label.setText("错误代码：104");
                            wrongDialog.add(label);
                            wrongDialog.setVisible(true);
                            System.out.println("104");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.out.println("104.");
                            WrongDialog wrongDialog=new WrongDialog();
                            Label label = new Label();
                            label.setText("错误代码：104");
                            wrongDialog.add(label);
                            wrongDialog.setVisible(true);
                            System.out.println("104");
                        }
                    }else{
                        WrongDialog wrongDialog=new WrongDialog();
                        Label label = new Label();
                        label.setText("错误代码：104");
                        wrongDialog.add(label);
                        wrongDialog.setVisible(true);
                        System.out.println("104");
                    }


                    //TODO: do sth
                }
                System.out.println(jfc.getSelectedFile().getName());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[3][2]);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[3][0]);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                buttonExit.setIcon(images[3][1]);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        contentPane.add(lblNewLabel);




    }
}
