import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

/**
 * Contains the class for an oval object. It inherits from the superclass
 * <code>MyBoundedShape</code>, but the only difference is the availability of a
 * completed <code>draw</code> method. The <code>draw</code> method draws an
 * oval based on the upper-left x, y starting coordinates, a height and width, and a
 * colour.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 *
 */
public class MyOval extends MyBoundedShape {
    /*
     * Class constructor.
     */
    public MyOval( int x1Coord, int y1Coord, int x2Coord, int y2Coord,
                  Color colour1, Color colour2, float strokeWidth, float[] dashLength,
                  boolean isGradient, boolean isDashed, boolean filled ) {
        super( x1Coord, y1Coord, x2Coord, y2Coord, colour1, colour2,
              strokeWidth, dashLength, isGradient, isDashed, filled );
    } // end constructor
    
    /**
     * Draws the oval from the current colour, the initial x and y starting coordinates,
     * the width, and the height (all gathered from the accessor methods).
     */
    public void draw ( Graphics2D g2d ) {
        setupShape( g2d );
        if ( getFilled() )
            g2d.fill( new Ellipse2D.Double(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
        else
            g2d.draw( new Ellipse2D.Double(getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
        
    } // end draw
} // end class