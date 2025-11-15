package com.msashop.auth.contexts.code.adapter.in.web.query.dto;

public record CodeQueryRequest(
        Long upperCodeId,
        String upperCode,
        String code,
        String codeName,
        Boolean useYn
) {}
