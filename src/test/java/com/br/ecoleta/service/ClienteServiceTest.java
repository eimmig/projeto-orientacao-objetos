package com.br.ecoleta.service;

import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.repository.ClienteRepository;
import com.br.ecoleta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {
    private static EntityManager em;
    private static ClienteService service;

    @BeforeAll
    static void setupAll() {
        em = JpaUtil.getEntityManager();
        service = new ClienteService(new ClienteRepository(em));
    }

    @AfterAll
    static void tearDownAll() {
        if (em != null && em.isOpen()) em.close();
    }

    @Test
    void crudCliente() {
        Cliente cliente = new Cliente("Teste", "12345678900-CLIENTE", "teste_cliente@email.com", "999999999");
        Cliente saved = service.save(cliente);
        assertNotNull(saved.getId());

        Optional<Cliente> found = service.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Teste", found.get().getNome());

        saved.setNome("Atualizado");
        Cliente updated = service.update(saved.getId(), saved);
        assertEquals("Atualizado", updated.getNome());

        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(service.getById(saved.getId()).isEmpty());
    }

    @Test
    void testGetAll() {
        int initialSize = service.getAll().size();
        Cliente cliente = new Cliente("Teste getAll", "99999999999", "getall@email.com", "111111111");
        service.save(cliente);
        assertEquals(initialSize + 1, service.getAll().size());
    }
}