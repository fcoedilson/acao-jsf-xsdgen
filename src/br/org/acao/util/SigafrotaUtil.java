package br.org.acao.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SigafrotaUtil{

	public static final String MAIL = "suporte.sgf@@fortaleza.ce.gov.br";
	public static final String USUARIO_LOGADO = "usuarioLogado";
	public static final String SESSION_OPEN = "sessionOpened";
	public static final String CONECTED_IP = "conectedIp";
	public static final String ADMIN = "ADMIN";
	public static final Integer DEFAULT_SRID = 54004;
	

	public static boolean isUserInRole(String role) {
		return getRequest().isUserInRole(role);
	}

	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		return request.getSession();
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Boolean cpfValidaDV(String cpf){
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		
		//if(cpf.matches("[0-9]*{0,10}")){
			//return false;
		//}
		int s1 = 0;
		int s2 = 0;
		int m1 = 10;
		int m2 = 11;
		for (int i = 0; i < (cpf.length() - 2); i++) {
			s1 += Integer.parseInt(cpf.substring(i, i+1))*m1;
			s2 += Integer.parseInt(cpf.substring(i, i+1))*m2;
			m1--;
			m2--;
		}
		s1 = (s1 % 11);

		if(s1  > 1){
			s1 = 11- s1;
		} else {
			s1 = 0;
		}

		s2 += s1*m2;
		s2 = (s2 % 11);
		if(s2 > 1){
			s2 = 11 - s2;
		} else {
			s2 = 0;
		}
		if( Integer.parseInt(cpf.substring(cpf.length() - 2, cpf.length() - 1)) == s1 && 
				Integer.parseInt(cpf.substring(cpf.length() - 1, cpf.length())) == s2 ){
			return  true;
		} else {
			return false;
		}
	}

	public static String md5(String valor) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(valor.getBytes()));
			String s = hash.toString(16);
			if (s.length() %2 != 0) s = "0" + s;
			return s;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}