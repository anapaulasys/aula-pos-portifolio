package com.java.pos.resource;

import com.java.pos.model.Habilidade;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/habilidade")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HabilidadeResource {

    @POST
    @Transactional
    public Response create(Habilidade habilidade) {
        habilidade.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    public Response update(Habilidade habilidade) {
        if (habilidade.id == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Habilidade.update("nome = ?1, descricao = ?2 where id = ?3",
                habilidade.getNome(), habilidade.getDescricao(), habilidade.id);
        return Response.ok().build();
    }

    @GET
    public Response getAll() {
        return Response.ok(Habilidade.listAll()).build();
    }
}
