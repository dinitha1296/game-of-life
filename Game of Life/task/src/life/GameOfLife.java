package life;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame implements ActionListener{

    private final GridPanel gridPanel = new GridPanel();
    private final JLabel generationLabel = new JLabel();
    private final JLabel aliveLabel = new JLabel();
    private final JToggleButton playToggleButton = new JToggleButton();
    private final JButton stopButton = new JButton();
    private final JButton resetButton = new JButton();
    private final JSlider speedChangeSlider = new JSlider();

    private static final int CELL_SIZE = 3;
    private final Controller controller;

    public GameOfLife() {
        setName("GameOfLife");
        int gridSize = 320;
        Dimension dimensions =
                new Dimension(
                        gridSize * CELL_SIZE + 336,
                        gridSize * CELL_SIZE + 60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gridSize * CELL_SIZE + 336,
                gridSize * CELL_SIZE + 60);
        setLayout(null);
        setVisible(true);
        //JPanel mainPanel = (JPanel) getContentPane();
        setSize(dimensions);

        generationLabel.setName("GenerationLabel");
        generationLabel.setVisible(true);
        generationLabel.setText("Generation #0");
        generationLabel.setBounds(12, 68, 300,48);
        generationLabel.setFont(new FontUIResource(generationLabel.getFont().getName(),
                FontUIResource.PLAIN, 24));
        add(generationLabel);

        aliveLabel.setName("AliveLabel");
        aliveLabel.setVisible(true);
        aliveLabel.setText("Alive: 0");
        aliveLabel.setBounds(12, 104, 300, 48);
        aliveLabel.setFont(new FontUIResource(aliveLabel.getFont().getName(),
                FontUIResource.PLAIN, 24));
        add(aliveLabel);

        gridPanel.setName("GridPanel");
        gridPanel.setVisible(true);
        gridPanel.setBounds(324, 12,
                gridSize * CELL_SIZE + 1,
                gridSize * CELL_SIZE + 1);
        add(gridPanel);

        playToggleButton.setName("PlayToggleButton");
        playToggleButton.setVisible(true);
        playToggleButton.setBounds(12, 12, 88, 44);
        playToggleButton.addActionListener(this);
        playToggleButton.setActionCommand("toggle play/pause");
        add(playToggleButton);

        stopButton.setName("StopButton");
        stopButton.setVisible(true);
        stopButton.setEnabled(false);
        stopButton.setBounds(112, 12, 88, 44);
        stopButton.addActionListener(this);
        stopButton.setActionCommand("stop");
        add(stopButton);

        resetButton.setName("ResetButton");
        resetButton.setVisible(true);
        resetButton.setBounds(212, 12, 88, 44);
        resetButton.addActionListener(this);
        resetButton.setActionCommand("reset");
        add(resetButton);

        speedChangeSlider.setName("SpeedChangeSlider");
        speedChangeSlider.setVisible(true);
        speedChangeSlider.setBounds(12, 164, 300, 90);
        speedChangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                controller.setWaitTime(speedChangeSlider.getValue());
            }
        });
        speedChangeSlider.setValue(50);
        add(speedChangeSlider);

        //Label in the slider
        JLabel speedChangeSliderLabel = new JLabel();
        speedChangeSliderLabel.setName("SpeedChangeSliderLabel");
        speedChangeSliderLabel.setVisible(true);
        speedChangeSliderLabel.setText("Speed mode:");
        speedChangeSliderLabel.setBounds(6, 10, 300, 30);
        speedChangeSliderLabel.setFont(new FontUIResource(aliveLabel.getFont().getName(),
                FontUIResource.PLAIN, 18));
        speedChangeSlider.add(speedChangeSliderLabel);

        JButton colorChangeButton = new JButton();
        colorChangeButton.setName("ColorChangeButton");
        colorChangeButton.setText("Change Color");
        colorChangeButton.setBounds(12, 264, 300, 44);
        colorChangeButton.addActionListener(this);
        colorChangeButton.setActionCommand("changeColor");
        add(colorChangeButton);

        this.controller = new Controller(this);
    }

    void setGenerationNumber(int generationNumber) {
        generationLabel.setText("Generation #" + generationNumber);
    }

    void setLifeCount(int lifeCount) {
        aliveLabel.setText("Alive: " + lifeCount);
    }

    void reDrawGrid(boolean[][] grid) {
        gridPanel.drawGrid(grid);
    }

    void enablePlayToggleButton(boolean enable) {
        playToggleButton.setEnabled(enable);
    }

    void enableStopButton(boolean enable) {
        stopButton.setEnabled(enable);
    }

    void enableResetButton(boolean enable) {
        resetButton.setEnabled(enable);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        switch (action) {
            case "toggle play/pause": controller.playToggle(); break;
            case "stop": controller.stop(); break;
            case "reset": controller.reset(); break;
            case "changeColor":
                if (controller.isRunning()) playToggleButton.doClick();
                Color newColor = JColorChooser.showDialog(
                        this,
                        "Select a color for the cells",
                        gridPanel.getCellColor());
                gridPanel.setCellColor(newColor);
                gridPanel.drawGrid(gridPanel.getLastGrid());
            default: break;
        }
    }
}