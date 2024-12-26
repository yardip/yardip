
package id.my.mdn.kupu.core.base.view.widget;

/**
 *
 * @author aphasan
 * @param <E>
 */
public interface IEditable<E> {

    E getEditedValue();

    void setEditedValue(E editedValue);

    void toggleEdit(E value);

    boolean isEditing(E value);

    default void saveEdit() {
        save(getEditedValue());
        setEditedValue(null);
    }

    void save(E editedValue);

}
