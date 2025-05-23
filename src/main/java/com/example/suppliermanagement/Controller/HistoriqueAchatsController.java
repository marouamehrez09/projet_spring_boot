package com.example.suppliermanagement.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.Repository.HistoriqueAchatsRepository;
import com.example.suppliermanagement.Service.CommandeAchatService;
import com.example.suppliermanagement.Service.HistoriqueAchatsService;
import com.example.suppliermanagement.entity.Fournisseur;
import com.example.suppliermanagement.entity.HistoriqueAchats;
import com.example.suppliermanagement.entity.LigneCommandeAchat;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/historiques")
@RequiredArgsConstructor
public class HistoriqueAchatsController {
    private final HistoriqueAchatsService historiqueService;
    public HistoriqueAchatsController (HistoriqueAchatsService  historiqueService) {
    	this.historiqueService = historiqueService;
    }
    @GetMapping
    public ResponseEntity<List<HistoriqueAchats>> getAllHistoriques() {
        return ResponseEntity.ok(historiqueService.getAllHistoriques());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueAchats> getHistoriqueById(@PathVariable Long id) {
        return ResponseEntity.ok(historiqueService.getHistoriqueById(id));
    }
    
    @PostMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<HistoriqueAchats> createHistorique(
            @PathVariable Long fournisseurId,
            @RequestBody HistoriqueAchats historique) {
        return ResponseEntity.ok(historiqueService.createHistorique(historique, fournisseurId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistorique(@PathVariable Long id) {
        historiqueService.deleteHistorique(id);
        return ResponseEntity.ok("Historique supprimé avec succès");
    }
    
    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<HistoriqueAchats>> getHistoriqueByFournisseur(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(historiqueService.getHistoriqueByFournisseur(fournisseurId));
    }
    
    @GetMapping("/produit/{produit}")
    public ResponseEntity<List<HistoriqueAchats>> getHistoriqueByProduit(@PathVariable String produit) {
        return ResponseEntity.ok(historiqueService.getHistoriqueByProduit(produit));
    }
    
    @GetMapping("/fournisseur/{fournisseurId}/moyenne-delai")
    public ResponseEntity<Double> getMoyenneDelaiLivraison(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(historiqueService.calculateMoyenneDelaiLivraison(fournisseurId));
    }
    
    @GetMapping("/produit/{produit}/comparaison")
    public ResponseEntity<List<HistoriqueAchats>> comparerOffres(@PathVariable String produit) {
        return ResponseEntity.ok(historiqueService.comparerOffres(produit));
    }


}