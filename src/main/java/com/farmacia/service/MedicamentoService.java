package com.farmacia.service;

import com.farmacia.model.Medicamento;
import com.farmacia.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {
    @Autowired
    private MedicamentoRepository repository;

    public List<Medicamento> getAll() {
        List<Medicamento> medicamentos = repository.findAll();

        if (medicamentos.isEmpty()) {
            throw new RuntimeException("Não há medicamentos listados");
        }

        return medicamentos;
    }

    public Medicamento getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));
    }

    @Transactional
    public Medicamento post(Medicamento medicamento) {
        if (medicamento.getNome().isEmpty()) {
            throw new RuntimeException("Nome inválido");
        } else if (medicamento.getDosagem().isEmpty()) {
            throw new RuntimeException("Dosagem inválida");
        }

        return repository.save(medicamento);
    }

    @Transactional
    public Medicamento put(Medicamento medicamento) {
        Optional<Medicamento> medicamentoOpt = repository.findById(medicamento.getId());

        if (medicamentoOpt.isEmpty()) {
            throw new RuntimeException("Medicamento não encontrado");
        } else if (medicamento.getNome().isEmpty()) {
            throw new RuntimeException("Nome inválido");
        } else if (medicamento.getDosagem().isEmpty()) {
            throw new RuntimeException("Dosagem inválida");
        }

        return repository.save(medicamento);
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

        repository.deleteById(id);
    }
}