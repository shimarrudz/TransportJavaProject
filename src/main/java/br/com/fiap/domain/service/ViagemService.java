package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Viagem;
import br.com.fiap.domain.repository.ViagemRepository;
import br.com.fiap.infra.database.EntityManagerFactoryProvider;
import br.com.fiap.infra.security.service.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ViagemService implements Service<Viagem, Long> {

    private static final AtomicReference<ViagemService> instance = new AtomicReference<>();

    private final ViagemRepository repo;


    private ViagemService(ViagemRepository repo) {
        this.repo = repo;
    }

    public static ViagemService build(String persistenceUnit) {

        EntityManagerFactory factory = EntityManagerFactoryProvider.of( persistenceUnit ).provide();
        EntityManager manager = factory.createEntityManager();
        ViagemRepository repo = ViagemRepository.build( manager );

        instance.compareAndSet( null, new ViagemService( repo ) );
        return instance.get();
    }

    @Override
    public List<Viagem> findAll() {
        return repo.findAll();
    }

    @Override
    public Viagem findById(Long id) {
         return repo.findById( id );
    }

    @Override
    public Viagem persist(Viagem viagem) {
        return repo.persist(viagem);
    }
}
