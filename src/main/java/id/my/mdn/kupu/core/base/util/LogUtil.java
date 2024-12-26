package id.my.mdn.kupu.core.base.util;

import java.util.logging.Level;
import static java.util.logging.Level.FINE;
import java.util.logging.Logger;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class LogUtil { 

    private static final Logger LOG = Logger.getLogger("kupu");
    
    public static void logMethod(Object context, Object param) {
        LOG.log(FINE, "{0}.{1} '{'{2}'}'", new Object[]{
                context.getClass().getCanonicalName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                param
        });
    }
    
    public static void log(String template, Object... params) {
        LOG.log(Level.FINE, template, params);
    }
    
}
