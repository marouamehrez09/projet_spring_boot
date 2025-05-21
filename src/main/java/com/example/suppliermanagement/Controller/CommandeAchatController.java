package com.example.suppliermanagement.Controller;

import jakarta.persistence.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.suppliermanagement.Repository.CommandeAchatRepository;
import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.entity.CommandeAchat;
import com.example.suppliermanagement.entity.Fournisseur;
import com.example.suppliermanagement.entity.LigneCommandeAchat;

import lombok.*;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin("*")
public class CommandeAchatController {
    
    @Autowired
    private CommandeAchatRepository commandeRepository;
    
    @Autowired
    private FournisseurRepository fournisseurRepository;
    
    @GetMapping
    public List<CommandeAchat> getAllCommandes() {
        return commandeRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public CommandeAchat getCommandeById(@PathVariable Long id) {
        return commandeRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Commande non trouvée"));
    }
    
    @PostMapping
    public CommandeAchat createCommande(@RequestBody CommandeAchat commande) {
        // Vérifier que le fournisseur existe
        Fournisseur fournisseur = fournisseurRepository.findById(commande.getFournisseur().getId())
            .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        
        commande.setFournisseur(fournisseur);
        commande.setDate(new Date());
        commande.setStatut("En cours");
        
        // Calculer le montant total
        double montantTotal = commande.getLignes().stream()
            .mapToDouble(l -> l.getQuantite() * l.getPrix_unitaire())
            .sum();
        commande.setMontant(montantTotal);
        
        // Sauvegarder la commande et ses lignes
        CommandeAchat savedCommande = commandeRepository.save(commande);
        
        // Mettre à jour l'historique des achats
        for (LigneCommandeAchat ligne : commande.getLignes()) {
            ligne.setCommande(savedCommande);
        }
        
        return savedCommande;
    }
    
    @PutMapping("/{id}/statut")
    public CommandeAchat updateStatutCommande(@PathVariable Long id, @RequestBody String statut) {
        CommandeAchat commande = commandeRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Commande non trouvée"));
        
        commande.setStatut(statut);
        return commandeRepository.save(commande);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeRepository.deleteById(id);
    }
    
    @GetMapping("/fournisseur/{fournisseurId}")
    public List<CommandeAchat> getCommandesByFournisseur(@PathVariable Long fournisseurId) {
        return commandeRepository.findByFournisseurId(fournisseurId);
    }
    
    @GetMapping("/statut/{statut}")
    public List<CommandeAchat> getCommandesByStatut(@PathVariable String statut) {
        return commandeRepository.findByStatut(statut);
    }
}