import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Contains a window with the paint preferences. Saves any changes and every new
 * opening window will have these saved changes initally.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class PreferencesWindow extends JFrame {
    private DrawPanel drawPanel;
    private JPanel bottomPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JButton applyButton;
    
    public PreferencesWindow() {
        super( "Paint Preferences" );
        setSize( 575, 150 );
        setMinimumSize( new Dimension( 230, 123 ) );
        setLayout( new BorderLayout() );
        
        // top half
        drawPanel = new DrawPanel( new JLabel() );
        
        FileParser parser = new FileParser( drawPanel, true );
        parser.parse();
        
        // reuses the toolbar
        Toolbar toolbar = new Toolbar( drawPanel );
        toolbar.removeButtons();
        
        // bottom half
        bottomPanel = new JPanel();
        okButton = new JButton( "OK" );
        cancelButton = new JButton( "Cancel" );
        applyButton = new JButton( "Apply" );
        
        okButton.addActionListener( new ButtonHandler() );
        cancelButton.addActionListener( new ButtonHandler() );
        applyButton.addActionListener( new ButtonHandler() );
        
        bottomPanel.add( okButton );
        bottomPanel.add( cancelButton );
        bottomPanel.add( applyButton );
        
        // add toolbar and bottom JPanel
        add( toolbar, BorderLayout.CENTER );
        add( bottomPanel, BorderLayout.SOUTH );
        
        addWindowListener( new WindowHandler() );
    } // end constructor
    
    /*
     * Either saves data or exits (or both), based on which
     * button was pressed.
     */
    private class ButtonHandler implements ActionListener {
        public void actionPerformed( ActionEvent event ) {
            // fileparser - set second argument to false for writing data
            FileParser fileParser = new FileParser( drawPanel, false );
            if ( event.getSource() == okButton ) {
                fileParser.parse();
                setVisible( false );
            }
            else if ( event.getSource() == cancelButton ) {
                setVisible( false );
            }
            else if ( event.getSource() == applyButton ) {
                fileParser.parse();
            } // end if
        } // end method
    } // end inner class
    
    /*
     * Iconifies the window based on certain cases.
     */
    private class WindowHandler implements WindowListener {
        
        public void windowClosing( WindowEvent event ) {
            setVisible( false );
        }
        
        public void windowIconified( WindowEvent e ) {
            setVisible( false );
        }
        
        // not required
        public void windowOpened( WindowEvent event ) {}
        public void windowClosed( WindowEvent e ) {}
        public void windowDeiconified( WindowEvent e ) {}
        public void windowActivated( WindowEvent e ) {}
        public void windowDeactivated( WindowEvent e ) {}
    } // end inner class
} // end class
