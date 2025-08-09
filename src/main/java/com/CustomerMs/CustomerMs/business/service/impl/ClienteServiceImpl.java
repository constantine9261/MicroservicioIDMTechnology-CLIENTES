package com.CustomerMs.CustomerMs.business.service.impl;


import com.CustomerMs.CustomerMs.Model.Utils.ClienteMapper;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteRequest;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteResponse;
import com.CustomerMs.CustomerMs.business.repository.ClienteRepository;
import com.CustomerMs.CustomerMs.business.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper; // Convierte entre Entity y DTO

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Mono<ClienteResponse> crearCliente(ClienteRequest clienteRequest) {
        return Mono.just(clienteRequest)
                .map(clienteMapper::toEntity) // DTO -> Entity
                .flatMap(clienteRepository::save) // Guarda en DB
                .map(clienteMapper::toResponse); // Entity -> DTO Response
    }

    @Override
    public Mono<ClienteResponse> obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no encontrado")))
                .map(clienteMapper::toResponse);
    }
}
