package com.msashop.auth.contexts.organization.application.command.dto;

public record OrgCommand(String orgCode,
        String orgName,
        Long upperOrgId,
        Integer srt,
        Boolean useYn) {
}
