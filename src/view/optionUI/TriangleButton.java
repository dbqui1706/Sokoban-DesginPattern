package view.optionUI;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class TriangleButton extends JButton {
    private static final int SIZE = 20; // size of the button
    private static final Color BUTTON_COLOR = Color.WHITE; // color of the button
    private static final Color TRIANGLE_COLOR = Color.BLACK; // color of the triangle

    public TriangleButton() {
        super();
        setContentAreaFilled(false); // make the button transparent
        setPreferredSize(new Dimension(SIZE, SIZE)); // set the button size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // create the triangle shape
        int[] xPoints = {0, SIZE, 0};
        int[] yPoints = {0, SIZE / 2, SIZE};
        Shape triangle = new Polygon(xPoints, yPoints, xPoints.length);

        // set the color of the button
        g.setColor(BUTTON_COLOR);

        // fill the button with the button color
        g.fillRect(0, 0, getWidth(), getHeight());

        // set the color of the triangle
        g.setColor(TRIANGLE_COLOR);

        // create an AffineTransform to center and rotate the triangle
        AffineTransform transform = new AffineTransform();
        transform.translate(SIZE / 2, SIZE / 2);
        transform.rotate(Math.PI / 2);
        transform.translate(-SIZE / 2, -SIZE / 2);

        // create a new Graphics2D object with the transform applied
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setTransform(transform);

        // draw the triangle on the button
        g2d.fill(triangle);

        // dispose of the Graphics2D object
        g2d.dispose();
    }
}
