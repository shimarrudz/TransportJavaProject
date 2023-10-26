package br.com.fiap.domain.service;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Endereco;
import br.com.fiap.domain.repository.EnderecoRepository;
import br.com.fiap.infra.configuration.datas.LocalDateTimeTypeAdapter;
import br.com.fiap.infra.configuration.datas.LocalDateTypeAdapter;
import br.com.fiap.infra.configuration.datas.ZonedDateTimeTypeAdapter;
import br.com.fiap.infra.database.EntityManagerFactoryProvider;
import br.com.fiap.infra.security.service.PessoaFisicaService;
import br.com.fiap.infra.security.service.PessoaJuridicaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class EnderecoService {

    private final String FONTE = "http://viacep.com.br/ws/";

    private static final AtomicReference<EnderecoService> instance = new AtomicReference<>();

    private final EnderecoRepository repo;


    private EnderecoService(EnderecoRepository repo) {
        this.repo = repo;
    }

    public static EnderecoService build(String persistenceUnit) {

        EntityManagerFactory factory = EntityManagerFactoryProvider.of( persistenceUnit ).provide();
        EntityManager manager = factory.createEntityManager();
        EnderecoRepository repo = EnderecoRepository.build( manager );

        instance.compareAndSet( null, new EnderecoService( repo ) );
        return instance.get();
    }


    private PessoaFisicaService pfService = PessoaFisicaService.of( Main.PERSISTENCE_UNIT );
    private PessoaJuridicaService pjService = PessoaJuridicaService.of( Main.PERSISTENCE_UNIT );


    public Endereco findByCEP(String cep) {

        Endereco endereco = null;

        URI uri = null;

        HttpResponse<String> response = null;


        try {

            uri = new URI( this.FONTE + cep + "/json" );

            var cliente = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder( uri ).GET().build();

            response = cliente.send( request, HttpResponse.BodyHandlers.ofString() );

            if (response.statusCode() != 200) {
                return null;
            }

            var body = response.body();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter( LocalDate.class, new LocalDateTypeAdapter() )
                    .registerTypeAdapter( LocalDateTime.class, new LocalDateTimeTypeAdapter() )
                    .registerTypeAdapter( ZonedDateTime.class, new ZonedDateTimeTypeAdapter() )
                    .create();


            endereco = gson.fromJson( body, Endereco.class );

        } catch (URISyntaxException e) {
            throw new RuntimeException( e );
        } catch (IOException e) {
            throw new RuntimeException( e );
        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }

        return endereco;

    }


    public List<Endereco> findByLogradouro(String uf, String cidade, String logradouro) {

        List<Endereco> enderecos = null;

        URI uri = null;

        HttpResponse<String> response = null;


        var value = this.FONTE + uf + "/" + encode( cidade ) + "/" + encode( logradouro ) + "/json";


        try {
            uri = new URI( value );

            var cliente = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder( URI.create( uri.toASCIIString() ) ).GET().build();

            response = cliente.send( request, HttpResponse.BodyHandlers.ofString() );

            if (response.statusCode() != 200) {
                return null;
            }

            var body = response.body();

            Type tipo = new TypeToken<List<Endereco>>() {
            }.getType();


            Gson gson = new GsonBuilder()
                    .registerTypeAdapter( LocalDate.class, new LocalDateTypeAdapter() )
                    .create();

            enderecos = gson.fromJson( body, tipo );

        } catch (URISyntaxException e) {
            throw new RuntimeException( e );
        } catch (IOException e) {
            throw new RuntimeException( e );
        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }

        return enderecos;
    }

    public Endereco persist(Endereco endereco) {

//        Pessoa pessoa = null;
//
//        pessoa = pfService.findById( endereco.pessoa().id() );
//
//        if (Objects.isNull( pessoa )) {
//            pessoa = pjService.findById( endereco.pessoa().id() );
//        }
//
//        if (Objects.isNull( pessoa )) return null;

//        Endereco end = findByCEP( endereco.getCep() );
//
//        if(Objects.isNull( end )|| Objects.isNull( end.getCep() )) return null;
//
//
//        end.setPessoa( pessoa );
//
//        end.setComplemento( endereco.complemento() );
//        end.setNumero( endereco.numero() );
        return repo.persist( endereco );
    }


    public static String encode(String value) {
        return value.replace( " ", "%20" ).replace( "#", "%23" );
    }

}
