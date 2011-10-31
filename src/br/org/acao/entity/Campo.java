package br.org.acao.entity;

import java.io.Serializable;
import java.util.List;

public class Campo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String label;
	private String tipo;
	private String maskara;
	private int obrigatorio;
	
	private List<Campo> campos;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}

	public int getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(int obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getMaskara() {
		return maskara;
	}

	public void setMaskara(String maskara) {
		this.maskara = maskara;
	}
	
	public int hashCode() {
		int result = 1;
		result = 31 * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campo other = (Campo) obj;
		return ((name == null && other.name == null) || (name != null && name.equals(other.name)));
	}
}
