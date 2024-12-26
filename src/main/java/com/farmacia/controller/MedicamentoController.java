package com.farmacia.controller;

import com.farmacia.model.Medicamento;
import com.farmacia.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {
    @Autowired
    MedicamentoService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(service.post(medicamento));
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(service.put(medicamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Medicamento excluído");
    }
}