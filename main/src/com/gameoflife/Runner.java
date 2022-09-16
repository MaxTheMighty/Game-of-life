package com.gameoflife;



public class Runner {

    public static void main(String[] args) throws Exception {

        Universe myBoard  = new Universe(100,20);
        FileReader reader = new FileReader("/Users/maxwell/Documents/Java/Game-of-life/main/src/com/gameoflife/board1.txt");
        reader.fillGrid();
        myBoard.insertCellsFromInput(reader.grid);

        final Interface gui = new Interface(myBoard);
        gui.setVisible(true);

        while(true){

            try{
                Thread.sleep(gui.getUpdateRate());
                } catch (InterruptedException e){

                }


            if(gui.running){
                gui.update();
            }

        }
    }
}
