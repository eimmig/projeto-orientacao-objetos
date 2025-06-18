package com.br.ecoleta.service;

import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.repository.MotoristaRepository;
import com.br.ecoleta.repository.RotaRepository;
import com.br.ecoleta.repository.VeiculoRepository;
import com.br.ecoleta.repository.ColetaRepository;
import com.br.ecoleta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class RotaServiceTest {
    private static EntityManager em;
    private static RotaService service;
    private static MotoristaService motoristaService;
    private static VeiculoService veiculoService;
    private static ColetaService coletaService;
    private static Motorista motorista;
    private static Veiculo veiculo;

    @BeforeAll
    static void setupAll() {
        em = JpaUtil.getEntityManager();
        motoristaService = new MotoristaService(new MotoristaRepository(em));
        veiculoService = new VeiculoService(new VeiculoRepository(em));
        coletaService = new ColetaService(new ColetaRepository(em));
        service = new RotaService(new RotaRepository(em), coletaService);
        motorista = motoristaService.save(new Motorista("Motorista Rota", "22222222222", "CNH222", "777777777"));
        veiculo = veiculoService.save(new Veiculo("ROT1234", com.br.ecoleta.util.TipoVeiculo.CARRO, 500.0));
    }

    @AfterAll
    static void tearDownAll() {
        if (em != null && em.isOpen()) em.close();
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    void crudRota() {
        Rota rota = new Rota(LocalDate.now(), "Obs", motorista, veiculo);
        Rota saved = service.save(rota);
        assertNotNull(saved.getId());

        Optional<Rota> found = service.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Obs", found.get().getObservacoes());

        saved.setObservacoes("Atualizado");
        Rota updated = service.update(saved.getId(), saved);
        assertEquals("Atualizado", updated.getObservacoes());

        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(service.getById(saved.getId()).isEmpty());
    }

    @Test
    void testGetAll() {
        int initialSize = service.getAll().size();
        Rota rota = new Rota(java.time.LocalDate.now(), "Obs getAll", motorista, veiculo);
        service.save(rota);
        assertEquals(initialSize + 1, service.getAll().size());
    }

    @Test
    void testCalcularRotaParaMotorista() {
        com.br.ecoleta.model.Coleta coleta = new com.br.ecoleta.model.Coleta(
                java.time.LocalDateTime.now(),
                100.0,
                "Coleta para rota",
                com.br.ecoleta.util.ColetaStatus.PENDENTE,
                null,
                null,
                null
        );
        coletaService.save(coleta);

        Rota rotaCalculada = service.calcularRotaParaMotorista(motorista, veiculo);
        assertNotNull(rotaCalculada, "A rota calculada não deve ser nula se houver coleta pendente");
        assertNotNull(rotaCalculada.getId(), "A rota calculada deve ser persistida e ter ID");
        assertEquals(motorista, rotaCalculada.getMotorista());
        assertEquals(veiculo, rotaCalculada.getVeiculo());
        assertFalse(rotaCalculada.getColetas().isEmpty(), "A rota deve conter coletas associadas");
    }
}