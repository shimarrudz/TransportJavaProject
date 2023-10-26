package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Pacote;
import br.com.fiap.domain.repository.PacoteRepository;
import br.com.fiap.infra.database.EntityManagerFactoryProvider;
import br.com.fiap.infra.security.service.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PacoteService implements Service<Pacote, Long> {

    private static final AtomicReference<PacoteService> instance = new AtomicReference<>();

    private final PacoteRepository repo;


    private PacoteService(PacoteRepository repo) {
        this.repo = repo;
    }

    public static PacoteService build(String persistenceUnit) {

        EntityManagerFactory factory = EntityManagerFactoryProvider.of(persistenceUnit).provide();
        EntityManager manager = factory.createEntityManager();
        PacoteRepository repo = PacoteRepository.build(manager);

        instance.compareAndSet(null, new PacoteService(repo));
        return instance.get();
    }

    @Override
    public List<Pacote> findAll() {
        return repo.findAll();
    }

    @Override
    public Pacote findById(Long id) {
        return repo.findById(id);
    }


    @Override
    public Pacote persist(Pacote Pacote) {
        return repo.persist(Pacote);
    }
}
