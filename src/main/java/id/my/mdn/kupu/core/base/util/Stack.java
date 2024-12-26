
package id.my.mdn.kupu.core.base.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 *
 * @author aphasan
 * @param <E>
 */
public class Stack<E> {
    
    private final Deque<E> deque = new ArrayDeque<>();

    public E pop() {
        try { 
            return deque.pop();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }
    
    public E peek() {
        return deque.peek();
    }
    
    public void push(E view) {
        deque.push(view);
    }
    
    public E top() {
        return deque.peekFirst();
    }
    
    public int size() {
        return deque.size();
    }
    
    public boolean isEmpty() {
        return deque.isEmpty();
    }
    
    public void print() {     
    }
    
    public void clear() {
        deque.clear();
    }
}
