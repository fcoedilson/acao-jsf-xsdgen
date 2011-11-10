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
public class XmlBean extends EntityBean<Integer, Documento>  {


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
	
	private Map<String, List<Campo>> subNivelMap = new HashMap<String, List<Campo>>();
	

	@Override
	protected Documento createNewEntity() {
		indices = new ArrayList<Indice>();
		campos = new ArrayList<Campo>();
		subNivelMap = new HashMap<String, List<Campo>>();
		return new Documento();
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

	/*
	 * adicionar um item à lista de itens do campo
	 */

	public String adicionarIndice(){
		this.indices.add(this.indice);
		this.indice = new Indice();
		return SUCCESS;
	}

	public String adicionarCampo(){
		
		if(this.campo.getNomeSubnivel() != "" || this.campo.getNomeSubnivel() != null){
			
			String novoSubnivel = this.campo.getLabelSubnivel();
			this.campo.setNomeSubnivel(novoSubnivel);
			String novoSubNivel = this.campo.getNomeSubnivel().trim().toLowerCase().replace(" ", "");
			
			if(!subNivelMap.containsKey(novoSubNivel)){
				List<Campo> novaLista = new ArrayList<Campo>();
				novaLista.add(this.campo);
				subNivelMap.put(novoSubNivel, novaLista);
			} else {
				subNivelMap.get(novoSubNivel).add(this.campo);
			}
			this.campos.add(this.campo);
		} else {
			
			String baseSubNivel = "semsubnivel";
			if(this.campo.getName() != null && this.campo.getTipo() != null){
				this.campo.setNomeSubnivel(baseSubNivel);
				
				if(!subNivelMap.containsKey(baseSubNivel)){
					List<Campo> novaLista = new ArrayList<Campo>();
					novaLista.add(this.campo);
				} else {
					subNivelMap.get(baseSubNivel).add(this.campo);
				}
				this.campos.add(this.campo);
			}
		}
		
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
		this.select = false;
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
		this.campos.remove(this.campo);
		String novoSubNivel = this.campo.getNomeSubnivel().trim().toLowerCase().replace(" ", "");
		subNivelMap.get(novoSubNivel).remove(this.campo);
		this.campo = new Campo();
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
		List<SubNivel> subniveis2 = new ArrayList<SubNivel>();
		
		for (String sub : subNivelMap.keySet()) {
			SubNivel novo = new SubNivel();
			List<Campo> campos = subNivelMap.get(sub);
			novo.setNome(campos.get(0).getNomeSubnivel().replace(" ", "").toLowerCase());
			novo.setLabel(campos.get(0).getLabelSubnivel());
			novo.setCampos(campos);
			subniveis.add(novo);
		}

		for (int i = subniveis.size()-1 ; i > -1; i--) {
			subniveis2.add(subniveis.get(i));
		}
		
		byte[] bytes = VelocityUtil.merge("template-schema.vm", new String[]{"documento", "subniveis"}, new Object[]{documento, subniveis2});
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

	@Override
	protected BaseService<Integer, Documento> retrieveEntityService() {
		// TODO Auto-generated method stub
		return null;
	}
}