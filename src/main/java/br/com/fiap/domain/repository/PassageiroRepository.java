package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Passageiro;
import br.com.fiap.infra.security.repository.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PassageiroRepository implements Repository<Passageiro, Long> {

    private static final AtomicReference<PassageiroRepository> instance = new AtomicReference<>();

    private final EntityManager manager;

    private PassageiroRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static PassageiroRepository build(EntityManager manager) {
        instance.compareAndSet(null, new PassageiroRepository(manager));
        return instance.get();
    }

    @Override
    public List<Passageiro> findAll() {
        return manager.createQuery("From Passageiro").getResultList();
    }

    @Override
    public Passageiro findById(Long id) {
        return manager.find(Passageiro.class, id);
    }

    public List<Passageiro> findByName(String texto) {
        Query query = manager.createQuery("From Passageiro p where upper(p.pessoa.nome) like :texto");
        query.setParameter("texto", "%" + texto.trim().toUpperCase() + "%");
        return query.getResultList();
    }

    @Override
    public Passageiro persist(Passageiro passageiro) {

        manager.getTransaction().begin();
        manager.merge(passageiro);
        manager.getTransaction().commit();
        System.out.println(passageiro);
        return passageiro;
    }
}
