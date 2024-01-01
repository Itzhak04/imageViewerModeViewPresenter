package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageDisplay;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final List<PaintOrder> orders;
    private int shiftStart;
    private Dragged dragged = Dragged.Null;
    private Released released = Released.Null;
    private BufferedImage bitmap;


    public SwingImageDisplay() {
        this.orders = new ArrayList<>();
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                dragged.to(e.getX() - shiftStart);
            }

            public void mouseMoved(MouseEvent e) { }
        };
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                shiftStart = e.getX();
            }

            public void mouseReleased(MouseEvent e) {
                released.at(e.getX() - shiftStart);
            }

            public void mouseEntered(MouseEvent e) { }

            public void mouseExited(MouseEvent e) { }
        };
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());
        for (PaintOrder order : orders) {
            bitmap=load(order.image);
            Resizer resizer = new Resizer(new Dimension(this.getWidth(), this.getHeight()));
            Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(), bitmap.getHeight()));
            int y = (this.getHeight() - resized.height) / 2;
            g.drawImage(bitmap, order.offset, y,resized.width,resized.height, null);
        }

    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public void clear() {
        orders.clear();
        repaint();
    }

    @Override
    public void paint(String image, int offset) {
        orders.add(new PaintOrder(image, offset));
        repaint();
    }

    @Override
    public void on(Dragged dragged) {
        this.dragged = dragged != null ? dragged : Dragged.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }

    private record PaintOrder(String image, int offset) {}

    private class Resizer {
        private final Dimension dimension;

        public Resizer(Dimension dimension) {
            this.dimension = dimension;
        }

        public Dimension resize(Dimension dimension) {
            if (dimension.width <= this.dimension.width && dimension.height <= this.dimension.height) {
                return dimension;
            }else {
                double aspectRatio = (double) dimension.width / dimension.height;
                double panelAspectRatio = (double) this.dimension.width / this.dimension.height;

                if (aspectRatio > panelAspectRatio) {
                    return new Dimension(this.dimension.width, (int) (this.dimension.width / aspectRatio));
                } else {
                    return new Dimension((int) (this.dimension.height * aspectRatio), this.dimension.height);
                }
            }
        }
    }
}
