package com.example.suppliermanagement.entity;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Fournisseur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String contact;
    private String qualite_service;
    private double note;
    
    @OneToMany(mappedBy = "fournisseur")
    private List<CommandeAchat> commandes;
    
    @OneToMany(mappedBy = "fournisseur")
    private List<HistoriqueAchats> historique;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getQualite_service() {
		return qualite_service;
	}

	public void setQualite_service(String qualite_service) {
		this.qualite_service = qualite_service;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public List<CommandeAchat> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<CommandeAchat> commandes) {
		this.commandes = commandes;
	}

	public List<HistoriqueAchats> getHistorique() {
		return historique;
	}

	public void setHistorique(List<HistoriqueAchats> historique) {
		this.historique = historique;
	}

	
	
    
}