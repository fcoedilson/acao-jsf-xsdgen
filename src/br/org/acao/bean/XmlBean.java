package br.org.acao.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.org.acao.entity.Campo;
import br.org.acao.entity.Documento;
import br.org.acao.entity.Indice;
import br.org.acao.entity.SubNivel;
import br.org.acao.service.BaseService;
import br.org.acao.util.DownloadFileUtil;
import br.org.acao.util.VelocityUtil;

@Scope("session")
@Component("xmlBean")
public class XmlBean extends EntityBean<Integer, Campo>  {


	private static final String XSD = "XSD";

	private String formName;
	private String titulo;
	private String classificacao;
	private String targetNamespace = "";
	private int obrigatorio;

	private Indice indice = new Indice();
	private Campo campo = new Campo();
	private String subNivel;
	private String nomeArquivo;

	private List<Indice> indices = new ArrayList<Indice>();
	private List<Campo> campos  = new ArrayList<Campo>();
	private boolean incluirGrupo = false;
	private boolean autocomplete;
	private boolean select;
	private String itemToInclude;
	private String itemToRemove;
	private Campo campoRemove = new Campo();
	private Campo campoInclude = new Campo();
	private Integer up;
	private Integer down;

	private Map<String, List<Campo>> subNivelMap = new HashMap<String, List<Campo>>();


	@Override
	protected Campo createNewEntity() {
		indices = new ArrayList<Indice>();
		campos = new ArrayList<Campo>();
		subNivelMap = new HashMap<String, List<Campo>>();
		return new Campo();
	}

	public boolean getCampoStatus(){
		return campos.size() > 0;
	}

	// reeturn true se contem algum elementos
	public boolean getSubNiveisStatus(){
		return subNivelMap.keySet().size() > 0;
	}


	// pega a lista de subniveis já incluidos
	public List<String> getSubNivelList(){
		return new ArrayList<String>(this.subNivelMap.keySet());
	}

	public List<SelectItem> getCampoList(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for (Campo c : campos) {
			result.add(new SelectItem(c, c.getName()));
		}
		return result;
	}

