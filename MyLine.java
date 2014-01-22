import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Line2D;

/**
 * Contains the class for a specific line object. Inherits from the superclass
 * <code>MyShape</code>, and allows access to each of the superclass's constructors.
 * Also defines how to draw the line shape.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class MyLine extends MyShape {
    /*
     * Class constructor.
     */
    public MyLine( int x1Coord, int y1Coord, int x2Coord, int y2Coord,
                  Color colour1, Color colour2, float strokeWidth, float[] dashLength,
                  boolean isGradient, boolean isDashed ) {
        // calls the superclass's constructor
        super(x1Coord, y1Coord, x2Coord, y2Coord, colour1, colour2,
              strokeWidth, dashLength, isGradient, isDashed);
    } // end constructor
    
    /**
     * Draws a line based on the current colour and coordinates.
     */
    public void draw ( Graphics2D g2d ) {
        setupShape( g2d );
        g2d.draw( new Line2D.Double(getX1Coordinate(), getY1Coordinate(), getX2Coordinate(), getY2Coordinate()) );
    } // end draw
} // end class