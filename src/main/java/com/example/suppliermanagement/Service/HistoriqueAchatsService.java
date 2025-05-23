package com.example.suppliermanagement.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.suppliermanagement.Repository.HistoriqueAchatsRepository;
import com.example.suppliermanagement.entity.Fournisseur;
import com.example.suppliermanagement.entity.HistoriqueAchats;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoriqueAchatsService {
    private final HistoriqueAchatsRepository historiqueRepository;
    private final FournisseurService fournisseurService;
    public HistoriqueAchatsService(HistoriqueAchatsRepository historiqueRepository,
            FournisseurService fournisseurService) {
this.historiqueRepository = historiqueRepository;
this.fournisseurService = fournisseurService;
}
    public List<HistoriqueAchats> getAllHistoriques() {
        return historiqueRepository.findAll();
    }
    
    public HistoriqueAchats getHistoriqueById(Long id) {
        return historiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historique non trouvé"));
    }
    
    public HistoriqueAchats createHistorique(HistoriqueAchats historique, Long fournisseurId) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId);
        historique.setFournisseur(fournisseur);
        return historiqueRepository.save(historique);
    }
    
    public void deleteHistorique(Long id) {
        historiqueRepository.deleteById(id);
    }
    
    public List<HistoriqueAchats> getHistoriqueByFournisseur(Long fournisseurId) {
        return historiqueRepository.findByFournisseurId(fournisseurId);
    }
    
    public List<HistoriqueAchats> getHistoriqueByProduit(String produit) {
        return historiqueRepository.findByProduit(produit);
    }
    
    public double calculateMoyenneDelaiLivraison(Long fournisseurId) {
        List<HistoriqueAchats> historiques = historiqueRepository.findByFournisseurId(fournisseurId);
        return historiques.stream()
                .mapToInt(HistoriqueAchats::getDelai_livraison)
                .average()
                .orElse(0.0);
    }
    
    public List<HistoriqueAchats> comparerOffres(String produit) {
        return historiqueRepository.findByProduit(produit)
                .stream()
                .sorted(Comparator.comparingDouble(HistoriqueAchats::getPrix_unitaire))
                .collect(Collectors.toList());
    }


}