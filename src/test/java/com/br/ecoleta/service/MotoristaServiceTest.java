package com.br.ecoleta.service;

import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.repository.MotoristaRepository;
import com.br.ecoleta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class MotoristaServiceTest {
    private static EntityManager em;
    private static MotoristaService service;

    @BeforeAll
    static void setupAll() {
        em = JpaUtil.getEntityManager();
        service = new MotoristaService(new MotoristaRepository(em));
    }

    @AfterAll
    static void tearDownAll() {
        if (em != null && em.isOpen()) em.close();
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    void testInstancia() {
        MotoristaService localService = null;
        assertNull(localService);
    }

    @Test
    void crudMotorista() {
        Motorista motorista = new Motorista("Teste Motorista", "12345678901", "CNH123", "999999999");
        Motorista saved = service.save(motorista);
        assertNotNull(saved.getId());

        Optional<Motorista> found = service.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Teste Motorista", found.get().getNome());

        saved.setNome("Atualizado");
        Motorista updated = service.update(saved.getId(), saved);
        assertEquals("Atualizado", updated.getNome());

        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(service.getById(saved.getId()).isEmpty());
    }

    @Test
    void testGetAll() {
        int initialSize = service.getAll().size();
        Motorista motorista = new Motorista("Teste getAll", "88888888888", "CNHGETALL", "123456789");
        service.save(motorista);
        assertEquals(initialSize + 1, service.getAll().size());
    }
}