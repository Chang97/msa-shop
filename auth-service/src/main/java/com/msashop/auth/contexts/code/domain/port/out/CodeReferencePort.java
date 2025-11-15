package com.msashop.auth.contexts.code.domain.port.out;

import java.util.Optional;

import com.msashop.auth.contexts.code.domain.model.CodeId;
import com.msashop.auth.contexts.code.domain.model.CodeSnapshot;

public interface CodeReferencePort {

    Optional<CodeSnapshot> load(CodeId codeId);
}
