package com.msashop.product.contexts.product.adapter.out.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.msashop.product.contexts.product.adapter.out.persistence.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}