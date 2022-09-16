package com.gameoflife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class FileReader {
    private static final char aliveChar = 'O';
    private static final char deadChar = ' ';
    private File boardFile;
    private Scanner scanner;
    private String filePath;
    private ArrayList<String> stringsBuffer;
    public boolean[][] grid;
    private int longestRow;


    //Unsure how long the data is in the file
    //Store strings into ArrayList


    public FileReader(String fileIn) throws FileNotFoundException{
        this.filePath = fileIn;
        this.boardFile = createFileObj();
        this.scanner = createScannerObj();
        this.stringsBuffer = new ArrayList<String>();
    }


    void writeToFile(){

    }


    void fillGrid(){
        fillStrings();
        int rowCount = stringsBuffer.size();
        int colCount = longestRow;
        grid = new boolean[rowCount][colCount];

        for(int row = 0; row < rowCount; row++){
            String currentString = stringsBuffer.get(row);
            for(int col = 0; col < currentString.length(); col++){
                char currentChar = currentString.charAt(col);
                grid[row][col] = alive(currentChar);
            }
        }
    }

    void fillStrings(){
        while(scanner.hasNextLine()){
            stringsBuffer.add(getLineFromFile());
            updateLongestRow();
        }
    }



    private void updateLongestRow() {
        int length = stringsBuffer.get(stringsBuffer.size()-1).length();
        if(length > longestRow){
            longestRow = length;
        }
    }

    String getLineFromFile(){
        return scanner.nextLine();
    }


    File createFileObj(){
        File fileObj = new File(this.filePath);
        return fileObj;
    }


    Scanner createScannerObj() throws FileNotFoundException{
        Scanner scannerObj = new Scanner(this.boardFile);
        return scannerObj;
    }


    boolean alive(char input){
        return input == aliveChar;
    }

    void prettyPrint(){
        for(boolean[] row: grid){
            for(boolean cell: row){
                System.out.print(cell ? aliveChar : deadChar);
            }
            System.out.println();
        }
    }


}
