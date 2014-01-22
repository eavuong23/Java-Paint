import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

/**
 * Contains the class for a polygon object.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class MyPolygon extends MyBoundedShape {
    private int xCoords[];
    private int yCoords[];
    private int numberPoints;
    
    /*
     * Class constructor that takes in one set of points initially.
     */
    public MyPolygon( int xCoord, int yCoord, Color colour1, Color colour2,
                     float strokeWidth, float[] dashLength, boolean isGradient,
                     boolean isDashed, boolean filled, boolean isCompleted ) {
        
        super(xCoord, yCoord, xCoord, yCoord, colour1, colour2, strokeWidth,
              dashLength, isGradient, isDashed, filled);
        setCompleted(isCompleted);
        
        // initialize the array and propagate the array with the starting point
        xCoords = new int[10000];
        yCoords = new int[10000];
        Arrays.fill( this.xCoords, xCoord );
        Arrays.fill( this.yCoords, yCoord );
        
        this.isCompleted = isCompleted;
        this.numberPoints = 1;
    } // end constructor
    
    /**
     * Sets and save a pair of coordinates to the polygon.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public void addPoint( int x, int y ) {
        if ( addTempPoint( x, y ) ) {
            numberPoints++;
        }
        else if ( !isCompleted ) {
            setCompleted( true );
        }
    } // end method
    
    /**
     * Temporarily sets and save a pair of coordinates to the polygon.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @return Returns true if the shape is already completed, false otherwise.
     */
    public boolean addTempPoint( int x, int y ) {
        // Does not allow to draw if completed.
        if ( (isCompleted)  || (numberPoints >= xCoords.length - 2))
            return false;
        
        // set a temporary point (the "numberPoints" counter does not increase)
        xCoords[numberPoints] = x;
        yCoords[numberPoints] = y;
        setX2Coordinate( xCoords[numberPoints] );
        setY2Coordinate( yCoords[numberPoints] );
        return true;
    } // end method
    
    /**
     * Returns the first set of points.
     *
     * @return An integer array.
     */
    public int[] getFirstPoint() {
        return new int[] {xCoords[0], yCoords[0]};
    } // end method
    
    /**
     * Return The number of points.
     * @return The number of points.
     */
    public int getNumberPoints() {
        return numberPoints;
    } // end method
    
    /**
     * An overrided method of "MyShapes". Sets the specified shape to the
     * parameter, then, for memory-efficiency, creates a new array with a
     * smaller size than the original (i.e. xCoords, yCoords), and fills them
     * with only the useful points. This new array will be used instead.
     */
    public void setCompleted( boolean isCompleted ) {
        this.isCompleted = isCompleted;
        if ( isCompleted ) {
            int tempX[] = new int[numberPoints];
            int tempY[] = new int[numberPoints];
            
            // minimizes the original array by copying its data to a smaller array
            tempX = Arrays.copyOfRange(xCoords, 0, numberPoints);
            tempY = Arrays.copyOfRange(yCoords, 0, numberPoints);
            
            xCoords = tempX;
            yCoords = tempY;
            
            // for gradient aesthetics (direction of gradient line)
            if ( numberPoints <= 3 ) {
                setX2Coordinate( xCoords[numberPoints - 1] );
                setY2Coordinate( yCoords[numberPoints - 1] );
            }
            else {
                setX2Coordinate( xCoords[numberPoints - 2] );
                setY2Coordinate( yCoords[numberPoints - 2] );
            }
            
        } // end if
    } // end method
    
    /**
     * Draws the oval from the current colour, the initial x and y starting coordinates,
     * the width, and the height (all gathered from the accessor methods).
     */
    public void draw ( Graphics2D g2d ) {
        setupShape( g2d );
        
        // Create a custom polygon object by drawing each individual line of a
        // polygon (using the xCoords, yCoords array)
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                                              numberPoints);
        // draws each component in the polygon
        polygon.moveTo(xCoords[0], yCoords[0]);
        for ( int i = 1; i < numberPoints; i++ ) {
            polygon.lineTo( xCoords[i], yCoords[i]);
        }
        
        // if not completed, just draw the temporary line
        if ( !isCompleted ) {
            polygon.lineTo( xCoords[numberPoints], yCoords[numberPoints] );
        }
        else {
            // else if it is filled, make a filled polygon and close the edges
            if ( getFilled() )
                g2d.fill(polygon);
            polygon.closePath();
        } // end if
        
        g2d.draw( polygon );
    } // end draw
} // end class