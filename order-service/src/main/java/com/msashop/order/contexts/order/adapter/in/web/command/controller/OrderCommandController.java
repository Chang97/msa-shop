package com.msashop.order.contexts.order.adapter.in.web.command.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.order.contexts.order.adapter.in.web.command.dto.IdResponse;
import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.contexts.order.application.command.port.in.CreateOrderUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCommandController {
    
    private final CreateOrderUseCase create; 

    @PostMapping 
    public ResponseEntity<?> create(@RequestBody CreateOrderCommand cmd) { 
        long id = create.handle(cmd); 
        return ResponseEntity.ok(new IdResponse(id)); 
    }
}
