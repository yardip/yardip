/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.yardip.util;

import id.my.mdn.kupu.app.yardip.dao.BuktiKasFacade;
import id.my.mdn.kupu.app.yardip.model.BuktiKas;
import id.my.mdn.kupu.app.yardip.model.BuktiKasId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
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
@Path("bk")
@RequestScoped
public class BuktiKasStreamer {
    
    @Inject
    private BuktiKasFacade dao;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getDoc(@PathParam("id") String id) {
        BuktiKas doc = dao.find(new BuktiKasId(EntityUtil.parseCompositeId(id)));
        return Response.ok(doc.getContent())
                .header("Content-Type", doc.getContentType())
                .header("Content-Disposition", "attachment; filename=" + doc.getName())
                .build();
    }

}
