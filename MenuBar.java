import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Creates a menubar, with some minor features.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class MenuBar extends JMenuBar {
    private DrawFrame drawFrame;
    private Information aboutWindow;
    private PreferencesWindow preferencesWindow;
    
    // File
    private JMenu file;
    private JMenuItem about;
    private JMenuItem preferences;
    private JMenuItem exit;
    
    // Window
    private JMenu window;
    private JMenuItem createWindow;
    private JMenuItem showDesktop;
    
    public MenuBar( DrawFrame drawFrame ) {
        this.drawFrame = drawFrame;
        aboutWindow = new Information();
        preferencesWindow = new PreferencesWindow();
        
        file = new JMenu( "File" );
        file.setMnemonic( KeyEvent.VK_F );
        // submenus of "File"
        about = new JMenuItem( "About", KeyEvent.VK_A );
        preferences = new JMenuItem( "Preferences", KeyEvent.VK_P );
        exit = new JMenuItem( "Exit", KeyEvent.VK_X );
        
        file.add( about );
        file.add( preferences );
        file.add( exit );
        
        window = new JMenu( "Window" );
        //submenus of "Window"
        createWindow = new JMenuItem( "New Window", KeyEvent.VK_N );
        showDesktop = new JMenuItem( "Show Desktop", KeyEvent.VK_S );
        
        window.add( createWindow );
        window.add( showDesktop );
        window.setMnemonic( KeyEvent.VK_W );
        
        // all add menu handlers
        about.addActionListener( new MenuHandler() );
        preferences.addActionListener( new MenuHandler() );
        exit.addActionListener( new MenuHandler() );
        createWindow.addActionListener( new MenuHandler() );
        showDesktop.addActionListener( new MenuHandler() );
        
        add( file );
        add( window );
    } // end constructor
    
    /*
     * Handles menu items.
     */
    private class MenuHandler implements ActionListener {
        public void actionPerformed( ActionEvent event ) {
            if ( event.getSource() == about ) {
                aboutWindow.setVisible( true );
            }
            else if ( event.getSource() == preferences ) {
                preferencesWindow.setVisible( true );
            }
            else if ( event.getSource() == exit ) {
                System.exit( 0 );
            }
            else if ( event.getSource() == createWindow ) {
                drawFrame.newWindow();
            }
            else if ( event.getSource() == showDesktop ) {
                drawFrame.iconifyAllWindows();
            } // end if
        } // end method
    } // end inner class
} // end class
