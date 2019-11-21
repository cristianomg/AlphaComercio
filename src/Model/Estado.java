package Model;

import java.util.HashMap;

public class Estado implements Comparable<Estado>{

	private String nome;
	private HashMap<String, Cidade> cidades = new HashMap<String, Cidade>();
	
	public Estado(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public HashMap<String, Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(HashMap<String, Cidade> cidades) {
		this.cidades = cidades;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int compareTo(Estado estado) {
		return this.nome.compareTo(estado.getNome());
	}
	
	
	
	
}
