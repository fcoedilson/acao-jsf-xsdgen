/**
 * 
 */
package br.org.acao.conversores;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class CampoConverter implements Converter, Serializable{

	

	private static final long serialVersionUID = -3150047151826294316L;

	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String str) {
		if(str != null && !str.equals("")){
			return (Object)str;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object obj) {
		if(obj == null){
			return null;
		}else{
			if(obj != null && obj != ""){
				return obj.toString();
			} else {
				return null;
			}
		}
	}
}
