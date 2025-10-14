import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InteractiveCohenSutherland extends Frame implements MouseListener {
    // Clipping window
    final int xmin = 100, ymin = 100, xmax = 300, ymax = 250;
    ArrayList<Point> points = new ArrayList<>();

    // Region codes
    final int INSIDE = 0; // 0000
    final int LEFT = 1;   // 0001
    final int RIGHT = 2;  // 0010
    final int BOTTOM = 4; // 0100
    final int TOP = 8;    // 1000

    public InteractiveCohenSutherland() {
        super("Interactive Cohen-Sutherland Clipping");
        setSize(500, 500);
        setLocationRelativeTo(null);
        addMouseListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private int computeCode(double x, double y) {
        int code = INSIDE;
        if (x < xmin) code |= LEFT;
        else if (x > xmax) code |= RIGHT;
        if (y < ymin) code |= BOTTOM;
        else if (y > ymax) code |= TOP;
        return code;
    }

    /**
     * Implements the Cohen-Sutherland line clipping algorithm.
     * Returns an array of two Points for the clipped line, or null if rejected.
     */
    private Point[] clipLine(Point p1, Point p2) {
        double x1 = p1.x, y1 = p1.y;
        double x2 = p2.x, y2 = p2.y;
        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);
        boolean accept = false;
        while (true) {
            // If both endpoints inside: trivially accept
            if ((code1 | code2) == 0) {
                accept = true;
                break;
            }
            // If endpoints share an outside region: trivially reject
            else if ((code1 & code2) != 0) {
                break;
            }
            // Otherwise, calculate intersection
            else {
                double x = 0, y = 0;
                int codeOut = (code1 != 0) ? code1 : code2;
                if ((codeOut & TOP) != 0) {
                    // point is above the clip rectangle
                    if (y2 != y1) {
                        x = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
                    } else {
                        x = x1; // degenerate, keep x
                    }
                    y = ymax;
                } else if ((codeOut & BOTTOM) != 0) {
                    // point is below the clip rectangle
                    if (y2 != y1) {
                        x = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
                    } else {
                        x = x1;
                    }
                    y = ymin;
                } else if ((codeOut & RIGHT) != 0) {
                    // point is to the right of clip rectangle
                    if (x2 != x1) {
                        y = y1 + (y2 - y1) * (xmax - x1) / (x2 - x1);
                    } else {
                        y = y1;
                    }
                    x = xmax;
                } else if ((codeOut & LEFT) != 0) {
                    // point is to the left of clip rectangle
                    if (x2 != x1) {
                        y = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
                    } else {
                        y = y1;
                    }
                    x = xmin;
                }
                // Replace outside point with intersection point
                if (codeOut == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    code2 = computeCode(x2, y2);
                }
            }
        }
        if (accept) {
            return new Point[] {
                new Point((int)Math.round(x1), (int)Math.round(y1)),
                new Point((int)Math.round(x2), (int)Math.round(y2))
            };
        } else {
            return null;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // clear background and let Frame draw decorations
        Graphics2D g2 = (Graphics2D) g;
        // draw clipping window
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);

        if (points.size() >= 1) {
            // draw markers for selected points
            for (Point pt : points) {
                g2.setColor(Color.BLUE);
                g2.fillOval(pt.x - 3, pt.y - 3, 6, 6);
            }
        }
        if (points.size() == 2) {
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            // draw original line (gray)
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            // clip and draw result
            Point[] clipped = clipLine(p1, p2);
            if (clipped != null) {
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(clipped[0].x, clipped[0].y, clipped[1].x, clipped[1].y);
                // optional: draw intersection points
                g2.setColor(Color.MAGENTA);
                g2.fillOval(clipped[0].x - 3, clipped[0].y - 3, 6, 6);
                g2.fillOval(clipped[1].x - 3, clipped[1].y - 3, 6, 6);
            } else {
                // If rejected, show a small cross at mid-point (optional)
                g2.setColor(Color.DARK_GRAY);
                int mx = (p1.x + p2.x) / 2;
                int my = (p1.y + p2.y) / 2;
                g2.drawLine(mx - 6, my - 6, mx + 6, my + 6);
                g2.drawLine(mx - 6, my + 6, mx + 6, my - 6);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Left click — record points. If there are already 2, start over.
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (points.size() == 2) points.clear();
            points.add(e.getPoint());
            repaint();
        }
        // Right click — clear points
        else if (e.getButton() == MouseEvent.BUTTON3) {
            points.clear();
            repaint();
        }
    }

    // unused MouseListener methods
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        // Launch GUI on AWT event thread
        EventQueue.invokeLater(() -> new InteractiveCohenSutherland());
    }
}