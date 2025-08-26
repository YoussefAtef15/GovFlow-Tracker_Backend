package com.example.backend.govflow.service;

import com.example.backend.govflow.dto.PayableServiceDto;
import com.example.backend.govflow.dto.PaymentRecordDto;
import com.example.backend.govflow.entity.User;
import com.example.backend.govflow.repository.PaymentRepository;
import com.example.backend.govflow.repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    public List<PayableServiceDto> getPayableServicesForUser(User user) {
        return serviceRequestRepository.findByCitizenId(user.getId())
                .stream()
                .map(req -> new PayableServiceDto(
                        req.getId(),
                        req.getServiceName(),
                        new BigDecimal("250.00")
                ))
                .collect(Collectors.toList());
    }

    public List<PaymentRecordDto> getPaymentHistoryForUser(User user) {
        return paymentRepository.findByRequest_Citizen_IdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(payment -> new PaymentRecordDto(
                        payment.getId(),
                        payment.getRequest().getServiceName(),
                        payment.getAmount(),
                        payment.getCreatedAt().toLocalDate(),
                        payment.getPaymentStatus()
                ))
                .collect(Collectors.toList());
    }
}