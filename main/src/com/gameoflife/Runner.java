package com.gameoflife;

public class Runner {

    public static void main(String[] args){

        Universe myBoard = new Universe(20,20);

        final Interface gui = new Interface(myBoard);
        gui.setVisible(true);

        while(true){
//

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
