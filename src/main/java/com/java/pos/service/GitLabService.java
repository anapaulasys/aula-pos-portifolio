package com.java.pos.service;

import com.java.pos.model.dto.Projeto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@RegisterRestClient
@Path("/api/v4")
public interface GitLabService {

    @GET
    @Path("/users/{user}/projects")
    List<Projeto> getAllByUser(@PathParam("user") String user, @QueryParam("private_token") String privateToken);
}
