package com.testefinalibm.testefinal.controller;

import com.testefinalibm.testefinal.model.Candidato;
import com.testefinalibm.testefinal.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hiring")
public class HiringController {
    private final CandidatoRepository candidatoRepository;

    @Autowired
    public HiringController(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    @PostMapping("/start")
    public ResponseEntity<Candidato> startHiring(@RequestBody Candidato candidato) {
        Candidato savedCandidato = candidatoRepository.save(candidato);
        return new ResponseEntity<>(savedCandidato, HttpStatus.CREATED);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Void> scheduleInterview(@RequestParam Long codCandidato) {
        Candidato candidato = candidatoRepository.findById(codCandidato)
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado."));
        candidato.setStatus("Qualificado");
        candidatoRepository.save(candidato);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disqualify")
    public ResponseEntity<Void> disqualifyCandidate(@RequestParam Long codCandidato) {
        candidatoRepository.deleteById(codCandidato);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approve")
    public ResponseEntity<Void> approveCandidate(@RequestParam Long codCandidato) {
        Candidato candidato = candidatoRepository.findById(codCandidato)
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado."));
        candidato.setStatus("Aprovado");
        candidatoRepository.save(candidato);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/candidate/{codCandidato}")
    public ResponseEntity<String> getCandidateStatus(@PathVariable Long codCandidato) {
        Candidato candidato = candidatoRepository.findById(codCandidato)
                .orElseThrow(() -> new IllegalArgumentException("Candidato não encontrado."));
        return ResponseEntity.ok(candidato.getStatus());
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Candidato>> getApprovedCandidates() {
        List<Candidato> approvedCandidates = candidatoRepository.findApprovedCandidates();
        return ResponseEntity.ok(approvedCandidates);
    }



}
