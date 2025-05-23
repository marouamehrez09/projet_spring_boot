package com.example.suppliermanagement.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suppliermanagement.entity.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    List<Fournisseur> findByNomContaining(String nom);
    List<Fournisseur> findByNoteGreaterThanEqual(double note);
}