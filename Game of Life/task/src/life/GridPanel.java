package life;

import javax.swing.*;
import java.awt.*;

class GridPanel extends JPanel {

    private boolean[][] gridPanel;
    private final int gridSize;
    private final int cellSize;
    private Color cellColor;

    public GridPanel() {
        cellColor = Color.BLACK;
        this.gridSize = 320;
        this.cellSize = 3;
        this.gridPanel = new boolean[gridSize][gridSize];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(cellColor);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gridPanel[i][j]) {
                    g.fillRect(i * cellSize,
                            j * cellSize,
                            cellSize, cellSize);
                }
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0,
                cellSize * gridSize,
                cellSize * gridSize);
        for (int i = 0; i < cellSize * gridSize; i += cellSize) {
            g.drawLine(0, i, gridSize * cellSize, i);
            g.drawLine(i, 0, i, gridSize * cellSize);
        }
    }

    protected void drawGrid(boolean[][] gridPanel) {
        this.gridPanel = gridPanel;
        repaint();
    }

    protected Color getCellColor() {
        return cellColor;
    }

    protected void setCellColor(Color newCellColor) {
        cellColor = new Color(newCellColor.getRGB());
    }

    protected boolean[][] getLastGrid() {
        return gridPanel;
    }
}
