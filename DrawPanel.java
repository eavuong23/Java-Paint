import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains the class to handle all the drawing events that the user creates.
 * Also contains a <code>paintComponent</code> method that draws the shape.
 * There is also a function of removing and clearing shapes that were already
 * drawn.
 *
 * @author Edward Vuong
 * @version May 2, 2012
 */
public class DrawPanel extends JPanel {
    private LinkedList shapeObjects;
    private DynamicStack recycleBin; // allows for "redo" operations
    
    // Shape properties.
    private MyShape currentShapeObject;
    private Color currentShapeColor1;
    private Color currentShapeColor2;
    private boolean currentShapeFilled;
    
    private float strokeWidth;
    private float[] dashLength;
    private boolean isGradient;
    private boolean isDashed;
    
    private JLabel statusLabel;
    private ShapeTypes currentShapeType;
    
    enum ShapeTypes { LINE, OVAL, RECTANGLE, POLYGON }; // determines the shape to be drawn
    
    /**
     * Constructor: only has one parameter; all other fields are set to default values.
     *
     * @param label A JLabel reference that will be used for display in the statusbar.
     */
    public DrawPanel( JLabel label ) {
        super();
        statusLabel = label;
        shapeObjects = new LinkedList();
        recycleBin = new DynamicStack();
        
        // defaults to a black coloured line.
        currentShapeType = ShapeTypes.LINE;
        currentShapeColor1 = Color.BLACK;
        currentShapeColor2 = Color.BLACK;
        currentShapeFilled = false;
        strokeWidth = 1;
        dashLength = new float[] { 1f };
        isGradient = false;
        isDashed = false;
        
        setBackground( Color.WHITE );
        addMouseListener( new MouseHandler() );
        addMouseMotionListener( new MouseHandler() );
    } // end class constructor
    
    /**
     * Class constructor: sets initial values of the customizable components to the
     * main paint program. The handlers are also added to the mouse.
     *
     * @param label A JLabel reference that will be used for display in the statusbar.
     * @param currentShapeColor1 Miscellaneous.
     * @param currentShapeColor2 Miscellaneous.
     * @param currentShapeFilled Miscellaneous.
     * @param strokeWidth Miscellaneous.
     * @param dashLength Miscellaneous.
     * @param isGradient Miscellaneous.
     * @param isDashed Miscellaneous.
     */
    public DrawPanel(JLabel label, ShapeTypes currentShapeType,
                     Color currentShapeColor1, Color currentShapeColor2,
                     boolean currentShapeFilled, float strokeWidth, float[] dashLength,
                     boolean isGradient, boolean isDashed) {
        super();
        
        statusLabel = label;
        shapeObjects = new LinkedList();
        recycleBin = new DynamicStack();
        
        // defaults to a black coloured line.
        setShapeType( currentShapeType );
        setShapeColor1( currentShapeColor1 );
        setShapeColor2( currentShapeColor2 );
        setShapeFilled( currentShapeFilled );
        setStrokeWidth( strokeWidth ) ;
        setDashLength( dashLength );
        setGradient( isGradient );
        setDashed( isDashed );
        
        setBackground( Color.WHITE );
        addMouseListener( new MouseHandler() );
        addMouseMotionListener( new MouseHandler() );
    } // end class constructor
    
    /*
     * A mouse handler inner class that handles mouse clicks, releases, drags, and movements.
     */
    private class MouseHandler extends MouseAdapter {
        
