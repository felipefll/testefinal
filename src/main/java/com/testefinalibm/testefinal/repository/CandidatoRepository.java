package com.testefinalibm.testefinal.repository;

import com.testefinalibm.testefinal.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    @Query("SELECT c FROM Candidato c WHERE c.status = 'Aprovado'")
    List<Candidato> findApprovedCandidates();
}