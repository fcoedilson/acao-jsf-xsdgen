package br.org.acao.util.status;

public enum StatusAtendimentoAbastecimento {

	ATENDIDO, NEGADO;

	public String toString() {
		String string = null;
		switch (this) {
		case ATENDIDO:
			string = "Atendido";
			break;
		default:
			string = "Negado";
			break;
		}
		return string;
	};

}