        public void mousePressed( MouseEvent event ) {
            
            // only the left mouse button controls the drawing
            if ( event.getButton() == MouseEvent.BUTTON1 && currentShapeObject == null ) {
                // create a line
                if ( currentShapeType == ShapeTypes.LINE ) {
                    currentShapeObject = new MyLine( event.getX(), event.getY(),
                                                    event.getX(), event.getY(), currentShapeColor1, currentShapeColor2,
                                                    strokeWidth, dashLength, isGradient, isDashed );
                }
                // create an oval
                else if ( currentShapeType == ShapeTypes.OVAL ) {
                    currentShapeObject = new MyOval( event.getX(), event.getY(),
                                                    event.getX(), event.getY(), currentShapeColor1, currentShapeColor2,
                                                    strokeWidth, dashLength, isGradient, isDashed,currentShapeFilled );
                }
                // create a rectangle
                else if ( currentShapeType == ShapeTypes.RECTANGLE ) {
                    currentShapeObject = new MyRectangle( event.getX(),
                                                         event.getY(), event.getX(), event.getY(),
                                                         currentShapeColor1, currentShapeColor2, strokeWidth, dashLength,
                                                         isGradient, isDashed, currentShapeFilled );
                }
                // create a polygon
                else if ( currentShapeType == ShapeTypes.POLYGON ) {
                    currentShapeObject = new MyPolygon( event.getX(), event.getY(),
                                                       currentShapeColor1, currentShapeColor2, strokeWidth, dashLength,
                                                       isGradient, isDashed, currentShapeFilled, false );
                } // end if
            }
            
            repaint();
        } // end mousePressed
        
        /*
         * "Creates" the shape that the user was currently drawing by appending
         * it to the "shapesObjects" array.
         */
        public void mouseReleased( MouseEvent event ) {
            // only accepts the left mouse button release (or an exception may
            // occur if there are other releases)
            if (event.getButton() != MouseEvent.BUTTON1 ||
                currentShapeObject == null) {
                return;
            }
            
            // call this instead of setting coding what could be reused
            mouseDragged(event);
            
            if ( currentShapeType == ShapeTypes.POLYGON ) {
                boolean status = ((MyPolygon) currentShapeObject).addTempPoint( event.getX(), event.getY() );
                
                // if there are at least two points drawn, then the shape could possibly be completed
                if ( ((MyPolygon) currentShapeObject).getNumberPoints() > 2 ) {
                    // gets the first point and checks in reference to other points
                    int firstPoint[] = ((MyPolygon) currentShapeObject).getFirstPoint();
                    
                    // if the shape is completed (!status)
                    if ( !status || ((Math.abs(event.getX() - firstPoint[0]) < (5 + strokeWidth)) &&
                                     (Math.abs(event.getY() - firstPoint[1]) < (5 + strokeWidth)))) {
                        int coords[] = ((MyPolygon) currentShapeObject).getFirstPoint();
                        ((MyPolygon) currentShapeObject).addPoint( coords[0], coords[1] );
                        setShapeCompleted();
                        return;
                    } // end nested nested if
                } // end nested if
                
                if ( status ) {
                    // else draw anyways
                    ((MyPolygon) currentShapeObject).addPoint( event.getX(), event.getY() );
                } // end nested if
                
            }
            else {
                setShapeCompleted();
            } // end if
            
            repaint();
        } // end mouseReleased
        
        /*
         * Updates the status bar to show the new coordinates.
         */
        public void mouseMoved( MouseEvent event ) {
            statusLabel.setText( String.format( "(%d, %d)", event.getX(), event.getY() ) );
        } // end mouseMoved
        
        /*
         * Sets the new end coordinates for the current shape (if any), then
         * refreshes the JPanel to display the shape.
         */
        public void mouseDragged( MouseEvent event ) {
            if ( currentShapeObject != null ) {
                if ( currentShapeType == ShapeTypes.POLYGON ) {
                    // if polygon, temporarily set one point
                    ((MyPolygon) currentShapeObject).addTempPoint( event.getX(), event.getY() );
                }
                else {
                    currentShapeObject.setX2Coordinate( event.getX() );
                    currentShapeObject.setY2Coordinate( event.getY() );
                    mouseMoved(event); // calls the mouseMoved() for code reuse
                }
                repaint();
            }
        } // end mouseDragged
    } // end inner-class MouseHandler
    
    /**
     * Removes the last shape drawn and displays the new result.
     */
    public void clearLastShape() {
        // if there is at least one shape drawn, then undo
        if ( !shapeObjects.isEmpty() ) {
            recycleBin.push( shapeObjects.removeEnd() );
            repaint();
        }
    } // end method
    
    /**
     * Retrieves the last shape erased ("undone") and displays the
     * new result.
     */
    public void unclearLastShape() {
        // if there is at least one shape undone, then redo
        if ( !recycleBin.isEmpty() ) {
            shapeObjects.addEnd( recycleBin.pop() );
            repaint();
        }
    } // end method
    
