package com.CustomerMs.CustomerMs.Model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("clientes")
public class ClienteEntity implements Serializable {
    @Id
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;

}
