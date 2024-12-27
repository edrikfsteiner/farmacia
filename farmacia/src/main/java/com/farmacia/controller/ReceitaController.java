package com.farmacia.controller;

import com.farmacia.model.Medicamento;
import com.farmacia.model.Receita;
import com.farmacia.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    @Autowired
    ReceitaService service;

    @Tag(name = "get", description = "Método GET para retornar todos os dados")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Tag(name = "get", description = "Método GET para retornar um dado")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
        @Parameter(description = "ID da receita", required = true)
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Tag(name = "post")
    @Operation(summary = "Método POST para criar uma receita",
        description = "Ao receber atributos JSON, validará-los e criará a receita")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Receita receita) {
        return ResponseEntity.ok(service.post(receita));
    }

    @Tag(name = "put")
    @Operation(summary = "Método PUT para alterar uma receita",
        description = "Ao receber atributos JSON, validará-los e alterará uma receita")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(
        @Parameter(description = "ID da receita", required = true)
        @PathVariable Long id,
        @RequestBody Receita receita
    ) {
        return ResponseEntity.ok(service.put(id, receita));
    }

    @Tag(name = "delete", description = "Método DELETE para deletar um dado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Medicamento.class)) }),
        @ApiResponse(responseCode = "404", description = "Receita não encontrada",
            content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @Parameter(description = "ID da receita", required = true)
        @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok("Receita excluída");
    }
}