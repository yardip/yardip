/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.base.view;

import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author aphasan
 */
public class Confirmation {
    
    private Page page;
    
    private int what;
    
    private String message = "";
    
    private String[] params;
    
    private Confirmation() {}
    
    private Confirmation(Page page) {
        this.page = page;
    }
    
    public static Confirmation from(Page page) {
        return new Confirmation(page);
    }
    
    public Confirmation on(int what) {
        this.what = what;
        return this;
    }
    
    public Confirmation withMessage(String message) {
        this.message = message;
        return this;
    }
    
    public Confirmation andMessageParams(String... params) {
        this.params = params;
        return this;
    }
    
    public void open() {
//        page.updateUrl();
//        String compiledMessage = PageUtil.compileMessage(message, params);
        page.gotoChild(ConfirmationPage.class)
                .addParam("w")
                .withValues(what)
                .addParam("m")
                .withValues(Base64.getEncoder().encodeToString(message.getBytes()))
                .addParam("p")
                .withValues(Stream.of(params)
                        .map(param -> Base64.getEncoder().encodeToString(param.getBytes()))
                        .collect(Collectors.joining(";")))
                .open();
    }
    
}
