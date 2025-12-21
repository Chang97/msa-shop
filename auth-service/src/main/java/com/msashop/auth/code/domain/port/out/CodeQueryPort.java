package com.msashop.auth.code.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.msashop.auth.code.domain.model.Code;
import com.msashop.auth.code.domain.model.CodeFilter;

public interface CodeQueryPort {

    Optional<Code> findById(Long codeId);

    Optional<Code> findByCode(String code);

    List<Code> findChildrenByUpperId(Long upperCodeId);

    List<Code> findActiveChildrenByUpperId(Long upperCodeId);

    List<Code> findActiveChildrenByUpperCode(String upperCode);

    List<Code> search(CodeFilter filter);
}
