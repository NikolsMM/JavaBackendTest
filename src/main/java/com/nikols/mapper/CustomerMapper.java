package com.nikols.mapper;

import com.nikols.models.entities.Customer;
import com.nikols.models.requests.CustomerRequest;
import com.nikols.models.responses.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "products", source = "products") // Mapea la lista de productos
    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "customerId", ignore = true) // Lo ignoramos porque el ID lo genera la BD
    Customer toEntity(CustomerRequest request);
}

