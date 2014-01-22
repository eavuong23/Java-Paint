/**
 * Contains the class for a ListNode Object, which is the foundation for a
 * linked list. Each ListNode refers to at most two other ListNodes: the next
 * and previous. Each also contains some value that can be accessed and modified.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
class ListNode {
    private Object value;
    private ListNode next;
    private ListNode previous;
    
    // Constructor
    public ListNode( Object nodeValue ) {
        this( nodeValue, null, null);
    }
    
    // Constructor
    public ListNode( Object nodeValue, ListNode nodeNext, ListNode nodePrevious ) {
        value = nodeValue;
        next = nodeNext;
        previous = nodePrevious;
    }
    
    // Accessor: Return the value for current node object
    public Object getValue() {
        return value;
    }
    
    // Accessor: Return reference to next node object
    public ListNode getNext() {
        return next;
    }
    
    // Accessor: Return reference to previous node object
    public ListNode getPrevious() {
        return previous;
    }
    
    // Mutator: Set new value for current node object
    public void setValue( Object newValue ) {
        value = newValue;
    }
    
    // Mutator: Set new reference to the next node object
    public void setNext( ListNode newNext ) {
        next = newNext;
    }
    
    // Mutator: Set new reference to the previous node object
    public void setPrevious( ListNode newPrevious ) {
        previous = newPrevious;
    }
} // end class