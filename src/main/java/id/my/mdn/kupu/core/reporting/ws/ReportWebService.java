/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package id.my.mdn.kupu.core.reporting.ws;

import id.my.mdn.kupu.core.reporting.exception.ReportCompilationException;
import id.my.mdn.kupu.core.reporting.jasperreports.ReportLoadingException;
import id.my.mdn.kupu.core.reporting.util.Reporter;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * REST Web Service
 *
 * @author aphasan
 */
@Path("report")
@RequestScoped
public class ReportWebService {

    @Inject
    private Reporter reporter;

    @GET
    @Path("{template}.jasper")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getPlaylist(@PathParam("template") String template) {
        try {
            byte[] compiledTemplate = reporter.compileNSerializeTemplate(template);
            if (compiledTemplate == null) {
                return Response.noContent().build();
            }
            return Response.ok(compiledTemplate)
                    .header("Content-Type", "application/octet-stream")
                    .build();
        } catch (ReportLoadingException | ReportCompilationException ex) {
            Logger.getLogger(ReportWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }

    }
}
