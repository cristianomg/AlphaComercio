package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import Model.Bairro;
import Model.Cidade;
import Model.Estado;
import Model.Log;
import Model.Logradouro;

public class Sistema {

	private static Sistema uniqueInstance;
	private HashMap<String, Logradouro> estruturaCep = new HashMap<String, Logradouro>();
	public HashMap<String, Estado> estados = new HashMap<String, Estado>();
	public long tempoCarregarArquivos;
	public List<Long> listaTemposBuscarCep = new ArrayList<Long>();
	public List<Long> listaTemposBuscarLogradouros = new ArrayList<Long>();
	public List<Log> logs = new ArrayList<Log>();

	private Sistema() {
		carregarArquivos();
	}

	public static synchronized Sistema getInstance() {

		if (uniqueInstance == null) {
			uniqueInstance = new Sistema();
		}
		return uniqueInstance;
	}

	public void carregarArquivos() {
		long timeInitial = System.currentTimeMillis();
		try {
			Stream<Path> uf = Files.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Uf"));
			uf.forEach(x -> distribuirUnidadesFederativas(String.valueOf(x.getFileName())));
			uf.close();

			Stream<Path> cidade = Files.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Cidades"));
			cidade.forEach(x -> distribuirCidades(String.valueOf(x.getFileName())));
			cidade.close();

			Stream<Path> bairro = Files.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Bairros"));
			bairro.forEach(x -> distribuirBairros(String.valueOf(x.getFileName())));
			bairro.close();

			Stream<Path> logradouros = Files
					.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Logradouros"));
			logradouros.forEach(x -> distribuirLogradouros(String.valueOf(x.getFileName())));
			logradouros.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long timeFinal = System.currentTimeMillis();
		tempoCarregarArquivos = (timeFinal - timeInitial) / 1000;
		logs.add(new Log("Carregar arquivos para memória", tempoCarregarArquivos, "s"));
	}

	public void atualizarDados() {
		long timeInitial = System.currentTimeMillis();
		try {
			Stream<Path> uf = Files.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Uf"));
			uf.forEach(x -> distribuirUnidadesFederativas(String.valueOf(x.getFileName())));
			uf.close();

			Stream<Path> cidade = Files.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Cidades"));
			cidade.forEach(x -> distribuirCidades(String.valueOf(x.getFileName())));
			cidade.close();

			Stream<Path> bairro = Files.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Bairros"));
			bairro.forEach(x -> distribuirBairros(String.valueOf(x.getFileName())));
			bairro.close();

			Stream<Path> logradouros = Files
					.list(Path.of("C:\\Users\\ayrto\\Dropbox\\javafx\\BuscaCEP\\Arquivos\\Logradouros"));
			logradouros.forEach(x -> distribuirLogradouros(String.valueOf(x.getFileName())));
			logradouros.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long timeFinal = System.currentTimeMillis();
		tempoCarregarArquivos = (timeFinal - timeInitial) / 1000;
		logs.add(new Log("Atualizar arquivos para memória", tempoCarregarArquivos, "s"));
	}
	
	public void distribuirUnidadesFederativas(String caminho) {
		FileReader arq = null;
		try {
			arq = new FileReader("Arquivos/Uf/" + caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			linha = lerArq.readLine();
			while (linha != null) {
				if (linha.substring(0, 1).equals("D")) {
					String uf = linha.substring(3, 5).trim();
					if (estados.get(uf) == null) {
						estados.put(uf, new Estado(uf));
					}
				}
				linha = lerArq.readLine();
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void distribuirCidades(String caminho) {
		FileReader arq = null;
		try {
			arq = new FileReader("Arquivos/Cidades/" + caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			linha = lerArq.readLine();
			while (linha != null) {
				if (linha.substring(0, 1).equals("D")) {
					String uf = linha.substring(3, 5).trim();
					String cep = linha.substring(91, 99).trim();
					String cidade = linha.substring(19, 91).trim();
					Estado estado = estados.get(uf);
					if (estado == null) {
						estados.put(uf, new Estado(uf));
						estados.get(uf).getCidades().put(cidade, new Cidade(cidade));
						estruturaCep.put(cep, new Logradouro(estado, new Cidade(cidade), cep));
					} else {
						if (estado.getCidades().get(cidade) == null) {
							estado.getCidades().put(cidade, new Cidade(cidade));
							estruturaCep.put(cep, new Logradouro(estado, new Cidade(cidade), cep));
						}
					}
				}
				linha = lerArq.readLine();
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void distribuirBairros(String caminho) {
		FileReader arq = null;
		try {
			arq = new FileReader("Arquivos/Bairros/" + caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			linha = lerArq.readLine();
			while (linha != null) {
				if (linha.substring(0, 1).equals("D")) {
					String uf = linha.substring(1, 3).trim();
					String nomeCidade = linha.substring(17, 89).trim();
					String nomeBairro = linha.substring(102, 174).trim();
					Estado estado = estados.get(uf);
					if (estado == null) {
						estados.put(uf, new Estado(uf));
						estados.get(uf).getCidades().put(nomeCidade, new Cidade(nomeCidade));
						estados.get(uf).getCidades().get(nomeCidade).getBairros().put(nomeBairro,
								new Bairro(nomeBairro));
					} else {
						Cidade cidade = estado.getCidades().get(nomeCidade);
						if (cidade == null) {
							estados.get(uf).getCidades().put(nomeCidade, new Cidade(nomeCidade));
							estados.get(uf).getCidades().get(nomeCidade).getBairros().put(nomeBairro,
									new Bairro(nomeBairro));
						} else {
							Bairro bairro = cidade.getBairros().get(nomeBairro);
							if (bairro == null) {
								estados.get(uf).getCidades().get(nomeCidade).getBairros().put(nomeBairro,
										new Bairro(nomeBairro));
							}
						}
					}
				}
				linha = lerArq.readLine();
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void distribuirLogradouros(String caminho) {
		FileReader arq = null;
		try {
			arq = new FileReader("Arquivos/Logradouros/" + caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			linha = lerArq.readLine();

			while (linha != null) {
				String uf = null;
				String nomeOficialLocalidade = null;
				String bairroInicialLogradouro = null;
				String cep = null;
				String Abreviatura = null;

				switch (linha.substring(0, 1)) {
				case "D":
					uf = linha.substring(1, 3);
					nomeOficialLocalidade = linha.substring(17, 89).trim();
					bairroInicialLogradouro = linha.substring(102, 174).trim();
					cep = linha.substring(518, 526).trim();
					Abreviatura = linha.substring(446, 482).trim();
					break;
				case "S":
					uf = linha.substring(1, 3);
					nomeOficialLocalidade = linha.substring(17, 89).trim();
					bairroInicialLogradouro = linha.substring(102, 174).trim();
					cep = linha.substring(518, 526).trim();
					Abreviatura = linha.substring(446, 482).trim();
					break;
				case "N":
					uf = linha.substring(1, 3);
					nomeOficialLocalidade = linha.substring(17, 89).trim();
					bairroInicialLogradouro = linha.substring(102, 174).trim();
					cep = linha.substring(518, 526).trim();
					Abreviatura = linha.substring(446, 482).trim();
					break;
				case "K":
					uf = linha.substring(1, 3);
					nomeOficialLocalidade = linha.substring(17, 89).trim();
					bairroInicialLogradouro = linha.substring(102, 174).trim();
					cep = linha.substring(518, 526).trim();
					Abreviatura = linha.substring(446, 482).trim();
					break;
				case "Q":
					uf = linha.substring(1, 3);
					nomeOficialLocalidade = linha.substring(17, 89).trim();
					bairroInicialLogradouro = linha.substring(102, 174).trim();
					cep = linha.substring(518, 526).trim();
					Abreviatura = linha.substring(446, 482).trim();
					break;
				}

				if (uf != null && nomeOficialLocalidade != null && bairroInicialLogradouro != null
						&& Abreviatura != null && cep != null) {
					Estado estado = estados.get(uf);
					if (estado != null) {
						Cidade cidade = estado.getCidades().get(nomeOficialLocalidade);
						if (cidade != null) {
							Bairro bairro = cidade.getBairros().get(bairroInicialLogradouro);
							if (bairro != null) {
								Logradouro logradouro = new Logradouro(estado, cidade, bairro, Abreviatura, cep);
								estruturaCep.put(cep, logradouro);
								if (!bairro.getListaLogradouros().contains(logradouro)) {
									bairro.getListaLogradouros().add(logradouro);
								}
							} else {
								Logradouro logradouro = new Logradouro(estado, cidade, bairro, Abreviatura, cep);
								bairro = new Bairro(bairroInicialLogradouro);
								bairro.getListaLogradouros().add(logradouro);
								cidade.getBairros().put(bairroInicialLogradouro, bairro);
								estruturaCep.put(cep, logradouro);
							}
						} else {
							cidade = new Cidade(nomeOficialLocalidade);
							Bairro bairro = new Bairro(bairroInicialLogradouro);
							Logradouro logradouro = new Logradouro(estado, cidade, bairro, Abreviatura, cep);
							bairro.getListaLogradouros().add(logradouro);
							cidade.getBairros().put(bairroInicialLogradouro, bairro);
							estado.getCidades().put(nomeOficialLocalidade, cidade);
							estruturaCep.put(cep, logradouro);
						}
					} else {
						estado = new Estado(uf);
						Cidade cidade = new Cidade(nomeOficialLocalidade);
						Bairro bairro = new Bairro(bairroInicialLogradouro);
						Logradouro logradouro = new Logradouro(estado, cidade, bairro, Abreviatura, cep);
						bairro.getListaLogradouros().add(logradouro);
						cidade.getBairros().put(bairroInicialLogradouro, bairro);
						estado.getCidades().put(nomeOficialLocalidade, cidade);
						estruturaCep.put(cep, logradouro);
						estados.put(uf, estado);
					}
				}

				linha = lerArq.readLine();
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Logradouro buscarCep(String cep) {
		long timeInitial = System.nanoTime();
		Logradouro logradouro = estruturaCep.get(cep);
		long timeFinal = System.nanoTime();
		long tempoBuscarCep = (timeFinal - timeInitial) / 1000;
		this.listaTemposBuscarCep.add(tempoBuscarCep);
		logs.add(new Log("Buscar por Cep", tempoBuscarCep, "µs"));
		return logradouro != null ? logradouro : null;
	}

	public List<Logradouro> buscarLogradouros(String uf, String cidade, String bairro) {
		long timeInitial = System.nanoTime();
		List<Logradouro> listaLogradouros = estados.get(uf).getCidades().get(cidade).getBairros().get(bairro)
				.getListaLogradouros();
		long timeFinal = System.nanoTime();
		long tempoBuscarLogradouros = (timeFinal - timeInitial) / 1000;
		this.listaTemposBuscarLogradouros.add(tempoBuscarLogradouros);
		logs.add(new Log("Buscar Logradouros por bairro", tempoBuscarLogradouros, "µs"));
		return listaLogradouros;

	}
}
