package com.msashop.auth.organization.application.command.dto;

public record OrgCommand(String orgCode,
        String orgName,
        Long upperOrgId,
        Integer srt,
        Boolean useYn) {
}
