package br.org.acao.entity;

import java.io.Serializable;
import java.util.List;

public class Campo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String label;
	private String tipo;
	private String regex;
	private int obrigatorio;
	private String subnivelName;
	private String subnivelLabel;
	private String autocompleteEngine;
	private String autocompleteService;
	private List<String> itens;
	private List<Itemcbox> cboxItens;
	
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

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubnivelName() {
		return subnivelName;
	}

	public void setSubnivelName(String subnivelName) {
		this.subnivelName = subnivelName;
	}

	public String getSubnivelLabel() {
		return subnivelLabel;
	}

	public void setSubnivelLabel(String subnivelLabel) {
		this.subnivelLabel = subnivelLabel;
	}

	public String getAutocompleteEngine() {
		return autocompleteEngine;
	}

	public void setAutocompleteEngine(String autocompleteEngine) {
		this.autocompleteEngine = autocompleteEngine;
	}

	public String getAutocompleteService() {
		return autocompleteService;
	}

	public void setAutocompleteService(String autocompleteService) {
		this.autocompleteService = autocompleteService;
	}

	public List<String> getItens() {
		return itens;
	}

	public void setItens(List<String> itens) {
		this.itens = itens;
	}

	public List<Itemcbox> getCboxItens() {
		return cboxItens;
	}

	public void setCboxItens(List<Itemcbox> cboxItens) {
		this.cboxItens = cboxItens;
	}

	public int hashCode() {
		int result = 1;
		result = 31 * result + ((id == null) ? 0 : id.hashCode());
		result = 31 * result + ((name == null) ? 0 : name.hashCode());
		result = 31 * result + ((label == null) ? 0 : label.hashCode());
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
		return ((id == null && other.id == null) || (id != null && id.equals(other.id))) &&
			   ((name == null && other.name == null) || (name != null && name.equals(other.name))) &&
			   ((label == null && other.label == null) || (label != null && label.equals(other.label)));
	}
}
