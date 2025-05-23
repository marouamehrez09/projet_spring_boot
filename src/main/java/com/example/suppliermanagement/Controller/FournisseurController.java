package com.example.suppliermanagement.Controller;

import java.util.List;

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

import com.example.suppliermanagement.Repository.FournisseurRepository;
import com.example.suppliermanagement.Service.FournisseurService;
import com.example.suppliermanagement.entity.Fournisseur;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {
    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
       this.fournisseurService = fournisseurService;
    }
    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getAllFournisseurs());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getFournisseurById(id));
    }
    
    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        return ResponseEntity.ok(fournisseurService.saveFournisseur(fournisseur));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        return ResponseEntity.ok(fournisseurService.updateFournisseur(id, fournisseur));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok("Fournisseur supprimé avec succès");
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Fournisseur>> searchFournisseurs(@RequestParam String nom) {
        return ResponseEntity.ok(fournisseurService.searchFournisseurs(nom));
    }
    
    @GetMapping("/note-min/{note}")
    public ResponseEntity<List<Fournisseur>> getFournisseursByNoteMin(@PathVariable double note) {
        return ResponseEntity.ok(fournisseurService.getFournisseursByNoteMin(note));
    }
    
    @GetMapping("/moyenne-notes")
    public ResponseEntity<Double> getMoyenneNotes() {
        return ResponseEntity.ok(fournisseurService.calculateMoyenneNotes());
    }
}