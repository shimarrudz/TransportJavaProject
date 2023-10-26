package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Passageiro;
import br.com.fiap.domain.repository.PassageiroRepository;
import br.com.fiap.infra.database.EntityManagerFactoryProvider;
import br.com.fiap.infra.security.service.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PassageiroService implements Service<Passageiro, Long> {

    private static final AtomicReference<PassageiroService> instance = new AtomicReference<>();

    private final PassageiroRepository repo;


    private PassageiroService(PassageiroRepository repo) {
        this.repo = repo;
    }

    public static PassageiroService build(String persistenceUnit) {

        EntityManagerFactory factory = EntityManagerFactoryProvider.of( persistenceUnit ).provide();
        EntityManager manager = factory.createEntityManager();
        PassageiroRepository repo = PassageiroRepository.build( manager );

        instance.compareAndSet( null, new PassageiroService( repo ) );
        return instance.get();
    }

    @Override
    public List<Passageiro> findAll() {
        return repo.findAll();
    }

    @Override
    public Passageiro findById(Long id) {
        return repo.findById( id );
    }


    public List<Passageiro> findByName(String texto) {
        return repo.findByName( texto );
    }

    @Override
    public Passageiro persist(Passageiro passageiro) {
        return repo.persist( passageiro );
    }
}
