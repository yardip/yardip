
package id.my.mdn.kupu.core.base.view.annotation;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.PageCaller;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public class CreatorProcessor {

    public static void process(Object obj) {
        Class cls = obj.getClass();
        Stream.of(cls.getDeclaredMethods())
                .forEach(method -> {
                    Creator creatorAnnotation = method.getDeclaredAnnotation(Creator.class);
                    if (creatorAnnotation != null) {
                        method.setAccessible(true);
                        String listVar = creatorAnnotation.of();
                        try {
                            Field listField = cls.getDeclaredField(listVar);
                            if (listField != null) {

                                listField.setAccessible(true);

                                Object list = listField.get(obj);

                                if(list != null) {
                                    Method setCreator = list.getClass().getMethod("setCreator", PageCaller.class);
                                    if(setCreator != null) {
                                        setCreator.setAccessible(true);
                                        setCreator.invoke(list, (PageCaller) () -> {
                                            try {
                                                method.invoke(obj);
                                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                                Logger.getLogger(CreatorProcessor.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        });
                                    }
                                }
                            }
                        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                            Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                });
    }
}
