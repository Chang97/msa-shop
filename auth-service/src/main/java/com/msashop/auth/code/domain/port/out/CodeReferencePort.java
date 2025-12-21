package com.msashop.auth.code.domain.port.out;

import java.util.Optional;

import com.msashop.auth.code.domain.model.CodeId;
import com.msashop.auth.code.domain.model.CodeSnapshot;

public interface CodeReferencePort {

    Optional<CodeSnapshot> load(CodeId codeId);
}
