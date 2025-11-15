package com.msashop.auth.contexts.code.domain.port.out;

import com.msashop.auth.contexts.code.domain.model.Code;
import com.msashop.auth.contexts.code.domain.model.CodeId;

public interface CodeHierarchyPort {
    Code getReference(CodeId codeId);
}
