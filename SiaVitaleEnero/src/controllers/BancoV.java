package controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BancoV extends Application {
	
	Stage escenario0;
	
	@Override
	public void start(Stage escenario) {
		escenario.setTitle("Bancos");
		escenario0 = escenario;
		try {
			FXMLLoader cargador2 = new FXMLLoader(Principal.class.getResource("DescriGralView.fxml"));
			cargador2.setController(new BancoController());
			AnchorPane root = (AnchorPane) cargador2.load();
			escenario0.setScene(new Scene(root, Color.TRANSPARENT));
//			escenario0.initStyle(StageStyle.UNDECORATED);
//			escenario0.setResizable(false);
			escenario0.initModality(Modality.NONE);
			escenario0.initOwner(Principal.getStagePrincipal());
			escenario0.show();
			System.out.println("Muestra la Ventana???");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//	    root.setOnMousePressed(new EventHandler<MouseEvent>() {
//	        public void handle(MouseEvent me) {
//	            initx = me.getScreenX() - Principal.getStagePrincipal().getX();// - me.getSceneX(); 
//	            inity = me.getScreenY() - Principal.getStagePrincipal().getY();
//	        	System.out.println("Pasó por aquí... Mouse Pressed!!!");
//	        }
//	    });
//
//	    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//	        public void handle(MouseEvent me) {
//	        	Principal.getStagePrincipal().setX(me.getScreenX() - initx);
//	        	Principal.getStagePrincipal().setY(me.getScreenY() - inity);
//	        	System.out.println("Pasó por aquí... Mouse Dragged!!!");
//	        }
//	    });

	}

	public void mostrar()
	{
		escenario0.show();	
	}
	
}
