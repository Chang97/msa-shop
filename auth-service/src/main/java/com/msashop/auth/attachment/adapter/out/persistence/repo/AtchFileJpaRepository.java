package com.msashop.auth.attachment.adapter.out.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msashop.auth.attachment.adapter.out.persistence.entity.AtchFileEntity;

public interface AtchFileJpaRepository extends JpaRepository<AtchFileEntity, Long> {
}
