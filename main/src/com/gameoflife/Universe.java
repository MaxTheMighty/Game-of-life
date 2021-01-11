package com.gameoflife;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

class Universe {

    int dim;
    int updateCount = 0;
    int cellCount = 0;
    Random rand;
    char[][] currentBoard;
    char[][] nextBoard;
    boolean cleared = false;
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";

    public Universe(int dim, int randSeed){
        this.dim = dim;
        this.rand = new Random(randSeed);
        this.currentBoard = new char[dim][dim];
        this.nextBoard = new char[dim][dim];
        for(int y = 0; y < this.currentBoard.length; y++){
            for(int x = 0; x < this.currentBoard[0].length;x++){
                currentBoard[y][x] = rand.nextBoolean() ?  'O' : ' ';
                nextBoard[y][x] = ' ';
            }
        }
        setCellCount();
    }

    public Universe(char[][] board){
        if(board.length != board[0].length){
            this.dim = -1;
            return;

        }
        //may be inefficient
        this.nextBoard = new char[board.length][board.length];
        //check if the input board is valid
        for(int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                if (board[y][x] != 'O' && board[y][x] != ' ') {
                    System.out.println("Invalid board");
                    this.dim = -1;
                    return;

                }
                nextBoard[y][x] = ' ';
            }
        }
        currentBoard = board;
        dim = board.length;
        System.out.println(dim);


    }

    public int getCellNeighbors(int y, int x){
        int counter = 0;
        int bound = this.dim;
        for(int i = -1; i < 2; i++){
            for(int k = -1; k < 2; k++){
                int xPos = (x+k)%bound;
                int yPos = (y+i)%bound;
                if(xPos == -1){xPos = bound-1;}
                if(yPos == -1){yPos = bound-1;}

                //System.out.print(yPos + "," + xPos + " ");
                //System.out.print(yPos + "," + xPos + " ");

                if(currentBoard[yPos][xPos] == 'O' && !(xPos == x && yPos == y)){
                    //System.out.println("Cell found at " + yPos + "," + xPos);
                    counter++;
                }
            }
            //System.out.println();
        }
        return counter;
    }
    //debug
    public void printNeighborsAll(){
        for(int y = 0; y < currentBoard.length; y++){
            for(int x = 0; x < currentBoard[0].length; x++){
                int p = getCellNeighbors(y,x);
                if(p > 3 || p < 2){
//                    System.out.print(ANSI_RED + p + ANSI_RESET + " ");
                } else {
                    System.out.print(p + " ");
                }

            }
            System.out.println("");
        }
    }

    public boolean getCleared(){
        return this.cleared;
    }
    public int getUpdateCount(){
        return this.updateCount;
    }
    public char getNextCell(int y, int x){
        int neighbors = getCellNeighbors(y, x);
        char cell = currentBoard[y][x];
        if (cell == 'O'){
            if(neighbors < 2 || neighbors > 3){
                return ' ';
            }
            else{ return 'O'; }
        } else if(cell == ' '){ //empty cell
            if(neighbors == 3){
                return 'O';
            } else {
                return ' ';
            }
        }
        System.out.println("ERROR");
        return 'X';


    }

    public void updateBoard(){

        for(int y = 0; y < nextBoard.length; y++){
            for(int x = 0; x < nextBoard[0].length; x++){
                nextBoard[y][x] = getNextCell(y,x);

            }
        }

        for(int y = 0; y < nextBoard.length; y++){
            currentBoard[y] = nextBoard[y].clone();
        }


        this.cleared = false;
        updateCount++;
        setCellCount();
    }

    public void setCellCount(){
        int s = 0;
        for(int y = 0; y < currentBoard.length; y++){
            for(int x = 0; x < currentBoard[0].length; x++){
                if(currentBoard[y][x] == 'O'){
                    s++;
                }

            }
        }
        cellCount = s;
    }


    public String toString() {
        StringBuilder out = new StringBuilder();
        for (char[] chars : this.currentBoard) {
            for (int x = 0; x < this.currentBoard[0].length; x++) {
                out.append(chars[x]);
            }
            out.append("\n");
        }
        return out.toString();
    }

    public boolean empty(){
        for(int y = 0; y < currentBoard.length; y++){
            for(int x = 0; x < currentBoard[y].length; x++){
                if(currentBoard[y][x] == 'O'){
                    return false;
                }
            }
        }
        return true;
    }

    public void invert(int x, int y){
        this.cleared = false;
        currentBoard[y][x] = (currentBoard[y][x] == 'O' ? '_' : 'O');
    }

    public void clear(){
        for(char[] row: currentBoard){
            Arrays.fill(row,' ');
        }
        this.cleared = true;
    }
}