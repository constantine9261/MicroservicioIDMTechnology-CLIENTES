package com.CustomerMs.CustomerMs.controller;

import com.CustomerMs.CustomerMs.Model.api.customer.ClienteResponse;
import com.CustomerMs.CustomerMs.business.service.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;


class ClienteControllerTest {

    @Mock
    private IClienteService clienteService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        ClienteController controller = new ClienteController(clienteService);
        // Crea WebTestClient solo con el controlador (standalone, sin contexto Spring)
        this.webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void testObtenerCliente_ok() {
        ClienteResponse response = new ClienteResponse();
        response.setId(1L);
        response.setNombre("Juan Franco");

        // Define comportamiento del mock
        when(clienteService.obtenerCliente(1L)).thenReturn(Mono.just(response));

        webTestClient.get()
                .uri("/clientes/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.nombre").isEqualTo("Juan Franco");
    }

    @Test
    void testObtenerCliente_noEncontrado() {
        when(clienteService.obtenerCliente(2L)).thenReturn(Mono.error(new RuntimeException("Cliente no encontrado")));

        webTestClient.get()
                .uri("/clientes/2")
                .exchange()
                .expectStatus().isOk()  // Aquí responde 200 pero con error en el payload
                .expectBody()
                .jsonPath("$.message").isEqualTo("Error al procesar la solicitud");
    }
}