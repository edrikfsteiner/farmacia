package com.farmacia.controller;

import com.farmacia.model.Medicamento;
import com.farmacia.service.MedicamentoService;
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
@RequestMapping("/medicamentos")
public class MedicamentoController {
    @Autowired
    MedicamentoService service;

    @Tag(name = "get", description = "Método GET para retornar todos os dados")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Tag(name = "get", description = "Método GET para retornar um dado")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
        @Parameter(description = "ID do medicamento", required = true)
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Tag(name = "post")
    @Operation(summary = "Método POST para criar um medicamento",
        description = "Ao receber atributos JSON, validará-los e criará o medicamento")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(service.post(medicamento));
    }

    @Tag(name = "put")
    @Operation(summary = "Método PUT para alterar um medicamento",
        description = "Ao receber atributos JSON, validará-los e alterará um medicamento")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(
        @Parameter(description = "ID do medicamento", required = true)
        @PathVariable Long id,
        @RequestBody Medicamento medicamento
    ) {
        return ResponseEntity.ok(service.put(id, medicamento));
    }

    @Tag(name = "delete", description = "Método DELETE para deletar um dado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Medicamento.class)) }),
        @ApiResponse(responseCode = "404", description = "Medicamento não encontrado",
            content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @Parameter(description = "ID do medicamento", required = true)
        @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.ok("Medicamento excluído");
    }
}