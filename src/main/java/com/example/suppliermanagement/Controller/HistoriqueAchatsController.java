package com.example.suppliermanagement.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.Repository.HistoriqueAchatsRepository;
import com.example.suppliermanagement.entity.Fournisseur;
import com.example.suppliermanagement.entity.HistoriqueAchats;
import com.example.suppliermanagement.entity.LigneCommandeAchat;

@RestController
@RequestMapping("/api/historique")
@CrossOrigin("*")
public class HistoriqueAchatsController {
    
    @Autowired
    private HistoriqueAchatsRepository historiqueRepository;
    
    @Autowired
    private FournisseurRepository fournisseurRepository;
    
    @GetMapping
    public List<HistoriqueAchats> getAllHistorique() {
        return historiqueRepository.findAll();
    }
    
    @GetMapping("/fournisseur/{fournisseurId}")
    public List<HistoriqueAchats> getHistoriqueByFournisseur(@PathVariable Long fournisseurId) {
        return historiqueRepository.findByFournisseurId(fournisseurId);
    }
    
    @GetMapping("/produit/{produit}")
    public List<HistoriqueAchats> getHistoriqueByProduit(@PathVariable String produit) {
        return historiqueRepository.findByProduit(produit);
    }
    
    @PostMapping
    public HistoriqueAchats addHistorique(@RequestBody HistoriqueAchats historique) {
        // Vérifier que le fournisseur existe
        Fournisseur fournisseur = fournisseurRepository.findById(historique.getFournisseur().getId())
            .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        
        historique.setFournisseur(fournisseur);
        return historiqueRepository.save(historique);
    }
    
    @GetMapping("/evaluation/{fournisseurId}")
    public Map<String, Object> evaluerFournisseur(@PathVariable Long fournisseurId) {
        List<HistoriqueAchats> historique = historiqueRepository.findByFournisseurId(fournisseurId);
        
        if (historique.isEmpty()) {
            throw new RuntimeException("Aucun historique trouvé pour ce fournisseur");
        }
        
        double delaiMoyen = historique.stream()
            .mapToInt(HistoriqueAchats::getDelai_livraison)
            .average()
            .orElse(0);
        
        int totalQuantite = historique.stream()
            .mapToInt(HistoriqueAchats::getQuantite)
            .sum();
        
        Map<String, Object> evaluation = new HashMap<>();
        evaluation.put("delaiMoyenLivraison", delaiMoyen);
        evaluation.put("totalQuantiteCommandee", totalQuantite);
        evaluation.put("nombreCommandes", historique.size());
        
        return evaluation;
    }
    
    @GetMapping("/comparaison/{produit}")
    public List<Map<String, Object>> comparerFournisseursParProduit(@PathVariable String produit) {
        List<HistoriqueAchats> historique = historiqueRepository.findByProduit(produit);
        
        // Grouper par fournisseur
        Map<Fournisseur, List<HistoriqueAchats>> parFournisseur = historique.stream()
            .collect(Collectors.groupingBy(HistoriqueAchats::getFournisseur));
        
        List<Map<String, Object>> resultats = new ArrayList<>();
        
        for (Map.Entry<Fournisseur, List<HistoriqueAchats>> entry : parFournisseur.entrySet()) {
            Fournisseur fournisseur = entry.getKey();
            List<HistoriqueAchats> achats = entry.getValue();
            
            double delaiMoyen = achats.stream()
                .mapToInt(HistoriqueAchats::getDelai_livraison)
                .average()
                .orElse(0);
            
            double prixMoyen = achats.stream()
                .mapToDouble(a -> {
                    // Trouver le prix unitaire dans les commandes
                    // (Note: dans une vraie application, vous devriez avoir une meilleure façon de faire ça)
                    return a.getFournisseur().getCommandes().stream()
                        .flatMap(c -> c.getLignes().stream())
                        .filter(l -> l.getProduit().equals(produit))
                        .mapToDouble(LigneCommandeAchat::getPrix_unitaire)
                        .average()
                        .orElse(0);
                })
                .average()
                .orElse(0);
            
            Map<String, Object> info = new HashMap<>();
            info.put("fournisseurId", fournisseur.getId());
            info.put("fournisseurNom", fournisseur.getNom());
            info.put("delaiMoyenLivraison", delaiMoyen);
            info.put("prixMoyen", prixMoyen);
            info.put("noteFournisseur", fournisseur.getNote());
            
            resultats.add(info);
        }
        
        return resultats;
    }
}