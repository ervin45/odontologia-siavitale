package controllers;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NuevaCitaOdontologicaV extends Application {
	
	Stage escenario0;
	
	@Override
	public void start(Stage escenario) {
		escenario.setTitle("Nueva Cita Odontologica");
		escenario0 = escenario;
		try {
			FXMLLoader cargador2 = new FXMLLoader(Principal.class.getResource("NuevaCitaOdontologica.fxml"));
			AnchorPane root = (AnchorPane) cargador2.load();
			escenario0.setScene(new Scene(root, Color.TRANSPARENT));
			escenario0.initModality(Modality.NONE);
			escenario0.initOwner(Principal.getStagePrincipal());
			escenario0.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mostrar()
	{
		escenario0.show();	
	}
	
}
