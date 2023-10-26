package br.com.fiap.domain.resources;

import br.com.fiap.Main;
import br.com.fiap.domain.dto.PacoteDTO;
import br.com.fiap.domain.entity.Pacote;
import br.com.fiap.domain.entity.dto.PacoteDTO;
import br.com.fiap.domain.entity.service.PacoteService;
import br.com.fiap.domain.service.PacoteService;
import br.com.fiap.infra.security.resources.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Objects;

@Path("/Pacote")
public class PacoteResource implements Resource<PacoteDTO, Long> {

    @Context
    private UriInfo uriInfo;

    PacoteService service = PacoteService.build(Main.PERSISTENCE_UNIT);


    @GET
    @Override
    public Response findAll() {
        return Response.ok(service.findAll().stream().map(PacoteDTO::of).toList()).build();
    }


    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {

        Pacote Pacote = service.findById(id);

        if (Objects.isNull(Pacote)) return Response.status(404).build();

        return Response.ok(PacoteDTO.of(Pacote)).build();


    }

    @POST
    @Override
    public Response persist(Pacote Pacote) {
        Pacote persisted = service.persist(Pacote);

        if (Objects.isNull(persisted)) return Response.status(400).build();

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path(String.valueOf(persisted.getId())).build();

        return Response.created(uri).entity(PacoteDTO.of(persisted)).build();
    }

}
