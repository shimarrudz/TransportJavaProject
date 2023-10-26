package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Pacote;
import br.com.fiap.infra.security.repository.Repository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PacoteRepository implements Repository<Pacote, Long> {

    private static final AtomicReference<PacoteRepository> instance = new AtomicReference<>();

    private final EntityManager manager;

    private PacoteRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static PacoteRepository build(EntityManager manager) {
        instance.compareAndSet(null, new PacoteRepository(manager));
        return instance.get();
    }

    @Override
    public List<Pacote> findAll() {
        return manager.createQuery("From Pacote").getResultList();
    }

    @Override
    public Pacote findById(Long id) {
        return manager.find(Pacote.class, id);
    }


    @Override
    public Pacote persist(Pacote Pacote) {
        manager.getTransaction().begin();
        manager.persist(Pacote);
        manager.getTransaction().commit();
        return Pacote;
    }
}
