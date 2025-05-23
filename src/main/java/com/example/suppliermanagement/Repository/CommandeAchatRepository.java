package com.example.suppliermanagement.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.suppliermanagement.entity.CommandeAchat;


public interface CommandeAchatRepository extends JpaRepository<CommandeAchat, Long> {
    List<CommandeAchat> findByFournisseurId(Long fournisseurId);
    List<CommandeAchat> findByStatut(String statut);
    List<CommandeAchat> findByDateBetween(Date start, Date end);
    
}