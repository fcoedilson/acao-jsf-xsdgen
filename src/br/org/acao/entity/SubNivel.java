package br.org.acao.entity;

import java.io.Serializable;
import java.util.List;

public class SubNivel implements Serializable{
	
	
	private String nome;
	private String label;	
	private List<Campo> campos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
}
