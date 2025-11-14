package com.product_service.contexts.product.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product_service.contexts.product.application.command.dto.ChangeStockCommand;
import com.product_service.contexts.product.application.command.dto.CreateProductCommand;
import com.product_service.contexts.product.application.command.dto.CreateProductResult;
import com.product_service.contexts.product.application.command.port.in.ChangeStockUseCase;
import com.product_service.contexts.product.application.command.port.in.CreateProductUseCase;
import com.product_service.contexts.product.domain.model.Product;
import com.product_service.contexts.product.domain.model.exception.ProductNotFoundException;
import com.product_service.contexts.product.domain.port.out.ProductPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService implements CreateProductUseCase, ChangeStockUseCase {

    private final ProductPersistencePort productPersistencePort;

    @Override
    public CreateProductResult handle(CreateProductCommand command) {
        Product saved = productPersistencePort.save(Product.create(
                command.name(),
                command.price(),
                command.stock()
        ));
        return new CreateProductResult(saved.getId());
    }

    @Override
    public Product handle(ChangeStockCommand command) {
        Product current = productPersistencePort.findById(command.productId())
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        int nextStock = current.getStock() + command.delta();
        if (nextStock < 0) {
            throw new IllegalArgumentException("Stock cannot become negative.");
        }
        Product updated = current.withStock(nextStock);
        return productPersistencePort.save(updated);
    }
}
