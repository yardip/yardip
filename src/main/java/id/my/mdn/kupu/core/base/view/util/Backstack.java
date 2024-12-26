
package id.my.mdn.kupu.core.base.view.util;

import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.util.Stack;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "backstack")
@SessionScoped
public class Backstack implements Serializable {
    
    private final Stack<RequestedView> stack = new Stack<>();

    public RequestedView pop() { 
        RequestedView view = stack.pop();
        return view != null ? view : new RequestedView("/index.xhtml");
    }
    
    public RequestedView peek() {
        return stack.peek();
    }
    
    public void push(RequestedView view) {
        stack.push(view);
    }

    public void clear() {
        stack.clear();
    }

    public void print() {
        stack.print();
    }
    
}
