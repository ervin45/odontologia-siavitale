package controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MontosV extends Application {

Stage escenario0;
	
	@Override
	public void start(Stage escenario) {
		escenario.setTitle("Montos");
		escenario0 = escenario;
		try {
			FXMLLoader cargador2 = new FXMLLoader(Principal.class.getResource("MontosView.fxml"));
			cargador2.setController(new MontosController());
			AnchorPane root = (AnchorPane) cargador2.load();
			escenario0.setScene(new Scene(root, Color.TRANSPARENT));
//			escenario0.initStyle(StageStyle.UNDECORATED);
//			escenario0.setResizable(false);
			escenario0.initModality(Modality.NONE);
			escenario0.initOwner(Principal.getStagePrincipal());
			escenario0.show();
			System.out.println("Muestra la Ventana de Montos???");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void mostrar()
	{
		escenario0.show();	
	}
}