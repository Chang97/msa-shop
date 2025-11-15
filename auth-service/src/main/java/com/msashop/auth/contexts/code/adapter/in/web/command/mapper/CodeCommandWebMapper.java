package com.msashop.auth.contexts.code.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.contexts.code.adapter.in.web.command.dto.CodeCommandRequest;
import com.msashop.auth.contexts.code.adapter.in.web.command.dto.CodeCommandResponse;
import com.msashop.auth.contexts.code.application.command.dto.CodeCommand;
import com.msashop.auth.contexts.code.application.command.dto.CodeCommandResult;
import com.msashop.auth.shared.core.util.StringNormalizer;

@Component
public class CodeCommandWebMapper {

    public CodeCommand toCommand(CodeCommandRequest request) {
        return new CodeCommand(
                request.upperCodeId(),
                StringNormalizer.trimToNull(request.code()),
                StringNormalizer.trimToNull(request.codeName()),
                StringNormalizer.trimToNull(request.description()),
                request.srt(),
                StringNormalizer.trimToNull(request.etc1()),
                StringNormalizer.trimToNull(request.etc2()),
                StringNormalizer.trimToNull(request.etc3()),
                StringNormalizer.trimToNull(request.etc4()),
                request.useYn()
        );
    }

    public CodeCommandResponse toResponse(CodeCommandResult result) {
        return new CodeCommandResponse(result.codeId());
    }
}
