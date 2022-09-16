package com.gameoflife;

import java.util.Arrays;
import java.util.Random;

class Universe {

    int dim;
    int updateCount = 0;
    int cellCount = 0;
    Random rand;
    boolean[][] currentBoard;
    boolean[][] nextBoard;
    boolean cleared = false;

    public Universe(){
        this.dim = 30;
        this.rand = new Random(System.currentTimeMillis());
        this.currentBoard = new boolean[dim][dim];
        this.nextBoard = new boolean[dim][dim];
        initBoards();
        setCellCount();
    }
    public Universe(int dim, int randSeed){
        this.dim = dim;
        this.rand = new Random(randSeed);
        this.currentBoard = new boolean[dim][dim];
        this.nextBoard = new boolean[dim][dim];
        initBoards();
        setCellCount();
    }

    public Universe(boolean[][] board){
        if(board.length != board[0].length){
            this.dim = -1;
            return;

        }
        this.nextBoard = new boolean[board.length][board.length];
        currentBoard = board;
        dim = board.length;
    }


    public void export(){

    }
    public void updateBoard(){
        setAllNextCells();
        cloneNextBoard();
        this.cleared = false;
        updateCount++;
        setCellCount();
    }


    public void insertCellsFromInput(boolean[][] input) throws Exception{
        if(inputGridFits(input)){
            throw new Exception("Invalid input size!");
        }
        clear();

        for(int row = 0; row < input.length; row++){
            boolean[] currentRow = input[row];
            for(int col = 0; col < currentRow.length; col++){
                currentBoard[row][col] = currentRow[col];
            }
        }
        this.cleared = false;
        setCellCount();
        updateCount = 0;

    }



    private boolean inputGridFits(boolean[][] input) {
        return input.length > this.dim && input[0].length > this.dim;
    }

    private void cloneNextBoard() {
        for(int y = 0; y < nextBoard.length; y++){
            currentBoard[y] = nextBoard[y].clone();
        }
    }

    private void setAllNextCells() {
        for(int y = 0; y < nextBoard.length; y++){
            for(int x = 0; x < nextBoard[0].length; x++){
                nextBoard[y][x] = getNextCell(y,x);
            }
        }
    }

    public void initBoards(){
        for(int y = 0; y < this.currentBoard.length; y++){
            for(int x = 0; x < this.currentBoard[0].length;x++){
                currentBoard[y][x] = rand.nextBoolean();
                nextBoard[y][x] = false;
            }
        }
    }


    public int getAliveNeighborCount(int y, int x){
        int counter = 0;

        for(int i = -1; i < 2; i++){
            for(int k = -1; k < 2; k++){
                int xPos = boundCoordinate(x,k);
                int yPos = boundCoordinate(y,i);
                if(!posValid(xPos)) {xPos = this.dim-1;}
                if(!posValid(yPos)) {yPos = this.dim-1;}
                if(cellAlive(xPos,yPos) && !(xPos == x && yPos == y)){
                    counter++;
                }
            }
        }
        return counter;
    }





    public boolean getNextCell(int y, int x){
        int neighbors = getAliveNeighborCount(y, x);
        if (cellAlive(x,y)){
            return cellLives(neighbors);
        } else {
            return cellBorn(neighbors);
        }


    }





    public void setCellCount(){
        int s = 0;
        for(int y = 0; y < currentBoard.length; y++){
            for(int x = 0; x < currentBoard[0].length; x++){
                if(cellAlive(x,y)){
                    s++;
                }
            }
        }
        cellCount = s;
    }


    public String toString() {
        StringBuilder out = new StringBuilder();
        for (boolean[] chars : this.currentBoard) {
            for (int x = 0; x < this.currentBoard[0].length; x++) {
                out.append(chars[x]);
            }
            out.append("\n");
        }
        return out.toString();
    }

    public void invert(int x, int y){
        this.cleared = false;
        currentBoard[y][x] = !currentBoard[y][x];
    }
    public boolean cellLives(int neighbors){
        return (neighbors == 2 || neighbors == 3);
    }

    public boolean cellBorn(int neighbors){
        return neighbors == 3;
    }
    public void clear(){
        for(boolean[] row: currentBoard){
            Arrays.fill(row,false);
        }
        this.cleared = true;
    }
    public int boundCoordinate(int posIn, int offset){
        return (posIn+offset)%this.dim;

    }
    public boolean cellAlive(int x, int y){
        return this.currentBoard[y][x];
    }

    public boolean posValid(int posIn){
        return posIn != -1;
    }



}