package com.br.ecoleta.service;

import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.repository.VeiculoRepository;
import com.br.ecoleta.util.JpaUtil;
import com.br.ecoleta.util.TipoVeiculo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class VeiculoServiceTest {
    private static EntityManager em;
    private static VeiculoService service;

    @BeforeAll
    static void setupAll() {
        em = JpaUtil.getEntityManager();
        service = new VeiculoService(new VeiculoRepository(em));
    }

    @AfterAll
    static void tearDownAll() {
        if (em != null && em.isOpen()) em.close();
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    void crudVeiculo() {
        Veiculo veiculo = new Veiculo("ABC1234", TipoVeiculo.CARRO, 1000.0);
        Veiculo saved = service.save(veiculo);
        assertNotNull(saved.getId());

        Optional<Veiculo> found = service.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("ABC1234", found.get().getPlaca());

        saved.setPlaca("XYZ9876");
        Veiculo updated = service.update(saved.getId(), saved);
        assertEquals("XYZ9876", updated.getPlaca());

        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(service.getById(saved.getId()).isEmpty());
    }

    @Test
    void testGetAll() {
        int initialSize = service.getAll().size();
        Veiculo veiculo = new Veiculo("GETALL123", com.br.ecoleta.util.TipoVeiculo.CARRO, 2000.0);
        service.save(veiculo);
        assertEquals(initialSize + 1, service.getAll().size());
    }
}