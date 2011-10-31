package br.org.acao.bean;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.org.acao.util.SigafrotaUtil;

@Scope("session")
@Component("controlBean")
public class ControlStateBean extends BaseStateBean implements Serializable {


	private static final long serialVersionUID = 1L;

	public String userMail;

	public String voltar() {
		if (SigafrotaUtil.getSession() != null) {
			SigafrotaUtil.getSession().invalidate();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	protected static <E> E getUsuarioService(Class<E> clazz, HttpSession session) {
		ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		return (E) wac.getBean("usuarioService");
	}


	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
}