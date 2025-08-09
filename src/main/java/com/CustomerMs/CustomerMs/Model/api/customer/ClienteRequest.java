package com.CustomerMs.CustomerMs.Model.api.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

    private String nombre;
    private String apellido;
    private String dni;
    private String email;
}
