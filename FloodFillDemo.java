import java.awt.*;
import java.awt.event.*;

public class FloodFillDemo extends Frame implements MouseListener {
    int width = 400, height = 400;
    int[][] pixels = new int[width][height]; // 0=empty, 1=boundary, 2=filled

    public FloodFillDemo() {
        setTitle("Flood Fill Demo");
        setSize(width, height);
        addMouseListener(this);

        // Create a rectangle boundary
        for (int x = 100; x <= 300; x++) {
            pixels[x][100] = 1;
            pixels[x][300] = 1;
        }
        for (int y = 100; y <= 300; y++) {
            pixels[100][y] = 1;
            pixels[300][y] = 1;
        }
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Image offscreen = createImage(width, height);
        Graphics buffer = offscreen.getGraphics();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (pixels[x][y] == 1) {
                    buffer.setColor(Color.BLACK); // boundary
                    buffer.drawLine(x, y, x, y);
                } else if (pixels[x][y] == 2) {
                    buffer.setColor(Color.RED); // filled
                    buffer.drawLine(x, y, x, y);
                }
            }
        }
        g.drawImage(offscreen, 0, 0, this);
    }

    public void floodFill(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return;
        if (pixels[x][y] != 0)
            return;
        pixels[x][y] = 2; // fill color
        floodFill(x + 1, y);
        floodFill(x - 1, y);
        floodFill(x, y + 1);
        floodFill(x, y - 1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Insets insets = getInsets();
        int x = e.getX() - insets.left;
        int y = e.getY() - insets.top;
        if (x >= 0 && x < width && y >= 0 && y < height) {
            floodFill(x, y);
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new FloodFillDemo();
    }
}