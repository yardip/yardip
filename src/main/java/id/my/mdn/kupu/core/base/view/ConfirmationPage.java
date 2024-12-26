/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view;

import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.view.annotation.OnInit;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author aphasan
 */
@Named(value = "confirmationPage")
@ConversationScoped
public class ConfirmationPage extends ChildPage implements Serializable {

    @Inject
    private Conversation conversation;
    
    private Integer what;

    private String message;

    private Boolean confirm;
    
    private List<String> messageParams;

    public List<String> getMessageParams() {
        return messageParams;
    }

    public void setMessageParams(List<String> messageParams) {
        this.messageParams = messageParams;
    }

    @OnInit
    public void init() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void yes(ActionEvent event) {
        confirm = true;
        finish();
    }

    public void no(ActionEvent event) {
        confirm = false;
        up();
    }

    public void finish() {
        RequestedView parent = getBackstack().pop();
        parent.addParam("h").withValues(Base64.getEncoder().encodeToString(parent.toString().getBytes()))
                .addParam("r").withValues(confirm)
                .addParam("c").withValues("BooleanConverter")
                .addParam("w").withValues(what);
        
        if (!conversation.isTransient()) {
            conversation.end();
        }
        
        parent.open();
    }

    public Integer getWhat() {
        return what;
    }

    public void setWhat(Integer what) {
        this.what = what;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
