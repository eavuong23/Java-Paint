/**
 * Contains the class to create a fully-functional linked list. Can perform
 * operations such as insertion and deletion with various parts of the list.
 *
 * @author Edward Vuong
 * @version May 31, 2012
 */
class LinkedList {
    private ListNode head;
    private ListNode tail;
    private int size;
    
    /**
     * Constructor: defaults to an empty linked list.
     */
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    } // end class constructor
    
    /**
     * Gets the size of the linked list.
     *
     * @return The size of the linked list.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Checks and returns whether the linked list is empty
     * or not.
     *
     * @return Whether it is empty or not.
     */
    public boolean isEmpty() {
        return (head == null);
    }
    
    /**
     * Deletes all list nodes.
     */
    public void makeEmpty() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /** Add a node to the head of the linked list.
      *
      * @param item The Object to insert at the front.
      */
    public void addFront( Object item ) {
        ListNode temp = new ListNode( item, head, null );
        
        if ( isEmpty() )
            tail = temp;
        else if ( tail.equals(head) ) // i.e. there is only one element in the LL
            // the tail doesn't change, but you must assoc. the "previous"
            attachNodes(temp, tail);
        //tail.setPrevious( temp );
        
        head = temp;
        attachNodes(head, head.getNext());
        size++;
    }
    
    /**
     * Adds an item to the end of the linked list.
     *
     * @param object The Object to insert at the end.
     */
    public void addEnd( Object object ) {
        ListNode temp = new ListNode(object, null, tail);
        
        if ( isEmpty() )
            head = temp;
        else if ( head.equals(tail) )
            attachNodes(head, temp);
        
        tail = temp;
        attachNodes(tail.getPrevious(), tail);
        size++;
    }
    
    /**
     * Remove a node from the head of the linked list and return a reference to
     * its value. Return null if the linked list is empty.
     *
     * @return Value of the node node.
     */
    public Object removeFront() {
        Object tempValue;
        
        if (isEmpty())
            return null;
        else if ( tail.equals(head) )
            tail = null;
        
        tempValue = head.getValue();
        head = head.getNext(); // move on
        
        // associates the "previous" of "head" with null (since the initial
        // front does not exist anymore)
        attachNodes(null, head);
        size--;
        return tempValue;
    }
    
    /**
     * Removes and returns the last node's value.
     *
     * @return The value of the last node (as an Object)
     */
    public Object removeEnd() {
        Object temp;
        
        if ( isEmpty() )
            return null;
        else if ( head.equals(tail) ) // if same, then head = null (and later, tail = null)
            head = null;
        
        temp = tail.getValue();
        tail = tail.getPrevious();
        
        attachNodes( tail, null ); // set the next (i.e. which is the last item reference) to null
        size--;
        return temp;
    }
    
    /**
     * Search for an item in the list and returns reference to ListNode if
     * found, null otherwise
     *
     * @param key
     * @return
     */
    public ListNode search( Object key ) {
        ListNode searchNode;
        searchNode = head;
        while ( (searchNode != null) && (!key.equals(searchNode.getValue())) ) {
            searchNode = searchNode.getNext(); // your basic traverse
        }
        return searchNode;
    }
    
    /**
     * Insert a node in the list with a given key value.
     *
     * @param key The value to insert.
     */
    public void insert( Comparable<Object> key ) {
        ListNode before = null;
        ListNode after = head;
        ListNode newNode;
        
        while ((after != null) && (key.compareTo(after.getValue()) > 0)) {
            before = after;
            after = after.getNext();
        }
        
        // Adjust head of the list or set before's link to point to
        // new node, as appropriate
        if (before == null) { // i.e. addFront
            addFront( key );
        }
        else if ( after == null ) // i.e. addEnd
            addEnd( key );
        else {
            // this is inserting something in between the array, thus the head
            // is not changed.
            // note that "before" is actually a reference to an Object, and thus
            // can change parts of that object (e.g. x = [[1], [2]] (pretend
            // each element is a ListNode), y = x[0], y[0] = 5 and now
            // x = [[5], [2]]
            newNode = new ListNode( key, after, before );
            attachNodes(before, newNode);
            attachNodes(newNode, after);
        }
        size++;
    }
    
    /**
     * Delete a node in the list with a given key value. Returns true if
     * found/deleted, false if not found/deleted.
     *
     * @param key The node to be deleted.
     * @return Whether or not the specified node has been successfully removed.
     */
    public boolean delete( Object key ) {
        ListNode before = null;
        ListNode current = head; // to be removed
        
        // brute force search (only option, with this implementation
        while ( (current != null) && (!key.equals(current.getValue())) ) {
            before = current; // keeps track of before
            current = current.getNext();
        }
        
        // return false if the key was not found in the linked list
        if (current == null) {
            return false;
        }
        
        // Adjust head of the list or set before's link to point to
        // the node after current, as appropriate
        if (before == null) { // i.e. removeFront
            removeFront();
        }
        else if (current.equals(tail)) // if @ tail, then essentially removing the end
            removeEnd();
        else {
            //not near the head/tail
            
            // re-links the list by re-attaching the item BEFORE the current to
            // the item AFTER the current (current = item to-be-deleted)
            attachNodes(before, current.getNext() );
        }
        size--;
        return true;
    }
    
    /**
     * Returns the head node (as a ListNode Object).
     *
     * @return The head node.
     */
    public ListNode peek() {
        return head;
    }
    
    /**
     * Associates ("attaches", or "chains") two nodes together in the linked
     * list, by setting the first node's <code>next</code> variable to the
     * second node, and setting the second node's <code>previous</code> variable
     * to the first.
     *
     * @param firstNode The first ListNode that you want to directly precede the second node.
     * @param secondNode The second ListNode that you want to directly follow from the first node.
     */
    private void attachNodes( ListNode firstNode, ListNode secondNode ) {
        if ( firstNode != null )
            firstNode.setNext( secondNode );
        if ( secondNode != null )
            secondNode.setPrevious( firstNode );
    }
    
    // Return String representation of the linked list.
    public String toString() {
        ListNode node;
        String linkedList = "HEAD ==> ";
        
        node = head;
        while (node != null) {
            linkedList += "[ " + node.getValue() + " ] ==> ";
            node = node.getNext();
        }
        
        return linkedList + "NULL";
    }
}