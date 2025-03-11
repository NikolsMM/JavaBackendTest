package com.nikols.mapper;

import com.nikols.models.entities.Product;
import com.nikols.models.requests.ProductRequest;
import com.nikols.models.responses.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Transformamos el customer |--> customer.customerId porque solo nos importa eso.
    @Mapping(source = "customer.customerId", target = "customerId")
    ProductResponse toResponse(Product product);

    // Convertimos ProductRequest a Product, sin necesidad de mapear customerId
    @Mapping(target = "productId", ignore = true) // Lo ignoramos porque el ID lo genera la BD
    Product toEntity(ProductRequest request);
}
