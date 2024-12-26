/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.service;

import id.my.mdn.kupu.core.base.util.ModuleInfo;
import id.my.mdn.kupu.core.reporting.dao.ReportTemplateFacade;
import id.my.mdn.kupu.core.reporting.model.ReportTemplate;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class ReportingService {

    @Inject
    private ReportTemplateFacade reportTemplateFacade;

    public void dumpTemplate(ModuleInfo moduleInfo) {
        InputStream in = moduleInfo.getResourceAsStream("template.json");
        if (in != null) {

            try (JsonReader jsonReader = Json.createReader(in)) {
                JsonObject jsonObject = jsonReader.readObject();
                for (String tplName : jsonObject.keySet()) {
                    String tplFileName = jsonObject.getString(tplName);
                    String id = UUID.nameUUIDFromBytes(tplName.getBytes()).toString();
                    ReportTemplate tpl = reportTemplateFacade.find(id);
                    
                    if(tpl != null) reportTemplateFacade.remove(tpl);

//                    if (tpl == null) {

                        try (BufferedReader tplReader = new BufferedReader(new InputStreamReader(moduleInfo.getResourceAsStream(tplFileName)))) {
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = tplReader.readLine()) != null) {
                                sb.append(line).append("\r\n");
                            }

                            tpl = new ReportTemplate();
                            tpl.setId(id);
                            tpl.setName(tplName);
                            tpl.setSourceTemplate(sb.toString());

                            reportTemplateFacade.create(tpl);

                        } catch (IOException ex) {
                            Logger.getLogger(ReportingService.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                    }
                }
            }
        }

    }
}
