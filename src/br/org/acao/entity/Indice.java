package br.org.acao.entity;

import java.io.Serializable;

public class Indice implements Serializable{
	
	private String key;
	private String path;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
