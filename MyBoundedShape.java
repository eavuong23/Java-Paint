import java.awt.Color;

/**
 * Contains a more specific shape from its superclass <code>MyShape</code> such that
 * the shape has information regarding its width and height, and whether the shape
 * is filled with a colour. Since this is still a highly-generalized shape, its
 * <code>draw</code> method remains abstract, and thus the class is abstract.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public abstract class MyBoundedShape extends MyShape {
    private boolean isFilled; // a boolean field to determine whether the shape is filled in or not
    
    /*
     * Class constructor.
     */
    public MyBoundedShape( int x1Coord, int y1Coord, int x2Coord, int y2Coord,
                          Color colour1, Color colour2, float strokeWidth, float[] dashLength,
                          boolean isGradient, boolean isDashed, boolean filled ) {
        // calls the superclass's constructor with specific parameters
        super(x1Coord, y1Coord, x2Coord, y2Coord, colour1, colour2,
              strokeWidth, dashLength, isGradient, isDashed);
        
        setFilled( filled );
    } // end constructor
    
    /**
     * This accessor method returns whether the shape is filled or not.
     *
     * @return The boolean value of whether the shape is filled or not.
     */
    public boolean getFilled() {
        return isFilled;
    } // end getFilled
    
    /**
     * This mutator method sets a boolean value for whether the shape is filled or not.
     *
     * @param filled Replaces the current private boolean <code>isFilled</code> field with this.
     */
    public void setFilled( boolean filled ) {
        isFilled = filled;
    } // end setFilled
    
    /**
     * Calculates and returns the left-most x coordinate (the lowest x coordinate value).
     *
     * @return The lowest x coordinate value.
     */
    public int getUpperLeftX() {
        // calls the overloaded Math.min static method to determine the lowest x value
        return Math.min( getX1Coordinate(), getX2Coordinate() );
    } // end getUpperLeftX
    
    /**
     * Calculates and returns the upper-most y coordinate (the lowest value).
     *
     * @return The lowest y coordinate value.
     */
    public int getUpperLeftY() {
        // calls the overloaded Math.min static method to determine the lowest y value
        return Math.min( getY1Coordinate(), getY2Coordinate() );
    } // end getUpperLeftY
    
    /**
     * Returns the width of the shape, determined by the absolute difference
     * between the two x coordinate values.
     *
     * @return The width of the shape.
     */
    public int getWidth() {
        return Math.abs( getX1Coordinate() - getX2Coordinate() ); // absolute difference value
    } // end getWidth
    
    /**
     * Returns the height of the shape, determined by the absolute difference
     * between the two y coordinate values.
     *
     * @return The height of the shape.
     */
    public int getHeight() {
        return Math.abs( getY1Coordinate() - getY2Coordinate() ); // absolute difference value
    } // end getHeight
} // end class