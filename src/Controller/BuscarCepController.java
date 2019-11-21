package Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Logradouro;
import View.BuscarCepView;
import View.BuscarLogradouroView;
import View.DesempenhoView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuscarCepController implements Initializable{

    @FXML
    private TextField txfBuscarCep;

    @FXML
    private TextField txfBairro;

    @FXML
    private Button btnBuscarCep;

    @FXML
    private Button btnBuscarLogradouro;

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField txfLogradouro;
    
    @FXML
    private Button btnFinalizar;
    
    @FXML
    private Button btnAtualizar;

    @FXML
    private TextField txfCidadeUF;
    private Sistema sistema = Sistema.getInstance();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnBuscarCep.setDisable(true);
		btnBuscarCep.setOpacity(40);
	}
    
    public void btnBuscarLogradouro_Action() {
    	BuscarCepView.getStage().close();
    	BuscarLogradouroView logradouroView = new BuscarLogradouroView();
    	logradouroView.start(new Stage());
    	
    }

    public void btnFinalizar_Action() {
    	BuscarCepView.getStage().close();
    	DesempenhoView finalizar = new DesempenhoView();
    	finalizar.start(new Stage());
    }

    public void btnBuscar_Action() {
    	Logradouro logradouro = sistema.buscarCep(txfBuscarCep.getText());
    	txfBairro.setText("");
    	txfCidadeUF.setText("");
    	txfLogradouro.setText("");
    	if (logradouro  != null) {
    		txfBairro.setText(logradouro.getBairro() != null ? logradouro.getBairro()+ "" : "");
    		txfCidadeUF.setText(logradouro.getCidade()+"/"+logradouro.getUf());
    		txfLogradouro.setText(logradouro.getRua());
    	}else {
    		Alert erro = new Alert(AlertType.ERROR);
    		erro.setTitle("Erro");
    		erro.setContentText("Cep não encontrado");
    		erro.show();
    	}
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
