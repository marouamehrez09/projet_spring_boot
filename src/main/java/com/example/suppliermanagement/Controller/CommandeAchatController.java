package com.example.suppliermanagement.Controller;

import jakarta.persistence.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.suppliermanagement.Repository.CommandeAchatRepository;
import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.Service.CommandeAchatService;
import com.example.suppliermanagement.Service.FournisseurService;
import com.example.suppliermanagement.entity.CommandeAchat;
import com.example.suppliermanagement.entity.Fournisseur;
import com.example.suppliermanagement.entity.LigneCommandeAchat;

import lombok.*;
@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeAchatController {
    private final CommandeAchatService commandeService;
    public CommandeAchatController (CommandeAchatService  commandeService) {
        this. commandeService =  commandeService;
    }
    @GetMapping
    public ResponseEntity<List<CommandeAchat>> getAllCommandes() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CommandeAchat> getCommandeById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }
    
    @PostMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<CommandeAchat> createCommande(
            @PathVariable Long fournisseurId,
            @RequestBody CommandeAchat commande) {
        return ResponseEntity.ok(commandeService.createCommande(commande, fournisseurId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.ok("{\"message\":\"Commande supprimée avec succès\"}");
    }

    
    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeAchat> updateStatutCommande(
            @PathVariable Long id,
            @RequestParam String statut) {
        return ResponseEntity.ok(commandeService.updateStatutCommande(id, statut));
    }
    
    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<CommandeAchat>> getCommandesByFournisseur(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(commandeService.getCommandesByFournisseur(fournisseurId));
    }
    
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CommandeAchat>> getCommandesByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(commandeService.getCommandesByStatut(statut));
    }
}