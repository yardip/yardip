/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.util;

import id.my.mdn.kupu.core.party.dao.OrganizationFacade;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Path("entitas")
@RequestScoped
public class EntitasStreamer {   
    
    @Inject
    private OrganizationFacade dao;
    
    @GET
    @Path("{entityId}")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getDoc(@PathParam("entityId") Long id) {
        byte[] content = dao.getLogo(id);
        return Response.ok(content)
                .build();
    }
    
}
