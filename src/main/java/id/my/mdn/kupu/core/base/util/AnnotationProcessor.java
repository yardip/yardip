
package id.my.mdn.kupu.core.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 */
public class AnnotationProcessor {

    public static void execute(Object context, Class annotation, List<Object> returns, Object... params) {
        Arrays.asList(context.getClass().getMethods()).stream()
                .filter(method -> method.getAnnotation(annotation) != null)
                .forEach(method -> {
                    try {
                        Method m = (Method) method;
                        m.setAccessible(true);
                        Object ret = m.invoke(context, params);
                        if (returns != null) {
                            returns.add(ret);
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(AnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }
}
