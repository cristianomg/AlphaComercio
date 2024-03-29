package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BuscarLogradouroView extends Application {
	private static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			SplitPane root = (SplitPane) FXMLLoader.load(getClass().getResource("/FXML/BuscarLogradouros.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Alpha Com�rcio");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
			primaryStage.setResizable(false);
			setStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		BuscarLogradouroView.stage = stage;
	}
	
	
}