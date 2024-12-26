/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.model;

import jakarta.persistence.PrePersist;
import java.util.UUID;

/**
 *
 * @author aphasan
 */
public class ReportTemplateListener {

    @PrePersist
    private void prePersist(ReportTemplate entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.nameUUIDFromBytes(entity.getName().getBytes()).toString());
        }
    }
    
}
