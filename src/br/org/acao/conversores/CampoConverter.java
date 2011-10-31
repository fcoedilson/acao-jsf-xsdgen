/**
 * 
 */
package br.org.acao.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class CampoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String str) {
		if(str != null && !str.equals("")){
			
			return Integer.valueOf(str);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object obj) {
		if(obj == null){
			return null;
		}else{
			return String.valueOf(obj);
		}
	}
}
