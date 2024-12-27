package com.farmacia.service;

import com.farmacia.model.Medicamento;
import com.farmacia.model.Receita;
import com.farmacia.repository.MedicamentoRepository;
import com.farmacia.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReceitaService {
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    @Autowired
    private ReceitaRepository repository;

    private void validarReceita(Receita receita) {
        List<Long> medicamentosId = receita.getMedicamentos().stream().map(Medicamento::getId).toList();
        List<Medicamento> medicamentos = medicamentoRepository.findAllById(medicamentosId);

        if (receita.getMedicamentos().isEmpty()) {
            throw new RuntimeException("Medicamentos inválidos");
        } else if (medicamentos.size() != medicamentosId.size()) {
            List<Long> idsInvalidos = medicamentosId.stream()
                    .filter(id -> medicamentos.stream()
                            .noneMatch(medicamento -> medicamento.getId().equals(id))
                    )
                    .toList();
            String mensagem = String.format("Medicamentos inválidos: %s", idsInvalidos);

            throw new RuntimeException(mensagem);
        } else if (receita.getInstrucoes().isBlank()) {
            throw new RuntimeException("Instruções inválidas");
        }
    }

    public List<Receita> getAll() {
        List<Receita> receitas = repository.findAll();

        if (receitas.isEmpty()) {
            throw new RuntimeException("Não há receitas listadas");
        }

        return receitas;
    }

    public Receita getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    @Transactional
    public Receita post(Receita receita) {
        validarReceita(receita);
        return repository.save(receita);
    }

    @Transactional
    public Receita put(Long id, Receita receita) {
        Receita receitaEncontrada = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        validarReceita(receita);

        receitaEncontrada.setMedicamentos(receita.getMedicamentos());
        receitaEncontrada.setInstrucoes(receita.getInstrucoes());

        return repository.save(receitaEncontrada);
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        repository.deleteById(id);
    }
}