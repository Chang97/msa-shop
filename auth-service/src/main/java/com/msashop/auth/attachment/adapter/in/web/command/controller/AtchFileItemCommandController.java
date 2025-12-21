package com.msashop.auth.attachment.adapter.in.web.command.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.msashop.auth.attachment.adapter.in.web.command.dto.AtchFileItemCommandRequest;
import com.msashop.auth.attachment.adapter.in.web.command.dto.AtchFileItemCommandResponse;
import com.msashop.auth.attachment.adapter.in.web.command.mapper.AtchFileItemCommandWebMapper;
import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommand;
import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommandResult;
import com.msashop.auth.attachment.application.command.port.in.CreateAtchFileItemUseCase;
import com.msashop.auth.attachment.application.command.port.in.DeleteAtchFileItemUseCase;
import com.msashop.auth.attachment.application.command.port.in.UpdateAtchFileItemUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attachments/{atchFileId}/items")
@RequiredArgsConstructor
public class AtchFileItemCommandController {

    private final CreateAtchFileItemUseCase createAtchFileItemUseCase;
    private final UpdateAtchFileItemUseCase updateAtchFileItemUseCase;
    private final DeleteAtchFileItemUseCase deleteAtchFileItemUseCase;
    private final AtchFileItemCommandWebMapper commandMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ATCH_FILE_ITEM_CREATE')")
    public ResponseEntity<AtchFileItemCommandResponse> create(@PathVariable Long atchFileId,
            @Valid @RequestBody AtchFileItemCommandRequest request) {
        AtchFileItemCommand command = commandMapper.toCommand(atchFileId, request);
        AtchFileItemCommandResult result = createAtchFileItemUseCase.handle(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemId}")
                .buildAndExpand(result.atchFileItemId())
                .toUri();
        return ResponseEntity.created(location).body(commandMapper.toResponse(result));
    }

    @PutMapping("/{atchFileItemId}")
    @PreAuthorize("hasAuthority('ATCH_FILE_ITEM_UPDATE')")
    public ResponseEntity<AtchFileItemCommandResponse> update(@PathVariable Long atchFileId,
            @PathVariable Long atchFileItemId,
            @Valid @RequestBody AtchFileItemCommandRequest request) {
        AtchFileItemCommand command = commandMapper.toCommand(atchFileId, request);
        AtchFileItemCommandResult result = updateAtchFileItemUseCase.handle(atchFileItemId, command);
        return ResponseEntity.ok(commandMapper.toResponse(result));
    }

    @DeleteMapping("/{atchFileItemId}")
    @PreAuthorize("hasAuthority('ATCH_FILE_ITEM_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long atchFileId, @PathVariable Long atchFileItemId) {
        deleteAtchFileItemUseCase.handle(atchFileItemId);
        return ResponseEntity.noContent().build();
    }
}