    /**
     * Removes all shapes and displays the new result.
     */
    public void clearDrawing() {
        setShapeCompleted();
        shapeObjects.makeEmpty();
        repaint();
    } // end method
    
    /**
     * Returns the primary gradient colour.
     *
     * @return A Color object.
     */
    public Color getShapeColour1() {
        return currentShapeColor1;
    } // end accessor
    
    /**
     * Returns the secondary gradient colour.
     *
     * @return A Color object.
     */
    public Color getShapeColour2() {
        return currentShapeColor2;
    } // end accessor
    
    /**
     * Return the dashlength.
     *
     * @return The dashlength.
     */
    public float[] getDashLength() {
        return dashLength;
    } // end accessor
    
    /**
     * Return the strokewidth.
     *
     * @return The strokewidth.
     */
    public float getStrokeWidth() {
        return strokeWidth;
    } // end accessor
    
    /**
     * Returns whether the settings are to fill the shape.
     *
     * @return If shape is to be filled.
     */
    public boolean isCurrentShapeFilled() {
        return currentShapeFilled;
    } // end accessor
    
    /**
     * Returns true if gradient.
     *
     * @return Is gradient
     */
    public boolean isGradient() {
        return isGradient;
    } // end accessor
    
    /**
     * Returns true if dashed.
     *
     * @return Is dashed.
     */
    public boolean isDashed() {
        return isDashed;
    } // end accessor
    
    /**
     * Returns the current shape type.
     *
     * @return The current shape type.
     */
    public ShapeTypes getCurrentShapeType() {
        return currentShapeType;
    } // end accessor
    
    /**
     * Mutator method for the current shape type - changes current value based
     * on parameter.
     */
    public void setShapeType( ShapeTypes newShapeType ) {
        currentShapeType = newShapeType;
    } // end accessor
    
    /**
     * Mutator method for the first current shape colour - changes current value
     * based on parameter.
     */
    public void setShapeColor1( Color newShapeColor1 ) {
        currentShapeColor1 = newShapeColor1;
    } // end mutator
    
    /**
     * Mutator method for the second current shape colour - changes current value
     * based on parameter.
     */
    public void setShapeColor2( Color newShapeColor2 ) {
        currentShapeColor2 = newShapeColor2;
    } // end mutator
    
    /**
     * Mutator to set the dash length. Requires a float-type array.
     */
    public void setDashLength( float[] dashLength ) {
        this.dashLength = dashLength;
    } // end mutator
    
    /**
     * Mutator to set the stroke width.
     */
    public void setStrokeWidth( float strokeWidth ) {
        this.strokeWidth = strokeWidth;
    } // end mutator
    
    /**
     * Mutator to set whether a gradient is active.
     */
    public void setGradient( boolean isGradient ) {
        this.isGradient = isGradient;
    } // end mutator
    
    /**
     * Mutator to set whether a dashed line is active.
     */
    public void setDashed( boolean isDashed ) {
        this.isDashed = isDashed;
    } // end mutator
    
    /**
     * Mutator method for the Boolean value of whether the shape is
     * filled or not - changes current value based on parameter.
     */
    public void setShapeFilled( boolean newShapeFilled ) {
        currentShapeFilled = newShapeFilled;
    } // end mutator
    
    /**
     * Sets the current shape (if any) to completed, which disallows any more
     * operations on it.
     */
    public void setShapeCompleted() {
        if ( currentShapeObject != null) {
            currentShapeObject.setCompleted( true );
            shapeObjects.addEnd( currentShapeObject );
            currentShapeObject = null;
            recycleBin.makeEmpty();
            repaint();
        } // end if
    } // end method
    
    /**
     * Selects each myShape Object from the array and calls its .draw() method.
     * Also draws a shape that the user is currently drawing (if any).
     */
    public void paintComponent( Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2d = ( Graphics2D ) g;
        
        ListNode temp = shapeObjects.peek();
        // Repeatedly gets the next reference from the first node ("temp") until
        // no more shapes are found.
        if ( !shapeObjects.isEmpty() ) {
            while (temp != null) {
                ((MyShape) temp.getValue()).draw(g2d);
                temp = temp.getNext();
            }
        }
        
        if (currentShapeObject != null) {
            currentShapeObject.draw(g2d);
        } // end if
        
    } // end method
} // end class