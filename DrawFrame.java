import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * The primary controller for all GUI components. Contains the drawpanel and
 * toolbar.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class DrawFrame extends JFrame {
    private JDesktopPane desktop;
    private int counter = 1;
    private JLabel statusLabel = new JLabel( "(0, 0)" );
    
    private DrawPanel drawPanel;
    private Toolbar toolbar;
    private MenuBar menu;
    
    /**
     * Class Constructor. Creates all necessary widgets, and implements all
     * handlers.
     */
    public DrawFrame() {
        super( "Ultra Paint" );
        setMinimumSize( new Dimension(200, 118) ); // sets the minimum window size
        
        desktop = new JDesktopPane();
        desktop.setDragMode( JDesktopPane.LIVE_DRAG_MODE );
        desktop.setDesktopManager( new DefaultDesktopManager() );
        
        // create the drawpanel and the toolbar
        drawPanel = new DrawPanel( statusLabel );
        toolbar = new Toolbar( drawPanel );
        menu = new MenuBar( this );
        
        // add
        add( toolbar, BorderLayout.NORTH );
        add( desktop, BorderLayout.CENTER );
        add( statusLabel, BorderLayout.SOUTH );
        setJMenuBar( menu );
        newWindow();
    } // end constructor
    
    /*
     * Constructs a new window ("paint canvas").
     */
    public void newWindow() {
        DrawPanel drawPanel = new DrawPanel( statusLabel );
        DrawWindow window = new DrawWindow( String.format( "Window #%d", counter ), drawPanel );
        window.setLocation( (counter % 15) * 20, (counter % 15) * 20 );
        desktop.add( window );
        counter++;
        
        // set selected
        try {
            window.setSelected( true );
        } catch ( PropertyVetoException exception ) {
        }
        
        FileParser parser = new FileParser( drawPanel, true );
        parser.parse();
        
        // make the toolbar focus on the attributes of this drawPanel
        toolbar.setFocusPanel( drawPanel );
        
        window.addInternalFrameListener( new InternalFrameHandler() );
        
    } // end method
    
    /*
     * Shows the desktop by iconifying all the JInternalFrames.
     */
    public void iconifyAllWindows() {
        for ( JInternalFrame window : desktop.getAllFrames() ) {
            try {
                window.setIcon( true );
            } catch ( PropertyVetoException exception ) {
                exception.printStackTrace();
            } // end try block
        } // end for
    } // end method
    
    /*
     * Gives focus of a window to the toolbar.
     */
    private class InternalFrameHandler extends InternalFrameAdapter {
        public void internalFrameActivated( InternalFrameEvent event ) {
            toolbar.setFocusPanel( ((DrawWindow) event.getInternalFrame()).getDrawPanel() );
        }
    } // end inner class
} // end class
