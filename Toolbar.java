import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * Contains the visual user settings to the paint program. The user can select
 * different shapes and colours to draw, and set whether the shape is filled or
 * not. There is also an option to undo, redo, or clear the drawing.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class Toolbar extends JPanel {
    // JPanels
    private DrawPanel drawPanel;
    private JPanel topPortion;
    private JPanel bottomPortion;
    
    // Buttons
    private JButton undoButton;
    private JButton redoButton;
    private JButton clearButton;
    
    // Shape type and Shape Fill
    private JComboBox shapesComboBox;
    private JCheckBox filledCheckBox;
    
    // Stroke Width
    private JLabel strokeWidthLabel;
    private JTextField strokeWidthField;
    
    // Stroke Style
    private JCheckBox styleCheckBox;
    private JLabel styleLabel;
    private JTextField styleTextField;
    
    // Gradient Options
    private JCheckBox gradientCheckBox;
    private JButton colour1Button;
    private JButton colour2Button;
    
    // Gradient Options
    
    // A master grouping of all JComponents to be placed in their respective
    // position on a JPanel (i.e. topPortion/bottomPortion)
    private final JComponent topComponents[];
    private final JComponent bottomComponents[]; // NOPMD by edward on 31/05/12 7:37 PM
    
    // The String representation of the current shape to be drawn
    private final String shapeArrayKey[] = { "Line", "Oval", "Rectangle", "Polygon" };
    
    // The enum value of the current shape to be drawn
    private final DrawPanel.ShapeTypes shapeArrayValue[] = {
        DrawPanel.ShapeTypes.LINE, DrawPanel.ShapeTypes.OVAL,
        DrawPanel.ShapeTypes.RECTANGLE, DrawPanel.ShapeTypes.POLYGON };
    
    /**
     *
     * Class Constructor. Creates all necessary widgets, and implements all
     * handlers.
     */
    public Toolbar( DrawPanel drawPanel ) {
        setLayout( new BorderLayout() );
        
        // create a JPanel
        this.drawPanel = drawPanel;
        topPortion = new JPanel();
        bottomPortion = new JPanel();
        
        // set BoxLayout for each JPanel
        topPortion.setLayout( new BoxLayout( topPortion, BoxLayout.X_AXIS) );
        bottomPortion.setLayout( new BoxLayout( bottomPortion, BoxLayout.X_AXIS) );
        
        // create buttons and other GUI components to add to the toolbar
        undoButton = new JButton( "Undo" );
        redoButton = new JButton( "Redo" );
        clearButton = new JButton( "Clear" );
        shapesComboBox = new JComboBox( shapeArrayKey );
        filledCheckBox = new JCheckBox( "Filled" );
        // second row
        strokeWidthLabel = new JLabel( "Stroke width:" );
        strokeWidthField = new JTextField( "1", 10 );
        styleCheckBox = new JCheckBox( "Dashed Line" );
        styleLabel = new JLabel( "Stroke Dash Length:");
        styleTextField = new JTextField( "1", 10 );
        gradientCheckBox = new JCheckBox( "Gradient" );
        colour1Button = new JButton( "Colour 1" );
        colour2Button = new JButton( "Colour 2" );
        
        // set some initial values
        undoButton.setToolTipText( "Undo" );
        redoButton.setToolTipText( "Redo" );
        clearButton.setToolTipText( "Clear Drawing" );
        shapesComboBox.setToolTipText( "Shape Type" );
        filledCheckBox.setToolTipText( "Allow Filled Shapes" );
        strokeWidthField.setToolTipText( "Set Stroke Width" );
        styleCheckBox.setToolTipText( "Dashed or Solid Line" );
        styleTextField.setToolTipText( "Set Dashed Line Length" );
        gradientCheckBox.setToolTipText( "Allow Gradients");
        colour1Button.setToolTipText( "Primary Colour" );
        colour2Button.setToolTipText( "Secondary Colour" );
        
        // set the placement order of each component
        topComponents = new JComponent[] { undoButton,
            redoButton, clearButton, shapesComboBox,
            filledCheckBox, strokeWidthLabel, strokeWidthField, };
        bottomComponents = new JComponent[] { styleCheckBox, styleLabel,
            styleTextField, gradientCheckBox, colour1Button, colour2Button };
        
        // add to the top of the toolbar, each component with a mouse handlers
        for ( JComponent component : topComponents ) {
            addComponent( topPortion, component );
            topPortion.add( Box.createRigidArea( new Dimension(5, 0) ) );
        } // end for
        
        // add to the bottom of the toolbar
        for ( JComponent component : bottomComponents ) {
            addComponent( bottomPortion, component );
            bottomPortion.add( Box.createRigidArea( new Dimension(5, 0) ) );
        } // end for
        
        addMouseListener( new ToolbarMouseHandler() );
        
        // add the portions to the toolbar
        add( topPortion, BorderLayout.NORTH );
        add( bottomPortion, BorderLayout.SOUTH );
        
        setFocusPanel( drawPanel );
    } // end constructor
    
    /*
     * A private inner class that performs an undo or clear operation, depending
     * on which button was pressed.
     */
    private class ButtonHandler implements ActionListener {
        public void actionPerformed( ActionEvent event ) {
            // undo
            if ( event.getSource() == undoButton ) {
                drawPanel.clearLastShape();
            }
            // redo
            else if ( event.getSource() == redoButton ) {
                drawPanel.unclearLastShape();
            }
            // clear
            else if ( event.getSource() == clearButton ) {
                drawPanel.clearDrawing();
            }
            // set gradient colour 1 (main colour)
            else if ( event.getSource() == colour1Button ) {
                Color temp = JColorChooser.showDialog(null,
                                                      "Choose Your Primary Colour",
                                                      drawPanel.getShapeColour1());
                
                if ( temp != null ) {
                    drawPanel.setShapeColor1( temp );
                    // shows the colour on the button, and the inverted colour as the text
                    colour1Button.setBackground( temp );
                    colour1Button.setForeground( invertColour( temp ) );
                }
            }
            // set gradient colour 2 (secondary colour)
            else if ( event.getSource() == colour2Button ) {
                Color temp = JColorChooser.showDialog(null,
                                                      "Choose Your Secondary Colour",
                                                      drawPanel.getShapeColour2());
                
                if ( temp != null ) {
                    drawPanel.setShapeColor2( temp );
                    // shows the colour on the button, and the inverted colour as the text
                    colour2Button.setBackground( temp );
                    colour2Button.setForeground( invertColour( temp ) );
                }
            }
            
        } // end actionPerformed
    } // end inner-class ButtonHandler
    
    /*
     * An inner class that allows selection of different combo box components
     * (e.g. set colour or shape type).
     */
    private class ComboBoxHandler implements ItemListener {
        public void itemStateChanged( ItemEvent event ) {
            // sets the shape based on what the user selected (if any)
            if ( event.getSource() == shapesComboBox ) {
                drawPanel.setShapeType( shapeArrayValue[shapesComboBox.getSelectedIndex()] );
            } // end if
        }// end itemStateChanged
    } // end inner-class ComboBoxHandler
    
    /*
     * Sets whether or not the next shape drawn will be filled.
     */
    private class CheckBoxHandler implements ItemListener {
        public void itemStateChanged( ItemEvent event ) {
            if ( event.getSource() == filledCheckBox ) {
                // set whether the shape is to be filled or not
                drawPanel.setShapeFilled( filledCheckBox.isSelected() );
            }
            else if ( event.getSource() == styleCheckBox ) {
                // sets dashed lines
                drawPanel.setDashed( styleCheckBox.isSelected() );
            } else if ( event.getSource() == gradientCheckBox ) {
                drawPanel.setGradient( gradientCheckBox.isSelected() );
            } // end if
        } // end itemStateChanged
    } // end inner-class CheckBoxHandler
    
    /*
     * Simply checks if any part of the JPanel (itself) was clicked, which will automatically
     * cancel all user drawing activity.
     */
    public class ToolbarMouseHandler extends MouseAdapter {
        public void mousePressed( MouseEvent event ) {
            drawPanel.setShapeCompleted();
        } // end method
    } // end inner-class ToolbarMouseHandler
    
    /*
     * An inner class that is used to set shapes when clicked, and to hide the
     * textfield.
     */
    private class WidgetMouseHandler extends MouseAdapter {
        public void mousePressed( MouseEvent event ) {
            drawPanel.setShapeCompleted();
        } // end method
        
        public void mouseExited( MouseEvent event ) {
            strokeWidthField.setEditable( false );
            styleTextField.setEditable( false );
            validateTextField( strokeWidthField );
            validateTextField( styleTextField );
        } // end method
        
        /*
         * Sets the textfield to editable.
         */
        public void mouseEntered( MouseEvent event ) {
            if ( event.getSource() == strokeWidthField ||
                event.getSource() == styleTextField ) {
                // highlights all text and allows editing
                ((JTextField) event.getSource()).requestFocus();
                ((JTextField) event.getSource()).setEditable( true );
                ((JTextField) event.getSource()).setSelectionStart( 0 );
                ((JTextField) event.getSource()).setSelectionEnd(
                                                                 ((JTextField) event.getSource()).getText().length());
            }
        } // end method
    } // end inner-class WidgetMouseHandler
    
    /*
     * Handles text inputs.
     */
    private class TextFieldHandler implements ActionListener {
        public void actionPerformed( ActionEvent event ) {
            if ( event.getSource() == strokeWidthField ||
                event.getSource() == styleTextField) {
                validateTextField( (JTextField) event.getSource() );
            } // end if
        } // end method
    } // end inner-class TextFieldHandler
    
    /**
     * Sets component values based on a given DrawPanel object.
     *
     * @param newDrawPanel The new DrawPanel object.
     */
    public void setFocusPanel( DrawPanel newDrawPanel ) {
        if ( newDrawPanel == null )
            return;
        drawPanel = newDrawPanel;
        shapesComboBox.setSelectedItem( toTitle( drawPanel.getCurrentShapeType().toString() ) );
        filledCheckBox.setSelected( drawPanel.isCurrentShapeFilled() );
        strokeWidthField.setText( String.format( "%f", drawPanel.getStrokeWidth() ) );
        styleCheckBox.setSelected( drawPanel.isDashed() );
        styleTextField.setText( String.format( "%f", drawPanel.getDashLength()[0] ) );
        gradientCheckBox.setSelected( drawPanel.isGradient() );
        colour1Button.setBackground( drawPanel.getShapeColour1() );
        colour1Button.setForeground( invertColour( colour1Button.getBackground() ) );
        colour2Button.setBackground( drawPanel.getShapeColour2() );
        colour2Button.setForeground( invertColour( colour2Button.getBackground() ) );
    } // end method
    
    /**
     * Returns a String in title format ( Title Format ).
     *
     * @param s String.
     * @return New String in titled format.
     */
    private String toTitle( String s ) {
        if ( s != null ) {
            return s.charAt( 0 ) + s.substring( 1 ).toLowerCase();
        }
        return s;
    }
    
    /**
     * Inverts a given Color object and returns it.
     *
     * @param original The original Color object.
     * @return The inverted Color object.
     */
    private Color invertColour( Color original ) {
        // the max component (255) subtract the current is the inverted colour
        return ( new Color(255 - original.getRed(), 255 - original.getGreen(),
                           255 - original.getBlue()) );
    } // end method
    
    /**
     * Validates by removing all non-digit values from a given textfield.
     *
     * @param textfield A JTextField object to be edited on.
     */
    private void validateTextField( JTextField textfield ) {
        String contents = textfield.getText();
        String newContents = "";
        
        // if a not valid floating-point number then destroy all offending
        // characters and assign value to the drawPanel
        if ( !contents.matches( "[0-9]*\\.?[0-9]*") ) {
            for ( int i = 0; i < contents.length(); i++ ) {
                if ( Character.isDigit( contents.charAt( i )) )
                    newContents += contents.charAt( i );
            } // end for
        } else {
            newContents = contents;
        } // end if
        
        // if not a valid value, then force a change
        if ( (newContents.length() == 0) || (Float.parseFloat( newContents ) <= 0) ) {
            newContents = "1";
        } // end if
        
        textfield.setText( newContents ); // set new text
        
        // configure both textfields
        float dashLength[] = new float[] { Float.parseFloat( styleTextField.getText() ) };
        drawPanel.setDashLength( dashLength );
        drawPanel.setStrokeWidth( Float.parseFloat( strokeWidthField.getText() ) );
    } // end method
    
    /**
     * Sets some component preferences for a given component, then adding it to
     * the parent component (JPanel). Also sets the component's respective
     * handler (if any).
     *
     * @param parent A JPanel that the component will be placed in.
     * @param component The component to place in the parent JPanel object.
     */
    private void addComponent( JPanel parent, JComponent component ) {
        // add the mouse listeners and sets the component to a fixed size
        component.addMouseListener( new WidgetMouseHandler() );
        component.addMouseMotionListener( new WidgetMouseHandler() );
        component.setMinimumSize( component.getPreferredSize() );
        component.setMaximumSize( component.getPreferredSize() );
        
        Class<? extends JComponent> componentClass = component.getClass();
        
        // adds a border to the given component; skip JLabels for aesthetics reasons
        if ( !componentClass.equals( JLabel.class )) {
            Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
            component.setBorder(border);
        }
        // shows border to JCheckBoxes and sets handlers
        if ( componentClass.equals( JCheckBox.class ) ) {
            ((JCheckBox) component).setBorderPainted( true );
            ((JCheckBox) component).addItemListener( new CheckBoxHandler() );
        }
        // set some more initial values (disable textfields) and sets handlers
        else if ( componentClass.equals( JTextField.class ) ) {
            ((JTextField) component).setEditable( false );
            ((JTextField) component).addActionListener( new TextFieldHandler() );
        }
        // set handlers for buttons
        else if ( componentClass.equals( JButton.class ) ) {
            ((JButton) component).addActionListener( new ButtonHandler() );
        }
        // set handlers for comboboxes
        else if ( componentClass.equals( JComboBox.class ) ) {
            ((JComboBox) component).addItemListener( new ComboBoxHandler() );
        }
        parent.add( component );
    } // end method
    
    /*
     * Removes buttons so the toolbar has only relevant components.
     */
    public void removeButtons() {
        topPortion.remove( undoButton );
        topPortion.remove( redoButton );
        topPortion.remove( clearButton );
        validate();
    } // end method
} // end class
