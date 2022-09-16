package com.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Interface extends JFrame{
    private JPanel mainPanel;
    private JButton pauseButton;
    private JButton runButton;
    private JButton stepButton;
    private JButton clearButton;
    private JToolBar toolBar;
    private JSlider speedSlider;
    private JPanel grid;

    private JTextArea stats;
    public boolean running; //
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
    private void init(){
        cells = new JButton[this.universe.dim][this.universe.dim];
        createUIComponents();
        createListeners();

        this.running = false;
        createButtons();

    }



    public void update(){
        this.universe.updateBoard();
        this.updateCellButtons();
        this.stats.setText("Generation: " + this.universe.updateCount);
        updatePanel();
    }

    private void updatePanel() {
        this.mainPanel.repaint();
        this.mainPanel.revalidate();
    }


    //anonymous classes
    private void createListeners(){
        setPauseListener();
        setRunListener();
        setStepListener();
        setClearListener();
        setMouseWheelListener();
    }






    private void AddListenerToButton(JButton b) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JButton button = (JButton)(e.getSource());
                int x = (int)button.getClientProperty('x');
                int y = (int)button.getClientProperty('y');

                if(!running){
                    universe.invert(x,y);
                }
                updateSingleCell(x,y);
            }
        });
    }


    private void createUIComponents() {
        createGridLayout();
        createMainPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createToolbarWithButtons();
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(600,600));
        this.pack();
    }

    private void createMainPanel() {
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setVisible(true);
        this.mainPanel.add(this.grid);
    }

    private void createGridLayout() {
        this.grid = new JPanel(new GridLayout(this.universe.dim,this.universe.dim,0,0));
        this.grid.setVisible(true);
    }

    //from https://stackoverflow.com/questions/1839074/howto-make-jbutton-with-simple-flat-style
    private JButton createButton(){
        JButton out = new JButton();
        out.setVisible(true);
        out.setBackground(Color.white);
        return out;

    }

    public int getUpdateRate(){
        return this.speedSlider.getMaximum() - this.speedSlider.getValue();
    }

    private void setMouseWheelListener() {
        mainPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                //https://stackoverflow.com/questions/33925884/zoom-in-and-out-of-jpanel
            }
        });
    }

    private void setClearListener() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clear pressed");
                universe.clear();
                updateCellButtons();
            }
        });
    }

    private void setStepListener() {
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Step pressed");
                System.out.println(e.getModifiers());
                update();
            }
        });
    }

    private void setRunListener() {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = true;
                System.out.println("Run pressed");
            }
        });
    }

    private void setPauseListener() {
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = false;
                System.out.println("Pause pressed");
            }
        });
    }


    private void createToolbarWithButtons(){
        Dimension moduleDimension = new Dimension(70,30);
        createRunButton(moduleDimension);
        createPauseButton(moduleDimension);
        createStepButton(moduleDimension);
        createSlider();
        createStatsArea();
        createClearButton(moduleDimension);
        createToolBar();
        this.mainPanel.add(this.toolBar, BorderLayout.SOUTH);

    }

    private void createToolBar() {
        this.toolBar = new JToolBar();
        this.toolBar.setLayout(new FlowLayout());
        this.toolBar.add(runButton,BorderLayout.CENTER);
        this.toolBar.add(pauseButton);
        this.toolBar.add(stepButton);
        this.toolBar.add(clearButton);
        this.toolBar.add(speedSlider);
        this.toolBar.add(stats);
        this.toolBar.setVisible(true);
        this.toolBar.setPreferredSize(new Dimension(70,40));
    }

    private void createStatsArea() {
        this.stats = new JTextArea("Generation: 0");
        this.stats.setVisible(true);
    }

    private void createClearButton(Dimension moduleDimension) {
        this.clearButton = createButton();
        this.clearButton.setText("Clear");
        this.clearButton.setPreferredSize(moduleDimension);

    }

    private void createStepButton(Dimension moduleDimension) {
        this.stepButton = createButton();
        this.stepButton.setText("Step");
        this.stepButton.setPreferredSize(moduleDimension);
    }

    private void createPauseButton(Dimension moduleDimension) {
        this.pauseButton = createButton();
        this.pauseButton.setText("Pause");
        this.pauseButton.setPreferredSize(moduleDimension);
    }

    private void createRunButton(Dimension moduleDimension) {
        this.runButton = createButton();
        this.runButton.setText("Run");
        this.runButton.setPreferredSize(moduleDimension);
    }

    private void createSlider() {
        this.speedSlider = new JSlider();
        this.speedSlider.setValue(500);
        this.speedSlider.setMinimum(1);
        this.speedSlider.setMaximum(500);
        this.speedSlider.setVisible(true);

    }
    private void createButtons() {
        for(int y = 0; y < this.universe.dim; y++){
            for(int x = 0; x < this.universe.dim; x++){
                JButton button = createButton();
                button.setOpaque(true);
                button.setBorderPainted(false);
                AddListenerToButton(button);
                button.putClientProperty('x',x);
                button.putClientProperty('y',y);
                cells[y][x] = button;
                updateSingleCell(x,y);
                this.grid.add(button);
            }
        }
    }


    private void updateCellButtons(){
        for(int y = 0; y < this.universe.dim; y++){
            for(int x = 0; x < this.universe.dim; x++){
                updateSingleCell(x,y);
            }
        }
    }
    private void updateSingleCell(int x, int y){
        cells[y][x].setBackground(this.universe.cellAlive(x,y) ? Color.BLACK : Color.WHITE);
    }
}
