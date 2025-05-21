package com.example.suppliermanagement.entity;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class HistoriqueAchats {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Fournisseur fournisseur;
    
    private String produit;
    private int quantite;
    private int delai_livraison; // en jours
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
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
	public int getDelai_livraison() {
		return delai_livraison;
	}
	public void setDelai_livraison(int delai_livraison) {
		this.delai_livraison = delai_livraison;
	}
    
}