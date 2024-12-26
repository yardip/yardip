/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.mail.model;

import java.io.File;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public final class MailMessage {
    public final String BODY;
    public final File ATTACHMENTS;
    public final String CONTENT_TYPE;
    public final String ENCODING;

    public MailMessage(String BODY, File ATTACHMENTS, String CONTENT_TYPE, String ENCODING) {
        this.BODY = BODY;
        this.ATTACHMENTS = ATTACHMENTS;
        this.CONTENT_TYPE = CONTENT_TYPE;
        this.ENCODING = ENCODING;
    }
}
