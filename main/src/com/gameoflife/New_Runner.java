package com.gameoflife;

import javax.swing.*;

public class New_Runner {

    public static void main(String[] args){
        char[][] board = {
                {' ',' ',' ',' ',' '},
                {' ','Y','O',' ',' '},
                {'O',' ','O',' ',' '},
                {' ','O','O',' ',' '},
                {' ',' ',' ',' ',' '}
        };
        Universe myBoard = new Universe(board);
        if(myBoard.dim == -1){
            myBoard = new Universe(100,200);
        }
        final Interface gui = new Interface(myBoard);
        gui.setVisible(true);

        while(true){
//            if(myBoard.empty() && !myBoard.getCleared()){
//                break;
//            }

            try{
                Thread.sleep(gui.getUpdateRate());
            } catch (InterruptedException e){}
            if(gui.getRunning()){
                System.out.println("Not running");
                gui.update();
            }


        }




    }
}
