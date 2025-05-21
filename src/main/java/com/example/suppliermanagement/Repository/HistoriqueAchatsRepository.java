package com.example.suppliermanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suppliermanagement.entity.HistoriqueAchats;
public interface HistoriqueAchatsRepository extends JpaRepository<HistoriqueAchats, Long> {
    List<HistoriqueAchats> findByFournisseurId(Long fournisseurId);
    List<HistoriqueAchats> findByProduit(String produit);
}