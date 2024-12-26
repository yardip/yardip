/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.core.base.view.widget;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "textEditor")
@Dependent
public class TextEditorBean implements Serializable {

    @FunctionalInterface
    public interface EditorLoadListener {
        String onLoad(Object context);
    }

    @FunctionalInterface
    public interface EditorSaveListener {
        void onSave(Object context, String content);
    }

    private Object id = null;

    private StringBuilder buffer = new StringBuilder();

    private boolean inUse = false;

    private EditorLoadListener loadListener;
    private EditorSaveListener saveListener;

    public void load(Object id) {
        if (inUse) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Editor sedang digunakan",
                            "Harap simpan atau batalkan perubahan sebelumnya.")
            );
            return;
        }
        if (loadListener != null) {
            String s = loadListener.onLoad(id);
            if(s != null) buffer.append(s);
        }
        
        this.id = id;
        inUse = true;
    }

    public String getBuffer() {
        return buffer.toString();
    }

    public void setBuffer(String buffer) {
        this.buffer.delete(0, this.buffer.length()).append(buffer);
    } 

    public void save(ActionEvent evt) {
        if (saveListener != null) {
            saveListener.onSave(id, buffer.toString());
        }
        buffer.delete(0, buffer.length()).trimToSize();
        id = null;
        inUse = false;
    }

    public void onChange(ValueChangeEvent evt) {
        buffer.delete(0, buffer.length()).append(evt.getNewValue().toString()).trimToSize();
    }

    public void cancel() {
        buffer.delete(0, buffer.length()).trimToSize();
        id = null;
        inUse = false;
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Editor dibatalkan",
                        "Perubahan tidak disimpan.")
        );
    }

    public boolean isEditing(Object id) {
        return id.equals(this.id);
    }

    public void addLoadListener(EditorLoadListener listener) {
        loadListener = listener;
    }

    public void addSaveListener(EditorSaveListener listener) {
        saveListener = listener;
    }

}
