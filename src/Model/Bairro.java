package Model;

import java.util.ArrayList;
import java.util.List;

public class Bairro implements Comparable<Bairro> {
	private String nome; 
	private List<Logradouro> listaLogradouros = new ArrayList<Logradouro>();
		
	public Bairro(String nome) {
		super();
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Logradouro> getListaLogradouros() {
		return listaLogradouros;
	}
	public void setListaLogradouros(List<Logradouro> listaLogradouros) {
		this.listaLogradouros = listaLogradouros;
	}
	
	
	
	@Override
	public String toString() {
		return getNome();
	}
	@Override
	public int compareTo(Bairro bairro2) {
		return this.nome.compareTo(bairro2.getNome());
	}
	
	
	
}
