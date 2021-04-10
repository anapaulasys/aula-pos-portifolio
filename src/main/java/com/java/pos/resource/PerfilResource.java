package com.java.pos.resource;

import com.java.pos.model.Perfil;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("api/perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {


    @GET
    public Response getOne() {
        try {
            return Response.ok(Perfil.listAll().get(0)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @POST
    @Transactional
    public Response createOrUpdate(Perfil perfil) {
        if (perfil.id != null) {
            Perfil.update("nome = ?1, telefone = ?2, email = ?3, bio = ?4 where id = ?5",
                    perfil.getNome(), perfil.getTelefone(), perfil.getEmail(), perfil.getBio(), perfil.id);
        } else {
            perfil.persist();
        }


        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
