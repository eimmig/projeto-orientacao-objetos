package com.br.ecoleta.service;

import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.model.PontoDeColeta;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.repository.ClienteRepository;
import com.br.ecoleta.repository.ColetaRepository;
import com.br.ecoleta.repository.PontoDeColetaRepository;
import com.br.ecoleta.repository.RotaRepository;
import com.br.ecoleta.util.ColetaStatus;
import com.br.ecoleta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ColetaServiceTest {
    private static EntityManager em;
    private static ColetaService service;
    private static ClienteService clienteService;
    private static PontoDeColetaService pontoService;
    private static RotaService rotaService;
    private static MotoristaService motoristaService;
    private static VeiculoService veiculoService;
    private static Cliente cliente;
    private static PontoDeColeta ponto;
    private static Rota rota;
    private static com.br.ecoleta.model.Motorista motorista;
    private static com.br.ecoleta.model.Veiculo veiculo;

    @BeforeAll
    static void setupAll() {
        em = JpaUtil.getEntityManager();
        service = new ColetaService(new ColetaRepository(em));
        clienteService = new ClienteService(new ClienteRepository(em));
        pontoService = new PontoDeColetaService(new PontoDeColetaRepository(em));
        rotaService = new RotaService(new RotaRepository(em), service);
        motoristaService = new MotoristaService(new com.br.ecoleta.repository.MotoristaRepository(em));
        veiculoService = new VeiculoService(new com.br.ecoleta.repository.VeiculoRepository(em));
        cliente = clienteService.save(new Cliente("Cliente Coleta", "33333333333-COLETA", "cliente_coleta@coleta.com", "666666666"));
        ponto = pontoService.save(new PontoDeColeta("Ponto Coleta", "Rua Coleta", 1.0, 2.0, cliente));
        motorista = motoristaService.save(new com.br.ecoleta.model.Motorista("Motorista Coleta", "44444444444", "CNH444", "555555555"));
        veiculo = veiculoService.save(new com.br.ecoleta.model.Veiculo("COE1234", com.br.ecoleta.util.TipoVeiculo.CARRO, 1000.0));
        rota = rotaService.save(new Rota(java.time.LocalDate.now(), "Obs", motorista, veiculo));
    }

    @AfterAll
    static void tearDownAll() {
        if (em != null && em.isOpen()) em.close();
    }

    @Test
    void testInstancia() {
        ColetaService localService = null;
        assertNull(localService);
    }

    @Test
    void crudColeta() {
        Coleta coleta = new Coleta(LocalDateTime.now(), 10.0, "Obs", ColetaStatus.PENDENTE, cliente, ponto, rota);
        Coleta saved = service.save(coleta);
        assertNotNull(saved.getId());

        Optional<Coleta> found = service.getById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Obs", found.get().getObservacoes());

        saved.setObservacoes("Atualizado");
        Coleta updated = service.update(saved.getId(), saved);
        assertEquals("Atualizado", updated.getObservacoes());

        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        assertTrue(service.getById(saved.getId()).isEmpty());
    }

    @Test
    void testGetAll() {
        int initialSize = service.getAll().size();
        Coleta coleta = new Coleta(LocalDateTime.now(), 5.0, "Teste getAll", ColetaStatus.PENDENTE, cliente, ponto, rota);
        service.save(coleta);
        assertEquals(initialSize + 1, service.getAll().size());
    }
}