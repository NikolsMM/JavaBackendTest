package com.nikols.mapper;

import com.nikols.models.entities.Product;
import com.nikols.models.entities.Customer;
import com.nikols.models.requests.ProductRequest;
import com.nikols.models.responses.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    //Transformamos el customer |--> customer.customerId porque solo nos importa eso.
    @Mapping(source = "customer.customerId", target = "customerId")
    ProductResponse toResponse(Product product);

    //Ahora transformamos customerId |--> customer y lo asignamos a product,
    // a través del mapCustomerIdToCustomer, para manejar casos donde ese id no exita.
    @Mapping(target = "productId", ignore = true) // Lo ignoramos porque el ID lo genera la BD
    @Mapping(source = "customerId", target = "customer", qualifiedByName = "mapCustomerIdToCustomer")
    Product toEntity(ProductRequest request);

    @Named("mapCustomerIdToCustomer")
    default Customer mapCustomerIdToCustomer(Integer customerId) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        return customer;
    }
}
