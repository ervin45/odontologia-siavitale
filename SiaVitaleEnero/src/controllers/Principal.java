
package controllers;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import data.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {

	private static Stage stagePrincipal;
	private BorderPane rootLayout;

	public static Configuration config;
	public static SessionFactory fabrica;
	public Session sesion; 

	boolean estafull = true;
	
//	final String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

	public static String con = "fabiola";
//	public static String con = "javier";
//	public static String con = "vitale";
//	public static String con = "freddy";
	
	private double initx = 0;
	private double inity = 0;

	Rectangle2D rect;
	
	@Override
	public void start(Stage primaryStage) {
//		System.out.println("start!!!");
		Screen screen = Screen.getPrimary();
		final Rectangle2D bounds = screen.getVisualBounds();
		
		stagePrincipal = primaryStage;
		stagePrincipal.setTitle("SiaVitale V.0.1"); 
			
//		stagePrincipal.initStyle(StageStyle.TRANSPARENT);

		try
		{
			FXMLLoader cargador = new FXMLLoader(Principal.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) cargador.load(); 
			Scene escena = new Scene(rootLayout,Color.TRANSPARENT);
			stagePrincipal.setScene(escena);
			stagePrincipal.show();

			{
				rect = new Rectangle2D(stagePrincipal.getX(), stagePrincipal.getY(), stagePrincipal.getWidth(), stagePrincipal.getHeight());
				stagePrincipal.setX(bounds.getMinX());
				stagePrincipal.setY(bounds.getMinY());
				stagePrincipal.setWidth(bounds.getWidth());
				stagePrincipal.setHeight(bounds.getHeight());				
			}
			
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		
	    rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent me) {
	            initx = me.getScreenX() - stagePrincipal.getX();// - me.getSceneX(); 
	            inity = me.getScreenY() - stagePrincipal.getY();
	        }
	    });
	    rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent me) {
	            stagePrincipal.setX(me.getScreenX() - initx);
	            stagePrincipal.setY(me.getScreenY() - inity);
	            
	        }
	    });

	    rootLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getClickCount() == 2)
				{
					if (!estafull)
					{
						rect = new Rectangle2D(stagePrincipal.getX(), stagePrincipal.getY(), stagePrincipal.getWidth(), stagePrincipal.getHeight());
						stagePrincipal.setX(bounds.getMinX());
						stagePrincipal.setY(bounds.getMinY());
						stagePrincipal.setWidth(bounds.getWidth());
						stagePrincipal.setHeight(bounds.getHeight());
						estafull = true;
					}else
					{
						stagePrincipal.setX(rect.getMinX());
						stagePrincipal.setY(rect.getMinY());
						stagePrincipal.setWidth(rect.getWidth());
						stagePrincipal.setHeight(rect.getHeight());
						estafull = false;
					}
				}
			}
	    	
	    });
	    
	}
	
	public static void main(String[] args) {
		System.out.println("main!!!");
		initdata();
		launch(args);
	}

	public static void initdata() {	
		try{
			System.out.println("init!!!");
			config = new Configuration();
			config.addAnnotatedClass(Horario.class);
			config.addAnnotatedClass(Turno.class);
			config.addAnnotatedClass(Cargo.class);
			config.addAnnotatedClass(Ambiente.class);			
			config.addAnnotatedClass(Almacen.class);			
			config.addAnnotatedClass(AArea.class);
			config.addAnnotatedClass(Banco.class);			
			config.addAnnotatedClass(CentrodeCosto.class);
			config.addAnnotatedClass(Ciudad.class);
			config.addAnnotatedClass(CuentaBancaria.class);
			config.addAnnotatedClass(CuentaBancaria2.class);
			config.addAnnotatedClass(CuentaContable.class);
			config.addAnnotatedClass(PersonaContactoBanco.class);
			config.addAnnotatedClass(DescriGral.class);
			config.addAnnotatedClass(DetDocProveedor.class);
			config.addAnnotatedClass(DocProveedor.class);
			config.addAnnotatedClass(Educacion.class);
			config.addAnnotatedClass(Empleado.class);
			config.addAnnotatedClass(Esfuerzo.class);
			config.addAnnotatedClass(Estado.class);
			config.addAnnotatedClass(Experiencia.class);
			config.addAnnotatedClass(GrupoProducto.class);
			config.addAnnotatedClass(Iva.class);
			config.addAnnotatedClass(Marca.class);
			config.addAnnotatedClass(Material_req.class);
			config.addAnnotatedClass(MovBancario.class);
			config.addAnnotatedClass(Movimiento.class);
			config.addAnnotatedClass(Movinventario.class);
			config.addAnnotatedClass(Municipio.class);
			config.addAnnotatedClass(Parroquia.class);
			config.addAnnotatedClass(Presentacion.class);
			config.addAnnotatedClass(PresentacionProducto.class);
			config.addAnnotatedClass(Prioridad.class);
			config.addAnnotatedClass(Producto.class);
			config.addAnnotatedClass(ProductoMarca.class);
			config.addAnnotatedClass(Proveedor.class);
			config.addAnnotatedClass(Requisicion.class);
			config.addAnnotatedClass(Responsabilidad.class);
			config.addAnnotatedClass(Riesgo.class);
			config.addAnnotatedClass(TipoCuentaBancaria.class);
			config.addAnnotatedClass(TipoDocProveedor.class);
			config.addAnnotatedClass(TipoMovimiento.class);
			config.addAnnotatedClass(TipoUndMedida.class);
			config.addAnnotatedClass(Ubicacion.class);
			config.addAnnotatedClass(UndMedida.class);
			config.addAnnotatedClass(Unidad.class);
			config.addAnnotatedClass(Personal.class);
			config.addAnnotatedClass(Paciente.class);
			config.addAnnotatedClass(Doctor.class);
			config.addAnnotatedClass(CitaOdontologica.class);
			config.addAnnotatedClass(ServicioOdontologico.class);
//			IGUAL QUE = config.configure();

			Properties propiedades = new Properties();
			propiedades.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");

			if (con=="local")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3309/vitale"); // SHADDAY
				propiedades.setProperty("hibernate.connection.username", "prueba"); //local
				propiedades.setProperty("hibernate.connection.password", "");  //local				
			} 
			
			if (con=="javier")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3309/vitale");
				propiedades.setProperty("hibernate.connection.username", "prueba");
				propiedades.setProperty("hibernate.connection.password", "");				
			} 

			if (con=="fabiola")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3307/Datos");// mi base de datos
				propiedades.setProperty("hibernate.connection.username", "root");
				propiedades.setProperty("hibernate.connection.password", "root");				
			}
			
			if (con=="freddy")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3309/Datos");// mi base de datos
				propiedades.setProperty("hibernate.connection.username", "vitale");
				propiedades.setProperty("hibernate.connection.password", "");				
			} 			

			if (con=="vitale")
			{
				propiedades.setProperty("hibernate.connection.url","jdbc:mysql://vitale/vitale");
				propiedades.setProperty("hibernate.connection.username", "vitalemde");
				propiedades.setProperty("hibernate.connection.password", "vitale");
			}
			
			propiedades.setProperty("hibernate.connection.pool_size","2");
			propiedades.setProperty("hibernate.current_session_context_class","thread");
			
	        propiedades.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");
			propiedades.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
			propiedades.setProperty("hibernate.show_sql","true");
			
			propiedades.setProperty("hibernate.connection.zeroDateTimeBehavior","convertToNull");   // nuevo 2013 -04-20 

			config.addProperties(propiedades);
// 			FIN DE IGUAL QUE!!!

//			fabrica = config.buildSessionFactory();

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();        
		    fabrica = config.buildSessionFactory(serviceRegistry);
			
//			System.out.println("Ya se inicializó toda la data!!!");

//			new SchemaExport(config).create(true,true);   // SE USA PARA CREAR LAS TABLAS EN LA BD!!!
		}catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public static Stage getStagePrincipal(){
		return stagePrincipal;
	}

}
