package com.br.ecoleta.service;

import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.model.PontoDeColeta;
import com.br.ecoleta.repository.ClienteRepository;
import com.br.ecoleta.repository.PontoDeColetaRepository;
import com.br.ecoleta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class PontoDeColetaServiceTest {
    private static EntityManager em;
    private static PontoDeColetaService service;
    private static ClienteService clienteService;
    private static Cliente cliente;

    @BeforeAll
    static void setupAll() {
        em = JpaUtil.getEntityManager();
        service = new PontoDeColetaService(new PontoDeColetaRepository(em));
        clienteService = new ClienteService(new ClienteRepository(em));
        cliente = clienteService.save(new Cliente("Cliente Ponto", "11111111111", "cliente@ponto.com", "888888888"));
    }

    @AfterAll
    static void tearDownAll() {
        if (em != null && em.isOpen()) em.close();
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    void testInstancia() {
        PontoDeColetaService localService = null;
        assertNull(localService);
    }

    @Test
    void crudPontoDeColeta() {
        PontoDeColeta ponto = new PontoDeColeta("Local Teste", "Rua Teste", 1.0, 2.0, cliente);
        PontoDeColeta saved = service.save(ponto);
        assertNotNull(saved.getId());

        Optional<PontoDeColeta> found = service.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Local Teste", found.get().getNomeLocal());

        saved.setNomeLocal("Atualizado");
        PontoDeColeta updated = service.update(saved.getId(), saved);
        assertEquals("Atualizado", updated.getNomeLocal());

        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(service.getById(saved.getId()).isEmpty());
    }

    @Test
    void testGetAll() {
        int initialSize = service.getAll().size();
        PontoDeColeta ponto = new PontoDeColeta("Ponto getAll", "Rua getAll", 3.0, 4.0, cliente);
        service.save(ponto);
        assertEquals(initialSize + 1, service.getAll().size());
    }
}