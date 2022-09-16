package com.gameoflife;

import java.io.FileNotFoundException;


public class Tester {
    public static void main(String[] args){
        FileReader reader;

        {
            try {
                reader = new FileReader("/Users/maxwell/Documents/Java/Game-of-life/main/src/com/gameoflife/board1.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        reader.fillGrid();

    }

}
