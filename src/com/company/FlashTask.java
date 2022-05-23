package com.company;

import java.util.TimerTask;

public class FlashTask extends TimerTask {

    GamePanel gamePanel;
    int time;
    public FlashTask(GamePanel gamePanel){
        this.gamePanel =gamePanel;
        this.time = gamePanel.getRound();
    }

    @Override
    public void run() {
        if(time!=0){
            System.out.println("huifang");
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<gamePanel.currentGame.round-time;i++){
                System.out.println(i);
                stringBuilder.append(gamePanel.currentGame.storeBoard.stored.get(i));
            }
            time--;
            AGame n=AGame.load(AGame.splitString(stringBuilder.toString()));
            gamePanel.setPieces(n.pieces);
            gamePanel.setBoard(n.board);
            gamePanel.setRound(n.round);
            gamePanel.setCurrentPlayer(n.currentPlayer);
            gamePanel.repaint();
        }else{
            gamePanel.inReview=false;
        }

    }



}
