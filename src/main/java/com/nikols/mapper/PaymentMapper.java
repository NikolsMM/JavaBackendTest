package com.nikols.mapper;

import com.nikols.models.entities.Payment;
import com.nikols.models.requests.PaymentRequest;
import com.nikols.models.responses.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentResponse toResponse(Payment payment);

    @Mapping(target = "paymentId", ignore = true)
    Payment toEntity(PaymentRequest request);

}
