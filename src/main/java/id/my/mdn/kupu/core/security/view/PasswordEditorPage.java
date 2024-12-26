/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.view;

import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.security.dao.ApplicationUserFacade;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
import id.my.mdn.kupu.core.security.util.PasswordUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;

/**
 *
 * @author aphasan
 */
@Named(value = "passwordEditorPage")
@ConversationScoped
public class PasswordEditorPage extends FormPage<ApplicationUser> {

    @Inject
    private ApplicationUserFacade userFacade;
    
    private String oldPassword;
    
    private String newPassword;
    
    private String confirmPassword;
    
    @Inject
    private SecurityContext securityContext;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        
        String username = securityContext.getCallerPrincipal().getName();
        
        entity = userFacade.findByUsername(username);
        
    }

//    public void save(ActionEvent evt) {
//        save();
//    }

    @Override
    protected Result<String> save(ApplicationUser entity) {
        return new Result<>(true, "Fungsi tidak didefinisikan");
    }

    @Override
    protected Result<String> checkFormValidity() {
        
        String savedPasssword = entity.getPassword();
        
        if(!savedPasssword.equals(PasswordUtil.generate(oldPassword))) {
            return new Result(false, "Password Lama salah");
        }
        
        if(!newPassword.equals(confirmPassword)) {
            return new Result(false, "Konfirmasi Password salah");
        }
        
        
        return new Result(true, "");
    }

    @Override
    protected Result<String> edit(ApplicationUser entity) {
        
        entity.setPassword(PasswordUtil.generate(newPassword));
        
//        if (confirmedPassword != null && !confirmedPassword.isEmpty()) {
//            entity.setPassword(PasswordUtil.generate(confirmedPassword));
//        }

        return userFacade.edit(entity);
    }

//    public String getConfirmedPassword() {
//        return confirmedPassword;
//    }

//    public void setConfirmedPassword(String confirmedPassword) {
//        this.confirmedPassword = confirmedPassword;
//    }

    @Override
    protected ApplicationUser newEntity() {
        return null;
    }

}
