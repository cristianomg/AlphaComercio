package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TableView;

public class DesempenhoController implements Initializable{
	
    @FXML
    private NumberAxis axisY;

    @FXML
    private CategoryAxis axisX;
	
    @FXML
    private LineChart<NumberAxis, CategoryAxis> graficoBuscas;
    
    @FXML
    private TableView<Log> tabelaLog;

    private int posicao;
    private Sistema sistema = Sistema.getInstance();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarGrafico();
		carregarLog();
	}
	
	public void carregarGrafico() {
		axisY.setLabel("microssegundo");
		Series serie = new Series();
		serie.setName("Buscas por CEP");
		posicao = 1;
		sistema.listaTemposBuscarCep.stream().forEach(x -> {
			serie.getData().add(new Data(String.valueOf(posicao), x));
			posicao ++;
		});
		Series serie2 = new Series();
		serie2.setName("Buscas por Logradouro");
		posicao = 1;
		sistema.listaTemposBuscarLogradouros.stream().forEach(x -> {
			serie2.getData().add(new Data(String.valueOf(posicao), x));
			posicao ++;
		});	
		graficoBuscas.getData().addAll(serie, serie2);
	}
	
	public void carregarLog() {
		ObservableList<Log> logs = FXCollections.observableList(sistema.logs);
		tabelaLog.getItems().setAll(logs);
	}
}
