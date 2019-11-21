package Model;

public class Log {

	private String tipoOperacao;
	private long tempo;
	private String unidade;
	
	public Log(String tipoOperacao, long tempo, String unidade) {
		super();
		this.tipoOperacao = tipoOperacao;
		this.tempo = tempo;
		this.unidade = unidade;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	
	
}
