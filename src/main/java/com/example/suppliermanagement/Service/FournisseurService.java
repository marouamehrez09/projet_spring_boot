package com.example.suppliermanagement.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.entity.Fournisseur;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }
    
    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
    }
    
    public Fournisseur saveFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }
    
    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(id);
    }
    
    public Fournisseur updateFournisseur(Long id, Fournisseur fournisseurDetails) {
        Fournisseur fournisseur = getFournisseurById(id);
        fournisseur.setNom(fournisseurDetails.getNom());
        fournisseur.setContact(fournisseurDetails.getContact());
        fournisseur.setQualite_service(fournisseurDetails.getQualite_service());
        fournisseur.setNote(fournisseurDetails.getNote());
        return fournisseurRepository.save(fournisseur);
    }
    
    public List<Fournisseur> searchFournisseurs(String nom) {
        return fournisseurRepository.findByNomContaining(nom);
    }
    
    public List<Fournisseur> getFournisseursByNoteMin(double noteMin) {
        return fournisseurRepository.findByNoteGreaterThanEqual(noteMin);
    }
    
    public double calculateMoyenneNotes() {
        return fournisseurRepository.findAll().stream()
                .mapToDouble(Fournisseur::getNote)
                .average()
                .orElse(0.0);
    }


}
