package com.CustomerMs.CustomerMs.business.service.impl;

import com.CustomerMs.CustomerMs.Model.Utils.ClienteMapper;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteRequest;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteResponse;
import com.CustomerMs.CustomerMs.Model.entity.ClienteEntity;
import com.CustomerMs.CustomerMs.business.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Spy
    private ClienteMapper clienteMapper = new ClienteMapper();

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCliente_Exitoso() {
        ClienteRequest request = ClienteRequest.builder()
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .email("juan.perez@example.com")
                .build();

        ClienteEntity entidadGuardada = ClienteEntity.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .email("juan.perez@example.com")
                .build();

        // Simulamos que el repositorio guarda y retorna la entidad
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(Mono.just(entidadGuardada));

        Mono<ClienteResponse> resultado = clienteService.crearCliente(request);

        StepVerifier.create(resultado)
                .expectNextMatches(response ->
                        response.getId().equals(1L) &&
                                response.getNombre().equals("Juan") &&
                                response.getApellido().equals("Perez") &&
                                response.getDni().equals("12345678") &&
                                response.getEmail().equals("juan.perez@example.com")
                )
                .verifyComplete();

        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void obtenerCliente_Existe() {
        Long clienteId = 1L;

        ClienteEntity entidad = ClienteEntity.builder()
                .id(clienteId)
                .nombre("Ana")
                .apellido("Lopez")
                .dni("87654321")
                .email("ana.lopez@example.com")
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Mono.just(entidad));

        Mono<ClienteResponse> resultado = clienteService.obtenerCliente(clienteId);

        StepVerifier.create(resultado)
                .expectNextMatches(response ->
                        response.getId().equals(clienteId) &&
                                response.getNombre().equals("Ana") &&
                                response.getApellido().equals("Lopez")
                )
                .verifyComplete();

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void obtenerCliente_NoExiste_Error() {
        Long clienteId = 99L;

        when(clienteRepository.findById(clienteId)).thenReturn(Mono.empty());

        Mono<ClienteResponse> resultado = clienteService.obtenerCliente(clienteId);

        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Cliente no encontrado"))
                .verify();

        verify(clienteRepository, times(1)).findById(clienteId);
    }
}