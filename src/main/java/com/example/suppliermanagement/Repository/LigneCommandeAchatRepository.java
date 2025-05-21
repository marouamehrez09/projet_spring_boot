package com.example.suppliermanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suppliermanagement.entity.LigneCommandeAchat;
public interface LigneCommandeAchatRepository extends JpaRepository<LigneCommandeAchat, Long> {
    List<LigneCommandeAchat> findByCommandeId(Long commandeId);
}