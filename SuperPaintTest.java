import javax.swing.JFrame;

/**
 * Contains the test class for the Ultra Paint Program. Creates the JFrame
 * application and configures some initial settings.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class SuperPaintTest {
    public static void main(String[] args) {
        DrawFrame application = new DrawFrame();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        application.setSize( 667, 589 );
        application.setVisible( true );
    }
}