package br.org.acao.bean;

import br.org.acao.util.SigafrotaUtil;

public abstract class BaseStateBean extends BaseBean {

	private String currentState;

	public boolean isOpened() {
		Object tmp = SigafrotaUtil.getSession().getAttribute("isOpen");
		if (tmp == null) {
			closePopup();
			return false;
		} else {
			return (Boolean) tmp;
		}
	}

	public boolean isOpenSession(){
		Object o = SigafrotaUtil.getSession().getAttribute("sessionOpened");
		if(o == null){
			return false;
		} else {
			return (Boolean)o;
		}
	}

	public String openPopup() {
		SigafrotaUtil.getSession().setAttribute("isOpen", Boolean.TRUE);
		return SUCCESS;
	}


	public String closePopup() {
		SigafrotaUtil.getSession().setAttribute("isOpen", Boolean.FALSE);
		return SUCCESS;
	}
	
	public boolean isUserIn(){
		Object tmp = SigafrotaUtil.getSession().getAttribute("userIn");
		if (tmp == null) {
			return false;
		} else {
			return (Boolean) tmp;
		}
	}

	protected String currentBeanName() {
		return this.getClass().getSimpleName();
	}

	protected void setCurrentBean(String bean) {
		SigafrotaUtil.getSession().setAttribute("currentBean", bean);
	}

	protected void setCurrentState(String state) {
		this.currentState = state;
	}

	public String getCurrentBean() {
		return (String) SigafrotaUtil.getSession().getAttribute("currentBean");
	}

	public String getCurrentState() {
		return this.currentState == null ? "" : this.currentState;
	}
}