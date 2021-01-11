package com.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.Flow;

public class Interface extends JFrame{
    private JPanel mainPanel;
    private JButton pauseButton;
    private JButton runButton;
    private JButton stepButton;
    private JButton clearButton;
    private JToolBar toolBar;
    private JSlider speedSlider;
    private JPanel grid;
    private JTextArea tesing123TextArea;
    private JTextArea stats;
    private boolean running; //
    private final Universe universe;
    private JButton[][] cells;

    public Interface(){
        super();
        this.universe = new Universe(10,200);
        init();
    }

    public Interface(Universe un){
        super();
        this.universe = un;
        init();


    }
    public boolean getRunning(){
        return running;
    }

    public void setRunning(boolean in){
        running = in;
    }

    public void update(){
        this.universe.updateBoard();
        this.updateCellButtons();
        this.stats.setText("Generation: " + this.universe.getUpdateCount());
    }


    //anonymous classes
    private void createListeners(){
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRunning(false);
                System.out.println("pause pressed");
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRunning(true);
                System.out.println("Run pressed");
            }
        });
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Step pressed");
                update();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clear pressed");
                universe.clear();
                updateCellButtons();
            }
        });
        mainPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                //https://stackoverflow.com/questions/33925884/zoom-in-and-out-of-jpanel
            }
        });
    }
    private void init(){
        cells = new JButton[this.universe.dim][this.universe.dim];
        createUIComponents();
        createListeners();

        this.running = false;
        for(int y = 0; y < this.universe.dim; y++){
            for(int x = 0; x < this.universe.dim; x++){
                JButton b = createButton();
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton)(e.getSource());
                        int x = (int)button.getClientProperty('x');
                        int y = (int)button.getClientProperty('y');
                        System.out.println(x + " " + y);
                        if(!getRunning()){
                            universe.invert(x,y);
                        }
                        updateSingleCell(x,y);
                    }
                });
                b.putClientProperty('x',x);
                b.putClientProperty('y',y);

                cells[y][x] = b;
                cells[y][x].setBackground(this.universe.currentBoard[y][x] == 'O' ? Color.white : Color.darkGray);
                this.grid.add(b);
            }
        }

    }


    private void createUIComponents() {
        // I ended up just making all of the components myself, LOL
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.grid = new JPanel(new GridLayout(this.universe.dim,this.universe.dim,0,0));
        this.grid.setVisible(true);
        this.mainPanel.add(this.grid);
        initToolbar();
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(600,600));
        this.pack();
    }

    //from https://stackoverflow.com/questions/1839074/howto-make-jbutton-with-simple-flat-style
    private JButton createButton(){
        JButton out = new JButton();
        out.setVisible(true);
        out.setBackground(Color.white);
        return out;

    }

    public int getUpdateRate(){
        return this.speedSlider.getValue();
    }
    private void initToolbar(){
        this.toolBar = new JToolBar();
        this.runButton = createButton();
        this.pauseButton = createButton();
        this.stepButton = createButton();
        this.speedSlider = new JSlider();
        this.speedSlider.setValue(500);
        this.stats = new JTextArea("Generation: 0");
        this.stats.setVisible(true);
        this.speedSlider.setMinimum(10);
        this.speedSlider.setMaximum(1000);
        this.speedSlider.setVisible(true);
        this.clearButton = createButton();
        this.clearButton.setText("Clear");
        this.runButton.setText("Run");
        this.pauseButton.setText("Pause");
        this.stepButton.setText("Step");
        Dimension dims = new Dimension(70,30);
        //maybe move this to createButton func
        this.runButton.setPreferredSize(dims);
        this.pauseButton.setPreferredSize(dims);
        this.stepButton.setPreferredSize(dims);
        this.clearButton.setPreferredSize(dims);
        this.toolBar.setLayout(new FlowLayout());
        this.toolBar.add(runButton,BorderLayout.CENTER);
        this.toolBar.add(pauseButton);
        this.toolBar.add(stepButton);
        this.toolBar.add(clearButton);
        this.toolBar.add(speedSlider);
        this.toolBar.add(stats);
        this.toolBar.setVisible(true);
        this.toolBar.setPreferredSize(new Dimension(70,40));
        this.mainPanel.add(this.toolBar, BorderLayout.SOUTH);

    }


    private void updateCellButtons(){
        for(int y = 0; y < this.universe.dim; y++){
            for(int x = 0; x < this.universe.dim; x++){
                cells[y][x].setBackground(this.universe.currentBoard[y][x] == 'O' ? Color.white : Color.darkGray);
            }
        }
    }
    private void updateSingleCell(int x, int y){
        cells[y][x].setBackground(this.universe.currentBoard[y][x] == 'O' ? Color.white : Color.darkGray);
    }
}
