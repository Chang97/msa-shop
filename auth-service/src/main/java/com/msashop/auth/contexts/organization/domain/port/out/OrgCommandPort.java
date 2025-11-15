package com.msashop.auth.contexts.organization.domain.port.out;

import java.util.Optional;

import com.msashop.auth.contexts.organization.domain.model.Org;

public interface OrgCommandPort {

    Org save(Org org);

    Optional<Org> findById(Long orgId);

    boolean existsById(Long orgId);

    Optional<Org> findByOrgCode(String orgCode);

    boolean existsByOrgCode(String orgCode);

    void deleteById(Long orgId);
}
