import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Contains a window with some text about the author.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class Information extends JFrame {
    private JTextArea textArea;
    private JButton close;
    
    public Information() {
        super( "About Me" );
        setSize( 300, 200 );
        setLayout( new BorderLayout() );
        
        String text = "Ultra Paint Ultimate Version\n\nHello, my name is Edward.\nThis was last updated on May 31, 2012.";
        textArea = new JTextArea( text );
        textArea.setEditable( false );
        
        close = new JButton( "Close" );
        // makes the button hide the window
        close.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                setVisible( false );
            }
        } ); // end anonymous inner-class
        
        add( textArea, BorderLayout.CENTER );
        add( close, BorderLayout.SOUTH );
        
        this.addWindowListener( new WindowHandler() );
    } // end constructor
    
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
        
        public void windowDeactivated( WindowEvent e ) {
            setVisible( false );
        }
        
        // not required
        public void windowOpened( WindowEvent event ) {}
        public void windowClosed( WindowEvent e ) {}
        public void windowDeiconified( WindowEvent e ) {}
        public void windowActivated( WindowEvent e ) {}
    } // end inner class
} // end class
