package br.com.fiap.domain.resources;

import br.com.fiap.Main;
import br.com.fiap.domain.dto.ViagemDTO;
import br.com.fiap.domain.entity.Viagem;
import br.com.fiap.domain.service.ViagemService;
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

@Path("/viagem")
public class ViagemResource implements Resource<ViagemDTO, Long> {

    @Context
    private UriInfo uriInfo;

    private ViagemService service = ViagemService.build(Main.PERSISTENCE_UNIT);


    @GET
    @Override
    public Response findAll() {
        return Response.ok( service.findAll().stream().map(ViagemDTO::of).toList()).build();
    }


    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {

        Viagem viagem = service.findById(id);

        if (Objects.isNull(viagem)) return Response.status(404).build();

        return Response.ok(viagem).build();

    }

    @POST
    @Override
    public Response persist(ViagemDTO v) {

        var viagem = ViagemDTO.of(v);

        Viagem persisted = service.persist(viagem);

        if (Objects.isNull(persisted)) return Response.status(400).build();

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path(String.valueOf(persisted.getId())).build();

        return Response.created(uri).entity(ViagemDTO.of(persisted)).build();
    }

}
