package com.msashop.auth.contexts.code.adapter.in.web.command.controller;

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

import com.msashop.auth.contexts.code.adapter.in.web.command.dto.CodeCommandRequest;
import com.msashop.auth.contexts.code.adapter.in.web.command.dto.CodeCommandResponse;
import com.msashop.auth.contexts.code.adapter.in.web.command.mapper.CodeCommandWebMapper;
import com.msashop.auth.contexts.code.application.command.dto.CodeCommand;
import com.msashop.auth.contexts.code.application.command.dto.CodeCommandResult;
import com.msashop.auth.contexts.code.application.command.port.in.CreateCodeUseCase;
import com.msashop.auth.contexts.code.application.command.port.in.DeleteCodeUseCase;
import com.msashop.auth.contexts.code.application.command.port.in.UpdateCodeUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeCommandController {
    
    private final CreateCodeUseCase createCodeUseCase;
    private final UpdateCodeUseCase updateCodeUseCase;
    private final DeleteCodeUseCase deleteCodeUseCase;
    private final CodeCommandWebMapper codeCommandWebMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('CODE_CREATE')")
    public ResponseEntity<CodeCommandResponse> createCode(@Valid @RequestBody CodeCommandRequest request) {
        CodeCommand command = codeCommandWebMapper.toCommand(request);
        CodeCommandResult result = createCodeUseCase.handle(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.codeId())
                .toUri();
        return ResponseEntity.created(location)
                .body(codeCommandWebMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_UPDATE')")
    public ResponseEntity<CodeCommandResponse> updateCode(@PathVariable Long id, @Valid @RequestBody CodeCommandRequest request) {
        CodeCommand command = codeCommandWebMapper.toCommand(request);
        CodeCommandResult result = updateCodeUseCase.handle(id, command);
        return ResponseEntity.ok(codeCommandWebMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CODE_DELETE')")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        deleteCodeUseCase.handle(id);
        return ResponseEntity.noContent().build();
    }

}
