package com.msashop.auth.attachment.adapter.in.web.command.dto;

public record AtchFileItemCommandRequest(
        String path,
        String fileName,
        Long fileSize,
        Boolean useYn) {
}
