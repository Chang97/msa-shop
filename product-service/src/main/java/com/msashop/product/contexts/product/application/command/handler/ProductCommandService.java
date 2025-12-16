package com.msashop.product.contexts.product.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.product.contexts.product.application.command.dto.ChangeStockCommand;
import com.msashop.product.contexts.product.application.command.dto.CreateProductCommand;
import com.msashop.product.contexts.product.application.command.dto.CreateProductResult;
import com.msashop.product.contexts.product.application.command.dto.ProductDetailResult;
import com.msashop.product.contexts.product.application.command.mapper.ProductCommandMapper;
import com.msashop.product.contexts.product.application.command.port.in.ChangeStockUseCase;
import com.msashop.product.contexts.product.application.command.port.in.CreateProductUseCase;
import com.msashop.product.contexts.product.domain.model.Product;
import com.msashop.product.contexts.product.domain.model.exception.InvalidProductStateException;
import com.msashop.product.contexts.product.domain.model.exception.ProductNotFoundException;
import com.msashop.product.contexts.product.domain.port.out.ProductPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService implements CreateProductUseCase, ChangeStockUseCase {

    private final ProductPersistencePort productPersistencePort;
    private final ProductCommandMapper mapper;

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
    public ProductDetailResult handle(ChangeStockCommand command) {
        Product current = productPersistencePort.findById(command.productId())
                .orElseThrow(() -> new ProductNotFoundException(command.productId()));

        int nextStock = current.getStock() + command.delta();
        if (nextStock < 0) {
            throw new InvalidProductStateException("Stock cannot become negative.");
        }
        Product updated = current.withStock(nextStock);
        Product saved = productPersistencePort.save(updated);
        return mapper.toDetailResult(saved);
    }
}