	@Override
	protected Integer retrieveEntityId(Campo entity) {
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

	/*
	 * adicionar um item à lista de itens do campo
	 */

	public String adicionarIndice(){
		this.indices.add(this.indice);
		this.indice = new Indice();
		return SUCCESS;
	}

	public String adicionarCampo(){

		String subNivel = "";

		if(this.campo.getSubnivelLabel() != "" || this.campo.getSubnivelLabel() != null){

			subNivel = this.campo.getSubnivelLabel();
			this.campo.setSubnivelName(this.campo.getSubnivelName().trim().replace(" ", ""));

			if(!subNivelMap.containsKey(subNivel)){
				List<Campo> novaLista = new ArrayList<Campo>();
				novaLista.add(this.campo);
				subNivelMap.put(subNivel, novaLista);
			} else {
				subNivelMap.get(subNivel).add(this.campo);
			}
			this.autocomplete = false;

		} else {

			subNivel = "semSubnivel";
			if(this.campo.getName() != null && this.campo.getTipo() != null){

				this.campo.setSubnivelName(subNivel);
				if(!subNivelMap.containsKey(subNivel)){
					List<Campo> novaLista = new ArrayList<Campo>();
					novaLista.add(this.campo);
				} else {
					subNivelMap.get(subNivel).add(this.campo);
				}
				this.autocomplete = false;
			}
		}

		this.campos = new ArrayList<Campo>();

		for (String s : subNivelMap.keySet()) {
			this.campos.addAll(subNivelMap.get(s));
		}
		
		this.select = false;

		this.campo = new Campo();
		return SUCCESS;
	}

	public String adicionarItemCampo(){
		if(this.campo.getItens() != null){
			this.campo.getItens().add(this.itemToInclude);
		} else {
			this.campo.setItens(new ArrayList<String>());
			this.campo.getItens().add(this.itemToInclude);
		}
		this.itemToInclude = null;
		//this.select = false;
		return SUCCESS;
	}

	public String removeItemCampo(){
		if( this.campo.getItens() != null){
			this.campo.getItens().remove(this.itemToRemove);
		}
		return SUCCESS;
	}


	public String removeIndice(){
		this.indices.remove(this.indice);
		this.indice = new Indice();
		return SUCCESS;
	}

	public String removeCampo(){
		String subNivel = this.campoRemove.getSubnivelLabel().trim().toLowerCase().replace(" ", "");
		subNivelMap.get(subNivel).remove(this.campoRemove);
		//this.campos.remove(this.campoRemove);
		this.campos = subNivelMap.get(subNivel);
		//this.campos = subNivelMap.get(novoSubNivel);
		this.campoRemove = new Campo();
		return SUCCESS;
	}

	public String upField(){
		Campo toup = this.campos.get(this.up);
		if(toup != null && this.up > 0){
			Campo todown = this.campos.get(this.up-1);
			if(todown != null){
				this.campos.set(this.up-1, toup);
				this.campos.set(this.up, todown);
			}
		}
		return SUCCESS;
	}

	public String downField(){
		Campo todown = this.campos.get(this.down);
		if(todown != null && this.down < this.campos.size() ){
			Campo toup = this.campos.get(this.down+1);
			if(toup != null){
				this.campos.set(this.down, toup);
				this.campos.set(this.down+1, todown);
			}
		}

		return SUCCESS;
	}

	public String upItem(){
		String toup = this.campo.getItens().get(this.up); 
		if(toup != null && this.up > 0){
			String todown = this.campo.getItens().get(this.up-1);
			if(todown != null){
				this.campo.getItens().set(this.up-1, toup);
				this.campo.getItens().set(this.up, todown);
			}
		}
		return SUCCESS;
	}

	public String downItem(){
		String todown = this.campo.getItens().get(this.down); 
		if(this.down != null && this.down < this.campo.getItens().size()-1){
			String toup = this.campo.getItens().get(this.down+1);
			this.campo.getItens().set(this.down, toup);
			this.campo.getItens().set(this.down+1, todown);
		}

		return SUCCESS;
	}

	/**
	 * Verifica o tipo do campo, caso seja autocomplete ou select, redenderiza itens adicionais na tela
	 * @return
	 */
	public String verificarTipoCampo(){

		if(this.campo.getTipo() != null){
			if(this.campo.getTipo().equals("select")){
				this.select = true;
				return SUCCESS;
			} else if(this.campo.getTipo().equals("autocomplete")){
				this.select = false;
				this.autocomplete = true;
				return SUCCESS;
			}
		}
		this.select = false;
		return SUCCESS;
	}

	/*
	 * usa o framework velocity para geração do arquivo XSD
	 */
	public String downloadXsdFile() throws Exception {

		Documento documento = new Documento();
		documento.setTargetNamespace(this.targetNamespace);
		documento.setFormName(this.formName);
		documento.setTitulo(this.titulo);
		documento.setClassificacao(this.classificacao);
		documento.setIndices(this.indices);		
		List<SubNivel> subniveis = new ArrayList<SubNivel>();

		for (String sub : subNivelMap.keySet()) {
			SubNivel novo = new SubNivel();
			List<Campo> campos = subNivelMap.get(sub);
			novo.setNome(campos.get(0).getSubnivelName().replace(" ", "").toLowerCase());
			novo.setLabel(campos.get(0).getSubnivelLabel());
			novo.setCampos(campos);
			subniveis.add(novo);
		}


		byte[] bytes = VelocityUtil.merge("template-schema.vm", new String[]{"documento", "subniveis"}, new Object[]{documento, subniveis});
		return DownloadFileUtil.downloadFile(bytes, this.nomeArquivo + ".xsd", "application/text");
	}

	public String getSubNivel() {
		return subNivel;
	}

	public void setSubNivel(String subNivel) {
		this.subNivel = subNivel;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String populate(){
		return SUCCESS;
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

	public boolean isIncluirGrupo() {
		return incluirGrupo;
	}

	public void setIncluirGrupo(boolean incluirGrupo) {
		this.incluirGrupo = incluirGrupo;
	}

	public boolean isAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(boolean autocomplete) {
		this.autocomplete = autocomplete;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getItemToInclude() {
		return itemToInclude;
	}

	public void setItemToInclude(String itemToInclude) {
		this.itemToInclude = itemToInclude;
	}

	public String getItemToRemove() {
		return itemToRemove;
	}

	public void setItemToRemove(String itemToRemove) {
		this.itemToRemove = itemToRemove;
	}

	public Campo getCampoRemove() {
		return campoRemove;
	}

	public void setCampoRemove(Campo campoRemove) {
		this.campoRemove = campoRemove;
	}

	public Campo getCampoInclude() {
		return campoInclude;
	}

	public void setCampoInclude(Campo campoInclude) {
		this.campoInclude = campoInclude;
	}

	public Integer getUp() {
		return up;
	}

	public void setUp(Integer up) {
		this.up = up;
	}

	public Integer getDown() {
		return down;
	}

	public void setDown(Integer down) {
		this.down = down;
	}

	@Override
	protected BaseService<Integer, Campo> retrieveEntityService() {
		// TODO Auto-generated method stub
		return null;
	}
}