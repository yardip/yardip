package id.my.mdn.kupu.core.base.view.annotation;

import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.ISelectable;
import id.my.mdn.kupu.core.base.view.widget.Selector.SelectorListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public class SelectProcessor {

    public static void process(Page page) {
        Class<? extends Page> cls = page.getClass();
        Stream.of(cls.getDeclaredFields()) // Iterate over Page class declared fields.
                .peek(field -> field.setAccessible(true)) // Make those fields accessible.
                .filter(field -> ISelectable.class.isAssignableFrom(field.getType())) // Filter those which aren't Selectable type out.
                .map(selectableField -> { // Map to
                    try {
                        return selectableField.get(page); //  its value over page,
                    } catch (IllegalAccessException
                            | IllegalArgumentException ex) {
                        return null; // or null
                    }
                })
                .filter(selectableObj -> selectableObj != null) // Filter null object out.
                .forEach(selectableObj -> { // For each Selectable object, 
                    try {
                        ISelectable selectable = (ISelectable) selectableObj;
                        selectable.addSelectListener(new SelectorListener() {
                            @Override
                            public void onSelect(Object selected) {
                                page.updateUrl();
                            }
                        });
                    } catch (SecurityException | IllegalArgumentException ex) {
                        Logger.getLogger(SelectProcessor.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                });
    }
}
