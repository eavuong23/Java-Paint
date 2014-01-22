import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * This is an abstract class that contains information of a generic shape. It
 * should be inherited to provide the fields and methods to draw lines, ovals,
 * rectangles, and the similar shapes.
 * An abstract method <code>draw</code> will be inherited and defined to
 * draw the subclass's shape.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public abstract class MyShape {
    private int x1Coordinate; // the first x coordinate
    private int y1Coordinate; // the first y coordinate
    private int x2Coordinate; // the second x coordinate
    private int y2Coordinate; // the second y coordinate
    private Color myColour1;   // the first colour
    private Color myColour2;   // the second colour
    
    // Graphics2D-related attributes
    private float strokeWidth;
    private float[] dashLength;
    private boolean isGradient;
    private boolean isDashed;
    protected boolean isCompleted;
    
    /**
     * Class constructor. Calls its own methods to set up.
     *
     * @param x1Coord X1 coordinate.
     * @param y1Coord Y1 coordinate
     * @param x2Coord X2 coordinate.
     * @param y2Coord Y2 coordinate.
     * @param colour1 Primary colour.
     * @param colour2 Secondary colour.
     * @param strokeWidth Stroke width.
     * @param dashLength Dash length.
     * @param isGradient Whether the shape is gradient.
     * @param isDashed Whether the shape is dashed.
     */
    public MyShape( int x1Coord, int y1Coord, int x2Coord, int y2Coord,
                   Color colour1, Color colour2, float strokeWidth, float[] dashLength,
                   boolean isGradient, boolean isDashed ) {
        // calls the mutator methods
        setX1Coordinate( x1Coord );
        setX2Coordinate( x2Coord );
        setY1Coordinate( y1Coord );
        setY2Coordinate( y2Coord );
        setColour1( colour1 );
        setColour2( colour2 );
        setStrokeWidth( strokeWidth );
        setDashLength( dashLength );
        setGradient(isGradient);
        setDashed(isDashed);
        isCompleted = true;
    } // end constructor
    
    // **Accessor methods**
    
    /**
     *  This accessor method returns the first x coordinate.
     *
     * @return The x1 coordinate.
     */
    public int getX1Coordinate() {
        return x1Coordinate;
    } // end getX1Coordinate
    
    /**
     *  This accessor method returns the first y coordinate.
     *
     * @return The y1 coordinate.
     */
    public int getY1Coordinate() {
        return y1Coordinate;
    } // end getY1Coordinate
    
    /**
     *  This accessor method returns the second x coordinate.
     *
     * @return The x2 coordinate.
     */
    public int getX2Coordinate() {
        return x2Coordinate;
    } // end getX2Coordinate
    
    /**
     *  This accessor method returns the second y coordinate.
     *
     * @return The y2 coordinate.
     */
    public int getY2Coordinate() {
        return y2Coordinate;
    } // end getY2Coordinate
    
    /**
     * This accessor method returns the primary colour.
     *
     * @return The colour, as a {@link Color} object.
     */
    public Color getColour1() {
        return myColour1;
    } // end getColour1
    
    /**
     * This accessor method returns the secondary colour.
     *
     * @return The colour, as a {@link Color} object.
     */
    public Color getColour2() {
        return myColour2;
    } // end getColour2
    
    /**
     * This accessor method whether the shape is completed.
     *
     * @return A boolean true or false.
     */
    public boolean getCompleted() {
        return isCompleted;
    } // end getCompleted
    
    /**
     * Gets the stroke width.
     *
     * @return The stroke width.
     */
    public float getStrokeWidth() {
        return strokeWidth;
    } // end method
    
    /**
     * Gets the dashlength.
     *
     * @return The dashlength.
     */
    public float[] getDashLength() {
        return dashLength;
    } // end method
    
    /**
     * Returns whether the shape is to be in gradient.
     *
     * @return Whether the shape is to be in gradient.
     */
    public boolean isGradient() {
        return isGradient;
    } // end method
    
    /**
     * Returns whether the shape is using dashed lines.
     *
     * @return Whether the shape is using dashed lines.
     */
    public boolean isDashed() {
        return isDashed;
    } // end method
    
    // **End Accessors**
    
    // **Mutator methods**
    
    /**
     * Validates and sets the x1 coordinate (greater than zero).
     *
     * @param x1Coord The new x1 coordinate.
     */
    public void setX1Coordinate( int x1Coord ) {
        if ( x1Coord >= 0 )
            x1Coordinate = x1Coord;
        else
            x1Coordinate = 0; // default 0 otherwise
    } // end setX1Coordinate
    
    /**
     * Validates and sets the y1 coordinate (greater than zero).
     *
     * @param y1Coord The new y1 coordinate.
     */
    public void setY1Coordinate( int y1Coord ) {
        if ( y1Coord >= 0 )
            y1Coordinate = y1Coord;
        else
            y1Coordinate = 0; // default 0 otherwise
    } // end setY1Coordinate
    
    /**
     * Validates and sets the x1 coordinate (greater than zero).
     *
     * @param x2Coord The new x2 coordinate.
     */
    public void setX2Coordinate( int x2Coord ) {
        if ( x2Coord >= 0 )
            x2Coordinate = x2Coord;
        else
            x2Coordinate = 0; // default 0 otherwise
    } // end setX2Coordinate
    
    /**
     * Validates and sets the y2 coordinate (greater than zero).
     *
     * @param y2Coord The new y2 coordinate.
     */
    public void setY2Coordinate( int y2Coord ) {
        if ( y2Coord >= 0 )
            y2Coordinate = y2Coord;
        else
            y2Coordinate = 0; // default 0 otherwise
    } // end setY2Coordinate
    
    /**
     * Validates and sets the primary colour, from the Color object parameter.
     *
     * @param colour The new Color object.
     */
    public void setColour1( Color colour ) {
        if ( colour != null )
            myColour1 = colour;
        else
            myColour1 = Color.BLACK;
    } // end setColour1
    
    /**
     * Validates and sets the secondary colour, from the Color object parameter.
     *
     * @param colour The new Color object.
     */
    public void setColour2( Color colour ) {
        if ( colour != null )
            myColour2 = colour;
        else
            myColour2 = Color.BLACK;
    } // end setColour2
    
    /**
     * Sets the entire shape to a status of "completed".
     *
     * @param isCompleted Whether the shape is done.
     */
    public void setCompleted( boolean isCompleted ) {
        this.isCompleted = isCompleted;
    } // end method
    
    /**
     * Sets the strokelength.
     *
     * @param strokeWidth Value of the strokelength.
     */
    public void setStrokeWidth( float strokeWidth ) {
        if ( strokeWidth > 0 )
            this.strokeWidth = strokeWidth;
        else
            this.strokeWidth = 1;
    } // end method
    
    /**
     * Sets the dashlength.
     *
     * @param dashLength Value of the dashlength.
     */
    public void setDashLength(float[] dashLength) {
        if ( dashLength != null && dashLength[0] > 0 )
            this.dashLength = dashLength;
        else
            this.dashLength = new float[] { 1 };
    } // end method
    
    /**
     * Sets whether the shape is gradient or not.
     *
     * @param isGradient Boolean whether the shape is a gradient.
     */
    public void setGradient(boolean isGradient) {
        this.isGradient = isGradient;
    } // end method
    
    /**
     * Sets whether the shape is dashed or not.
     *
     * @param isDashed Boolean whether the shape is a dashed.
     */
    public void setDashed(boolean isDashed) {
        this.isDashed = isDashed;
    } // end method
    
    // **End Mutators**
    
    /**
     * Sets up a shape based on certain features.
     *
     * @param g2d {@link Graphics2D}
     */
    protected void setupShape( Graphics2D g2d ) {
        // set gradient (if not in gradient mode, then set all colours the same
        if ( isGradient() )
            g2d.setPaint(new GradientPaint(getX1Coordinate(),
                                           getY1Coordinate(), getColour1(), getX2Coordinate(),
                                           getY2Coordinate(), getColour2(), true));
        else
            g2d.setPaint(new GradientPaint(getX1Coordinate(),
                                           getY1Coordinate(), getColour1(), getX2Coordinate(),
                                           getY2Coordinate(), getColour1(), true));
        
        // set dashed
        if ( isDashed() )
            g2d.setStroke(new BasicStroke(getStrokeWidth(),
                                          BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 1f,
                                          getDashLength(), 0));
        else
            g2d.setStroke(new BasicStroke(getStrokeWidth(),
                                          BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
    } // end method
    
    /**
     * Will be inherited in order to draw a specific shape.
     *
     * @param g The {@link java.awt.Graphics} class.
     */
    public abstract void draw( Graphics2D g2d );
    
} // end class