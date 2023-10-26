package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Viagem;
import br.com.fiap.infra.security.repository.Repository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ViagemRepository implements Repository<Viagem, Long> {

    private static final AtomicReference<ViagemRepository> instance = new AtomicReference<>();

    private final EntityManager manager;

    private ViagemRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static ViagemRepository build(EntityManager manager) {
        instance.compareAndSet(null, new ViagemRepository(manager));
        return instance.get();
    }

    @Override
    public List<Viagem> findAll() {
        return manager.createQuery("From Viagem").getResultList();
    }

    @Override
    public Viagem findById(Long id) {
        return manager.find(Viagem.class, id);
    }


    @Override
    public Viagem persist(Viagem viagem) {
        manager.getTransaction().begin();
        manager.merge(viagem);
        manager.getTransaction().commit();
        return viagem;
    }
}
