package com.java.pos.resource;

import com.java.pos.model.dto.Projeto;
import com.java.pos.service.GitLabService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("api/projeto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjetoResource {

    @Inject
    @RestClient
    GitLabService gitLabService;

    @GET
    public Response getAll() {
        List<Projeto> projetos = gitLabService.getAllByUser("gustavo.scarpini", "JDKhLWsfasEUngVsAqqF");
        return Response.ok(projetos).build();
    }
}
