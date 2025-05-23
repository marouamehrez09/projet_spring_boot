package com.example.suppliermanagement.entity;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class LigneCommandeAchat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JsonIgnore 
    private CommandeAchat commande;
    
    private String produit;
    private int quantite;
    private double prix_unitaire;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CommandeAchat getCommande() {
		return commande;
	}
	public void setCommande(CommandeAchat commande) {
		this.commande = commande;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public double getPrix_unitaire() {
		return prix_unitaire;
	}
	public void setPrix_unitaire(double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}
    
}