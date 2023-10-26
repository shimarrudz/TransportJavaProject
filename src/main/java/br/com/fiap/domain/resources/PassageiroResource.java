package br.com.fiap.domain.resources;

import br.com.fiap.Main;
import br.com.fiap.domain.dto.PassageiroDTO;
import br.com.fiap.domain.entity.Passageiro;
import br.com.fiap.domain.service.PassageiroService;
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

@Path("/passageiro")
public class PassageiroResource implements Resource<PassageiroDTO, Long> {

    @Context
    private UriInfo uriInfo;

    PassageiroService service = PassageiroService.build(Main.PERSISTENCE_UNIT);

    @GET
    @Override
    public Response findAll() {
        return Response.ok(service.findAll().stream().map(PassageiroDTO::of).toList()).build();
    }

    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {

        Passageiro passageiro = service.findById(id);

        if (Objects.isNull(passageiro)) return Response.status(404).build();

        return Response.ok(PassageiroDTO.of(passageiro)).build();


    }

    @POST
    @Override
    public Response persist(PassageiroDTO passageiro) {

        var p = PassageiroDTO.of(passageiro);

        Passageiro persisted = service.persist(p);

        if (Objects.isNull(persisted)) return Response.status(400).build();

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path(String.valueOf(persisted.getId())).build();

        return Response.created(uri).entity(PassageiroDTO.of(persisted)).build();
    }

}
