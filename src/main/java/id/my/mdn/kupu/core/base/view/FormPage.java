/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.widget.Toast;
import jakarta.enterprise.context.Conversation;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.PhaseId;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aphasan
 * @param <T>
 */
public abstract class FormPage<T> extends ChildPage implements Serializable {

    private static final Logger LOG = Logger.getLogger(FormPage.class.getName());

    @FunctionalInterface
    public static interface UpdateListener {

        void onUpdate(Object object);
    }

    @Inject
    protected Conversation conversation;

    protected T entity;

    protected boolean createNew = false;

    protected FormComponent.ValidityChecker validityChecker = () -> checkFormValidity();

    protected Result<String> checkFormValidity() {
        return new Result<>(true, null);
    }

    @Override
    public void load() {
        if (conversation != null && conversation.isTransient()) {
            conversation.begin();
        }
        if (entity == null) {
            setEntity(newEntity());
            createNew = true;
        } else {
            loadEntity();
        }
    }

    public void save(ActionEvent evt) {
        Result<String> saveResult = save();
        if (saveResult.success) {
            if (conversation != null && !conversation.isTransient()) {
                conversation.end();
            }
            up();
        } else {
            Toast.error(saveResult.payload);
        }
    }

    public void cancel(ActionEvent evt) {
        if (conversation != null && !conversation.isTransient()) {
            conversation.end();
        }
        up();
    }

    @Override
    public void updateUrl() {
        super.updateUrl();
        if (conversation != null && !conversation.isTransient()) {
            Map<String, List<String>> params = new HashMap<>();
            params.put("cid", Arrays.asList(conversation.getId()));
            getViewUrl().getParams().putAll(params);
        }
    }

    protected Result<String> save() {

        for (Field f : getClass().getDeclaredFields()) {
            if (f.getAnnotation(id.my.mdn.kupu.core.base.view.annotation.Form.class) != null) {
                try {
                    f.setAccessible(true);
                    Object obj = f.get(this);
                    if (obj instanceof FormComponent) {
                        FormComponent formComponent = (FormComponent) obj;
                        Result<String> componentResult = formComponent.getValidityChecker().isValid();

                        if (componentResult.isSuccess()) {
                            formComponent.getPacker().pack();
                        } else {
                            return componentResult;
                        }

                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        Result<String> pageResult = validityChecker.isValid();

        if (pageResult.isNotSuccess()) {
            return pageResult;
        }
        
        if (createNew) {
            return saveEntity();
        } else {
            return editEntity();
        }
    }

    protected abstract T newEntity();

    protected void loadEntity() {
    }

    protected Result<String> saveEntity() {
        return save(entity);
    }

    protected abstract Result<String> save(T entity);

    protected Result<String> editEntity() {
        return edit(getEntity());
    }

    protected abstract Result<String> edit(T entity);

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        PhaseId currentPhaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
        if (!currentPhaseId.equals(PhaseId.UPDATE_MODEL_VALUES)
                || (currentPhaseId.equals(PhaseId.UPDATE_MODEL_VALUES) && !createNew)) {
            this.entity = entity;
        }
    }

    public boolean isCreateNew() {
        return createNew;
    }

    public void setCreateNew(boolean createNew) {
        this.createNew = createNew;
    }

}
