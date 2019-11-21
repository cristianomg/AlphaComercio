package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Bairro;
import Model.Cidade;
import Model.Estado;
import Model.Logradouro;
import View.BuscarCepView;
import View.BuscarLogradouroView;
import View.DesempenhoView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BuscarLogradourosController implements Initializable {
	@FXML
	private ChoiceBox<Bairro> choiceBairro;

	@FXML
	private Button btnFinalizar;

	@FXML
	private Button btnBuscarCep;

	@FXML
	private Button btnBuscarLogradouro;

	@FXML
	private TableView<Logradouro> tabelaLogradouro;

	@FXML
	private Button btnBuscar;

	@FXML
	private ChoiceBox<Estado> choiceUf;

	@FXML
	private ChoiceBox<Cidade> choiceCidade;
	
    @FXML
    private Button btnAtualizar;

	private Sistema sistema = Sistema.getInstance();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnBuscarLogradouro.setDisable(true);
		btnBuscarLogradouro.setOpacity(40);
		choiceUf.setTooltip(new Tooltip("Selecione a Unidade Federal"));
		choiceCidade.setTooltip(new Tooltip("Selecione a Cidade"));
		choiceBairro.setTooltip(new Tooltip("Selecione o Bairro"));
		carregarChoiceUf();
		try {
			choiceUf.getSelectionModel().selectedItemProperty().addListener((observador, estadoAntes, estadoNovo) -> {
				choiceCidade.getItems().setAll(new ArrayList<Cidade>());
				choiceBairro.getItems().setAll(new ArrayList<Bairro>());
				if (estadoNovo != null) {
					carregarChoiceCidade(estadoNovo.getCidades().values());
					choiceCidade.getSelectionModel().selectedItemProperty()
					.addListener((observador2, cidadeAntiga, cidadeNova) -> {
						choiceBairro.getItems().setAll(new ArrayList<Bairro>());
						if (cidadeNova != null) {
							carregarChoiceBairro(cidadeNova.getBairros().values());
						}
					});
				}
			});
		}catch (NullPointerException e) {
			choiceCidade.getItems().setAll(new ArrayList<Cidade>());
			choiceBairro.getItems().setAll(new ArrayList<Bairro>());
		}
	}

	public void btnBuscarCep_Action() {
		BuscarLogradouroView.getStage().close();
		BuscarCepView cepView = new BuscarCepView();
		cepView.start(new Stage());

	}

	public void btnFinalizar_Action() {
    	BuscarLogradouroView.getStage().close();
    	DesempenhoView finalizar = new DesempenhoView();
    	finalizar.start(new Stage());
	}

	public void btnBuscar_Action() {
		List<Logradouro> logradouros = null;
		if (choiceUf.getValue() != null && choiceCidade.getValue() != null && choiceBairro.getValue() != null) {
			logradouros = sistema.buscarLogradouros(choiceUf.getValue().getNome(), choiceCidade.getValue().getNome(),
					choiceBairro.getValue().getNome());
			ObservableList<Logradouro> logradourosObservados = FXCollections.observableList(logradouros);
			tabelaLogradouro.getItems().setAll(logradourosObservados);
		}

	}

	public void carregarChoiceUf() {
		List<Estado> estados = new ArrayList<Estado>();
		sistema.estados.values().forEach(x -> estados.add(x));
		Collections.sort(estados);
		choiceUf.getItems().setAll(estados);
	}

	public void carregarChoiceCidade(Collection<Cidade> cidades) {
		List<Cidade> cidadesOb = new ArrayList<Cidade>();
		cidades.forEach(x -> cidadesOb.add(x));
		Collections.sort(cidadesOb);
		choiceCidade.getItems().setAll(cidadesOb);
	}

	public void carregarChoiceBairro(Collection<Bairro> bairros) {
		List<Bairro> bairrosOb = new ArrayList<Bairro>();
		bairros.forEach(x -> bairrosOb.add(x));
		Collections.sort(bairrosOb);
		choiceBairro.getItems().setAll(bairrosOb);
	}

    public void btnAtualizar_Action() {
    	Alert confirmacao = new Alert(AlertType.CONFIRMATION);
    	confirmacao.setTitle("Atualização");
		confirmacao.setHeaderText("Confirmação de atualização dos dados");
		confirmacao.setContentText("Tem certeza deseja atualizar os dados?");
    	Optional<ButtonType> resultadoConfirmacao = confirmacao.showAndWait();
    	if (resultadoConfirmacao.isPresent() && resultadoConfirmacao.get() == ButtonType.OK) {
    		sistema.atualizarDados();    
        	Alert infoAlert = new Alert(AlertType.INFORMATION);
        	infoAlert.setTitle("Atualização");
    		infoAlert.setContentText("Sistema Atualizado.");
    		infoAlert.show();
    	}
    }
}
