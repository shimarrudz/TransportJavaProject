package br.com.fiap.domain.resources;


import br.com.fiap.Main;
import br.com.fiap.domain.dto.EnderecoDTO;
import br.com.fiap.domain.entity.Endereco;
import br.com.fiap.domain.service.EnderecoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Context
    private UriInfo uriInfo;

    private EnderecoService service = EnderecoService.build( Main.PERSISTENCE_UNIT );

    @GET
    @Path("/{cep}")
    public Response findByCEP(@PathParam("cep") String cep) {
        Endereco endereco = service.findByCEP( cep );
        if (Objects.isNull( endereco )) return Response.status( 404 ).build();
        return Response.ok( endereco ).build();
    }

    @GET
    public Response findByLogradouro(
            @QueryParam("uf") String uf,
            @QueryParam("cidade") String cidade,
            @QueryParam("logradouro") String logradouro
    ) {

        List<Endereco> enderecos = service.findByLogradouro( uf, cidade, logradouro );
        if (Objects.isNull( enderecos )) return Response.status( 404 ).build();
        return Response.ok( enderecos ).build();
    }


    @POST
    public Response persist(EnderecoDTO dto) {

        var endereco = EnderecoDTO.of(dto);

        Endereco persisted = service.persist( endereco );

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path( String.valueOf( persisted.getCep() ) ).build();


        if (Objects.isNull( persisted )) return Response.status( 400 ).build();
        return Response.created( uri ).entity( EnderecoDTO.of( persisted ) ).build();
    }

}
