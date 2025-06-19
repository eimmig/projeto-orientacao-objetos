package com.br.ecoleta.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JpaUtilShutdownTest {
    @AfterAll
    void shutdown() {
        JpaUtil.closeEntityManagerFactory();
    }
}
