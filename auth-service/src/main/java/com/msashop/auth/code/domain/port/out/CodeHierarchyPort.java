package com.msashop.auth.code.domain.port.out;

import com.msashop.auth.code.domain.model.Code;
import com.msashop.auth.code.domain.model.CodeId;

public interface CodeHierarchyPort {
    Code getReference(CodeId codeId);
}
