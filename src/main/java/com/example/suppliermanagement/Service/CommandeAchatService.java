package com.example.suppliermanagement.Service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;


import com.example.suppliermanagement.Repository.CommandeAchatRepository;
import com.example.suppliermanagement.Repository.LigneCommandeAchatRepository;
import com.example.suppliermanagement.entity.CommandeAchat;
import com.example.suppliermanagement.entity.Fournisseur;
import com.example.suppliermanagement.entity.LigneCommandeAchat;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommandeAchatService {

    private final CommandeAchatRepository commandeRepository;
    private final FournisseurService fournisseurService;
    private final LigneCommandeAchatRepository ligneRepository;

    public CommandeAchatService(CommandeAchatRepository commandeRepository,
                                FournisseurService fournisseurService,
                                LigneCommandeAchatRepository ligneRepository) {
        this.commandeRepository = commandeRepository;
        this.fournisseurService = fournisseurService;
        this.ligneRepository = ligneRepository;
    }

    public List<CommandeAchat> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public CommandeAchat getCommandeById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }

    public CommandeAchat createCommande(CommandeAchat commande, Long fournisseurId) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId);
        commande.setFournisseur(fournisseur);
        commande.setDate(new Date());
        commande.setStatut("En cours");

        double montantTotal = commande.getLignes().stream()
                .mapToDouble(l -> l.getQuantite() * l.getPrix_unitaire())
                .sum();
        commande.setMontant(montantTotal);

        CommandeAchat savedCommande = commandeRepository.save(commande);

        for (LigneCommandeAchat ligne : commande.getLignes()) {
            ligne.setCommande(savedCommande);
            ligneRepository.save(ligne);
        }

        return savedCommande;
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    public CommandeAchat updateStatutCommande(Long id, String statut) {
        CommandeAchat commande = getCommandeById(id);
        commande.setStatut(statut);
        return commandeRepository.save(commande);
    }

    public List<CommandeAchat> getCommandesByFournisseur(Long fournisseurId) {
        return commandeRepository.findByFournisseurId(fournisseurId);
    }

    public List<CommandeAchat> getCommandesByStatut(String statut) {
        return commandeRepository.findByStatut(statut);
    }
}
