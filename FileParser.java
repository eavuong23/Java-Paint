import java.util.Formatter;
import java.util.Scanner;
import java.awt.Color;
import java.io.*;

/**
 * Reads or writes to a file (settings.ini).
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class FileParser {
    private DrawPanel drawPanel;
    private boolean read;
    
    /**
     * Takes a drawPanel object to analyze (if necessary).
     *
     * @param drawPanel A DrawPanel object.
     * @param read Equals true for reading, false for writing.
     */
    public FileParser( DrawPanel drawPanel, boolean read ) {
        this.drawPanel = drawPanel;
        setMode( read );
    } // end constructor
    
    public void setMode( boolean read ) {
        this.read = read;
    } // end method
    
    /**
     * Either reads or writes data from the file, based on the variable "read".
     */
    public void parse() {
        if ( read ) {
            try {
                Scanner fileIn = new Scanner( new File( "settings.ini" ) );
                // get all lines within the file
                while ( fileIn.hasNext() ) {
                    String line = fileIn.nextLine().toLowerCase();
                    // get all tokens from a line
                    Scanner tokenizer = new Scanner( line );
                    
                    String data = tokenizer.next();
                    
                    // string compare
                    if ( data.equals( "type" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data.equals( "line" ) )
                            drawPanel.setShapeType( DrawPanel.ShapeTypes.LINE );
                        else if ( data.equals( "oval" ) )
                            drawPanel.setShapeType( DrawPanel.ShapeTypes.OVAL );
                        else if ( data.equals( "rectangle" ) )
                            drawPanel.setShapeType( DrawPanel.ShapeTypes.RECTANGLE );
                        else if ( data.equals( "polygon" ) )
                            drawPanel.setShapeType( DrawPanel.ShapeTypes.POLYGON );
                    }
                    // if filled
                    else if ( data.equals( "filled") ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        // this regex checks for the string "true" or "false"
                        if ( data.matches( "true|false" ) ) {
                            drawPanel.setShapeFilled( Boolean.parseBoolean( data ) );
                        }
                    }
                    // if stroke width
                    else if ( data.equals( "strokewidth" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        // this regex checks for a valid floating-point value
                        if ( data.matches( "[0-9]*\\.?[0-9]*") ) {
                            drawPanel.setStrokeWidth( Float.parseFloat( data ) );
                        }
                    }
                    // if dashed lines
                    else if ( data.equals( "dashed" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        if ( data.matches( "true|false" ) ) {
                            drawPanel.setDashed( Boolean.parseBoolean( data ) );
                        }
                    }
                    // if dashed length
                    else if ( data.equals( "dashlength" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        if ( data.matches( "[0-9]*\\.?[0-9]*") ) {
                            drawPanel.setDashLength( new float[] { Float.parseFloat( data ) } );
                        }
                    }
                    // if gradient
                    else if ( data.equals( "gradient" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        if ( data.matches( "true|false" ) ) {
                            drawPanel.setGradient( Boolean.parseBoolean( data ) );
                        }
                    }
                    // if first colour
                    else if ( data.equals( "colour1" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        // checking all three colour components (RGB), this
                        // regex checks for a valid expression (e.g.
                        // RRR,GGG,BBB)
                        if ( data.matches("([0-9]{1,3}\\,){2}[0-9]{1,3}") ) {
                            Scanner colourScanner = new Scanner( data ).useDelimiter( "\\," );
                            int r = Integer.parseInt( colourScanner.next() );
                            int g = Integer.parseInt( colourScanner.next() );
                            int b = Integer.parseInt( colourScanner.next() );
                            drawPanel.setShapeColor1( new Color( r, g, b ) );
                        }
                    }
                    // if second colour
                    else if ( data.equals( "colour2" ) ) {
                        data = tokenizer.next().toLowerCase();
                        if ( data == null ) continue;
                        
                        // checking all three colour components (RGB)
                        if ( data.matches("([0-9]{1,3}\\,){2}[0-9]{1,3}") ) {
                            Scanner colourScanner = new Scanner( data ).useDelimiter( "\\," );
                            int r = Integer.parseInt( colourScanner.next() );
                            int g = Integer.parseInt( colourScanner.next() );
                            int b = Integer.parseInt( colourScanner.next() );
                            drawPanel.setShapeColor2( new Color( r, g, b ) );
                        }
                    } // end if
                } // end while
                
            } catch ( IOException exception ) {
                // do nothing (if file does not exist, then use default settings)
            } // end try block
        }
        // else write to a file
        else {
            try {
                Formatter fileOut = new Formatter( new File( "settings.ini" ) );
                fileOut.format( "Type %s\n", drawPanel.getCurrentShapeType() );
                fileOut.format( "Filled %b\n", drawPanel.isCurrentShapeFilled() );
                fileOut.format( "StrokeWidth %f\n", drawPanel.getStrokeWidth() );
                fileOut.format( "Dashed %b\n", drawPanel.isDashed() );
                fileOut.format( "DashLength %f\n", drawPanel.getDashLength()[0] );
                fileOut.format( "Gradient %b\n", drawPanel.isGradient() );
                fileOut.format( "Colour1 %s\n", ColorToString( drawPanel.getShapeColour1() ) );
                fileOut.format( "Colour2 %s\n", ColorToString( drawPanel.getShapeColour2() ) );
                fileOut.close();
            } catch ( IOException exception) {
                System.out.println( "File cannot be created. Try this program in another directory." );
            } // end try block
            
        }// end if
    } // end method
    
    /**
     * Returns a String representation of a Color object (that can be read by this class).
     *
     * @param colour The Color.
     * @return A String of the Color.
     */
    private String ColorToString( Color colour ) {
        return "" + colour.getRed() + "," + colour.getGreen() + "," + colour.getBlue();
    } // end method
} // end class
