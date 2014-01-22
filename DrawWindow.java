import javax.swing.JInternalFrame;

/**
 * Creates one JInternalFrame window. This is extended so that it may return a
 * drawPanel Object.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class DrawWindow extends JInternalFrame {
    private DrawPanel drawPanel;
    
    public DrawWindow( String title, DrawPanel drawPanel ) {
        super( title, true, true, true, true );
        this.drawPanel = drawPanel;
        add( this.drawPanel );
        setSize( 275, 230 );
        setVisible( true );
    } // end constructor
    
    /*
     * Returns a drawPanel object.
     */
    public DrawPanel getDrawPanel() {
        return drawPanel;
    }
} // end class
