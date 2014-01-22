/**
 * Dynamic stack.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
public class DynamicStack {
    private LinkedList stack = new LinkedList();
    
    // checks and returns if it is empty
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    // pushes an Object to the stack
    public void push( Object object ) {
        stack.addFront( object );
    }
    
    // removes and returns the top of the stack
    public Object pop() {
        return stack.removeFront();
    }
    
    // returns the top of the stack
    public Object peek() {
        return stack.peek();
    }
    
    // makes the stack empty
    public void makeEmpty() {
        stack.makeEmpty();
    }
} // end class
