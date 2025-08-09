package com.CustomerMs.CustomerMs.Model.Utils;

import com.CustomerMs.CustomerMs.Model.api.customer.ClienteRequest;
import com.CustomerMs.CustomerMs.Model.api.customer.ClienteResponse;
import com.CustomerMs.CustomerMs.Model.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteEntity toEntity(ClienteRequest request) {
        return ClienteEntity.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .email(request.getEmail())
                .build();
    }

    public ClienteResponse toResponse(ClienteEntity entity) {
        return ClienteResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .dni(entity.getDni())
                .email(entity.getEmail())
                .build();
    }
}
