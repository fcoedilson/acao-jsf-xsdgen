package br.org.acao.entity;

import java.io.Serializable;
import java.util.List;

public class Campo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String label;
	private String tipo;
	private String maskara;
	private int obrigatorio;
	private String nomeSubnivel;
	private String labelSubnivel;
	
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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeSubnivel() {
		return nomeSubnivel;
	}

	public void setNomeSubnivel(String nomeSubnivel) {
		this.nomeSubnivel = nomeSubnivel;
	}

	public String getLabelSubnivel() {
		return labelSubnivel;
	}

	public void setLabelSubnivel(String labelSubnivel) {
		this.labelSubnivel = labelSubnivel;
	}

	public int hashCode() {
		int result = 1;
		result = 31 * result + ((id == null) ? 0 : id.hashCode());
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
		return ((id == null && other.id == null) || (id != null && id.equals(other.id)));
	}
}
