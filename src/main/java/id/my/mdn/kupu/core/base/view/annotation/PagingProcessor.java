
package id.my.mdn.kupu.core.base.view.annotation;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.IPageable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public class PagingProcessor {

    public static void process(Page page) {
        Class<? extends Page> cls = page.getClass();
        Stream.of(cls.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> IPageable.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return field.get(page);
                    } catch (IllegalAccessException
                            | IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(obj -> obj != null)
                .forEach(obj -> {
                    try {
                        IPageable pageable = (IPageable) obj;
                        pageable.addPagerListener(page::updateUrl);
                    } catch (SecurityException | IllegalArgumentException ex) {
                        Logger.getLogger(PagingProcessor.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                });
    }
}
