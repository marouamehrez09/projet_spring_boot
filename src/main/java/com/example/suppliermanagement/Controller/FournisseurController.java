package com.example.suppliermanagement.Controller;

import java.util.List;

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

import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.entity.Fournisseur;


@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin("*")
public class FournisseurController {
    
    @Autowired
    private FournisseurRepository fournisseurRepository;
    
    @GetMapping
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Fournisseur getFournisseurById(@PathVariable Long id) {
        return fournisseurRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Fournisseur non trouvé"));
    }
    
    @PostMapping
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }
    
    @PutMapping("/{id}")
    public Fournisseur updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseurDetails) {
        Fournisseur fournisseur = fournisseurRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Fournisseur non trouvé"));
        
        fournisseur.setNom(fournisseurDetails.getNom());
        fournisseur.setContact(fournisseurDetails.getContact());
        fournisseur.setQualite_service(fournisseurDetails.getQualite_service());
        fournisseur.setNote(fournisseurDetails.getNote());
        
        return fournisseurRepository.save(fournisseur);
    }
    
    @DeleteMapping("/{id}")
    public void deleteFournisseur(@PathVariable Long id) {
        fournisseurRepository.deleteById(id);
    }
    
    @GetMapping("/note/{note}")
    public List<Fournisseur> getFournisseursByNote(@PathVariable double note) {
        return fournisseurRepository.findByNoteGreaterThanEqual(note);
    }
}