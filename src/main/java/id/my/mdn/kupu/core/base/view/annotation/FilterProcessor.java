
package id.my.mdn.kupu.core.base.view.annotation;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.IFilterable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public class FilterProcessor {

    public static void process(Page page) {
        Class<? extends Page> cls = page.getClass();
        Stream.of(cls.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> IFilterable.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (IFilterable)field.get(page);
                    } catch (IllegalAccessException
                            | IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(obj -> obj != null)
                .forEach(obj -> {
                    try {
                        IFilterable filterable = (IFilterable) obj;
                        filterable.addFilterListener(page::updateUrl);
                    } catch (SecurityException | IllegalArgumentException ex) {
                        Logger.getLogger(FilterProcessor.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                });
    }
}
