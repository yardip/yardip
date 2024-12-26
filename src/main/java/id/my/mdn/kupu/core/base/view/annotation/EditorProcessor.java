
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
public class EditorProcessor {

    public static void process(Object obj) {
        Class cls = obj.getClass();
        Stream.of(cls.getDeclaredMethods())
                .forEach(method -> {
                    Editor editorAnnotation = method.getDeclaredAnnotation(Editor.class);
                    if (editorAnnotation != null) {
                        method.setAccessible(true);
                        String listVar = editorAnnotation.of();
                        try {
                            Field listField = cls.getDeclaredField(listVar);
                            if (listField != null) {

                                listField.setAccessible(true);

                                Object list = listField.get(obj);

                                if(list != null) {
                                    Method setEditor = list.getClass().getMethod("setEditor", PageCaller.class);
                                    if(setEditor != null) {
                                        setEditor.setAccessible(true);
                                        setEditor.invoke(list, (PageCaller) () -> {
                                            try {
                                                method.invoke(obj);
                                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                                Logger.getLogger(EditorProcessor.class.getName()).log(Level.SEVERE, null, ex);
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
