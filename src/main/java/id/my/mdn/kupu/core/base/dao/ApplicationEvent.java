
package id.my.mdn.kupu.core.base.dao;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Arief Prihasanto <ariefp5758 at gmail.com>
 */
public abstract class ApplicationEvent {
    
    protected ConcurrentHashMap<String, Object> parameters;
    
    public ApplicationEvent() {
        parameters = new ConcurrentHashMap<>();
    }
    
    public void setParam(String name, Object value) {
        parameters.put(name, value);
    }
    
    public Object getParam(String name) {
        return parameters.get(name);
    }
}
