package br.org.acao.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.org.acao.entity.Campo;
import br.org.acao.entity.Documento;
import br.org.acao.entity.Indice;
import br.org.acao.service.BaseService;
import br.org.acao.util.DownloadFileUtil;
import br.org.acao.util.VelocityUtil;

@Scope("session")
@Component("xmlBean")
public class XmlBean extends EntityBean<Integer, Documento>  {


	private static final String XSD = "XSD";

	private String formName;
	private String titulo;
	private String classificacao;
	private String targetNamespace = "";
	private int obrigatorio;
	private Indice indice = new Indice();
	private Campo campo = new Campo();
	private List<Indice> indices = new ArrayList<Indice>();;
	private List<Campo> campos  = new ArrayList<Campo>();

	@Override
	protected Documento createNewEntity() {
		indices = new ArrayList<Indice>();
		campos = new ArrayList<Campo>();
		return new Documento();
	}

	@Override
	protected Integer retrieveEntityId(Documento entity) {
		return entity.getId();
	}

	public boolean isXmlBean(){
		return XmlBean.class.getSimpleName().equals(getCurrentBean());
	}

	public String loadXmlState(){
		setCurrentState(XSD);
		setCurrentBean(currentBeanName());
		return SUCCESS;
	}

	public String adicionarIndice(){
		this.indices.add(this.indice);
		this.indice = new Indice();
		return SUCCESS;
	}

	public String adicionarCampo(){
		this.campos.add(this.campo);
		this.campo = new Campo();
		return SUCCESS;
	}

	public String removeIndice(){
		this.indices.remove(this.indice);
		this.indice = new Indice();
		return SUCCESS;
	}

	public String removeCampo(){
		this.campos.remove(this.campo);
		this.campo = new Campo();
		return SUCCESS;
	}

	public String downloadXsdFile() throws Exception {

		Documento d = new Documento();
		d.setTargetNamespace(this.targetNamespace);
		d.setFormName(this.formName);
		d.setTitulo(this.titulo);
		d.setClassificacao(this.classificacao);
		d.setCampos(this.campos);
		d.setIndices(this.indices);
		
		byte[] bytes = VelocityUtil.merge("xsd-schema.vm", new String[]{"documento"}, new Object[]{d});
		return DownloadFileUtil.downloadFile(bytes, "teste.xsd", "application/text");   //downloadKMLNoZipFile(bytes);
	}
	
	public int getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(int obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public List<Indice> getIndices() {
		return indices;
	}

	public void setIndices(List<Indice> indices) {
		this.indices = indices;
	}

	public Indice getIndice() {
		return indice;
	}

	public void setIndice(Indice indice) {
		this.indice = indice;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}

	public String getTargetNamespace() {
		return targetNamespace;
	}

	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}


	@Override
	protected BaseService<Integer, Documento> retrieveEntityService() {
		// TODO Auto-generated method stub
		return null;
	}
}