
package id.my.mdn.kupu.core.base.view.widget;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import java.io.Serializable;



/**
 *
 * @author aphasan
 * @param <E>
 */
@Named
@Dependent
public class EditingFeature<E extends Serializable> implements IEditable<E>, Serializable {
    
    private E editedValue;

    @Override
    public void toggleEdit(E value) {
        if (value.equals(editedValue)) {
            editedValue = null;
        } else {
            editedValue = value;
        }
    }

    @Override
    public boolean isEditing(E data) {
        return data.equals(editedValue);
    }

    @Override
    public E getEditedValue() {
        return editedValue;
    }

    @Override
    public void setEditedValue(E editedValue) {
        this.editedValue = editedValue;
    }

    @Override
    public void save(E editedValue) {
        
    }
    
}
