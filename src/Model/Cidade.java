package Model;

import java.util.HashMap;

public class Cidade implements Comparable<Cidade> {

	private String nome;
	private HashMap<String, Bairro> bairros = new HashMap<String, Bairro>();
	
	public Cidade(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public HashMap<String, Bairro> getBairros() {
		return bairros;
	}

	public void setBairros(HashMap<String, Bairro> bairros) {
		this.bairros = bairros;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int compareTo(Cidade cidade2) {
		
		return this.nome.compareTo(cidade2.getNome());
	}
	
	
	
}
