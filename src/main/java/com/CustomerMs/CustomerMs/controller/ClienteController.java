package com.CustomerMs.CustomerMs.controller;



import com.CustomerMs.CustomerMs.Model.api.customer.ClienteRequest;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteResponse;
import com.CustomerMs.CustomerMs.Model.api.shared.ResponseDto;
import com.CustomerMs.CustomerMs.Model.api.shared.ResponseDtoBuilder;
import com.CustomerMs.CustomerMs.business.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final IClienteService clienteService;

    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Mono<ResponseDto<ClienteResponse>> crear(@RequestBody Mono<ClienteRequest> request) {
        return request
                .flatMap(clienteService::crearCliente) // devuelve Mono<ClienteResponse>
                .map(result -> ResponseDtoBuilder.success(result, "Cliente creado con éxito"))
                .onErrorResume(ex -> {
                    log.error("Error al crear cliente: {}", ex.getMessage(), ex);
                    return Mono.just(ResponseDtoBuilder.error("Error al procesar la creación del cliente"));
                });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene la información de un cliente registrado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Mono<ResponseDto<ClienteResponse>> obtener(@PathVariable Long id) {
        return clienteService.obtenerCliente(id) // devuelve Mono<ClienteResponse>
                .map(result -> ResponseDtoBuilder.success(result, "Cliente encontrado"))
                .switchIfEmpty(Mono.just(ResponseDtoBuilder.error("Cliente no encontrado")))
                .onErrorResume(ex -> {
                    log.error("Error al obtener cliente: {}", ex.getMessage(), ex);
                    return Mono.just(ResponseDtoBuilder.error("Error al procesar la solicitud"));
                });
    }
}
