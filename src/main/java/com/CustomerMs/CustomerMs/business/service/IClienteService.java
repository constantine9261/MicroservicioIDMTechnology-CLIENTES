package com.CustomerMs.CustomerMs.business.service;



import com.CustomerMs.CustomerMs.Model.api.customer.ClienteRequest;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteResponse;
import reactor.core.publisher.Mono;

public interface IClienteService {

    Mono<ClienteResponse> crearCliente(ClienteRequest clienteRequest);

    Mono<ClienteResponse> obtenerCliente(Long  id);

}