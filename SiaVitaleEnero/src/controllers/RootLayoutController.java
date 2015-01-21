package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootLayoutController {

	DescriGralV DescriGralVentana;
	GrupoV GrupoVentana;
	UndMedidaV UndMedidaVentana;
	PresentacionV PresentacionVentana;
	MarcaV MarcaVentana;
	MovimientoInventarioV MovimientoInventarioVentana;
	ProductoV ProductoVentana;
	ProveedorV ProveedorVentana;
	CentrodeCostoV CentrodeCostoVentana;
	BancoV BancoVentana;
	CuentaBancariaV CuentaBancariaVentana;
	InstrumentosBancariosV InstrumentosBancariosVentana;
	//TipoCuentaBancariaV TipoCuentaBancariaVentana;
	//MovBancarioV MovBancarioVentana;
	CuentaContableV CuentaContableVentana;
	DocProveedorV DocProveedorVentana;
	
	// ----------------------- Fabiola ----------------------- // 
	AmbienteV AmbienteVentana;
	EducacionV EducacionVentana;
	EsfuerzoV EsfuerzoVentana;
	ExperienciaV ExperienciaVentana;
	ResponsabilidadV ResponsabilidadVentana;
	RiesgoV RiesgoVentana;
	CargoV CargoVentana;
	PersonalV PersonalVentana;
	MontosV MontosVentana;
	ConfProductoV ConfProductoVentana;
	ConfInventarioV ConfInventarioVentana;
	RequisicionV RequisicionVentana;
	
	// ----------------------- Fin Fabiola ------------------- // 
	
	HorariosTurnosV HorariosTurnosVentana;
	CitasOdontologicasV CitasOdonotologicasVentana;
	
	@FXML
	private void botonDescriGral() {
//		if (DescriGralVentana==null)
//		{
		DescriGralVentana = null;
		DescriGralVentana = new DescriGralV();
		DescriGralVentana.start(new Stage());
		System.out.println("Ejecuta la ventana???");			
//		}else
//		{
//			DescriGralVentana.mostrar();
//		}
	}

	@FXML
	private void botonMovimientoInventario() {
		MovimientoInventarioVentana = null;
		if (MovimientoInventarioVentana==null)
		{
			MovimientoInventarioVentana = new MovimientoInventarioV();
			MovimientoInventarioVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			MovimientoInventarioVentana.mostrar();
		}
	}

	
	@FXML
	private void botonGrupo() {
		if (GrupoVentana==null)
		{
			GrupoVentana = new GrupoV();
			GrupoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			GrupoVentana.mostrar();
		}
	}

	@FXML
	private void botonUndMedida() {
		if (UndMedidaVentana==null)
		{
			UndMedidaVentana = new UndMedidaV();
			UndMedidaVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			UndMedidaVentana.mostrar();
		}
	}

	@FXML
	private void botonPresentacion() {
		if (PresentacionVentana==null)
		{
			PresentacionVentana = new PresentacionV();
			PresentacionVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			PresentacionVentana.mostrar();
		}
	}

	@FXML
	private void botonMarca() {
		if (MarcaVentana==null)
		{
			MarcaVentana = new MarcaV();
			MarcaVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			MarcaVentana.mostrar();
		}
	}

	@FXML
	private void botonProducto() {
		ProductoVentana = null;
		
		if (ProductoVentana==null)
		{
			ProductoVentana = new ProductoV();
			ProductoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			ProductoVentana.mostrar();
		}
	}
	
	@FXML
	private void botonProveedor() {
//		if (ProveedorVentana==null)
//		{
			ProveedorVentana = null;
			ProveedorVentana = new ProveedorV();
			ProveedorVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
//		}else
//		{
//			ProveedorVentana.mostrar();
//		}
	}

	@FXML
	private void botonDocProveedor() {
		if (DocProveedorVentana==null)
		{
			DocProveedorVentana = new DocProveedorV();
			DocProveedorVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			DocProveedorVentana.mostrar();
		}
	}

	@FXML
	private void botonCentrodeCosto() {
		if (CentrodeCostoVentana==null)
		{
			CentrodeCostoVentana = new CentrodeCostoV();
			CentrodeCostoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			CentrodeCostoVentana.mostrar();
		}
	}

	@FXML
	private void botonBanco() {
		if (BancoVentana==null)
		{
			BancoVentana = new BancoV();
			BancoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			BancoVentana.mostrar();
		}
	}

	@FXML
	private void botonCuentaBancaria() {
		InstrumentosBancariosVentana=null;
		if (InstrumentosBancariosVentana==null){
			InstrumentosBancariosVentana = new InstrumentosBancariosV();
			InstrumentosBancariosVentana.start(new Stage());			
		}else{
			InstrumentosBancariosVentana.mostrar();
		}
		
	}

	@FXML
	private void botonCuentaContable() {
		if (CuentaContableVentana==null)
		{
			CuentaContableVentana = new CuentaContableV();
			CuentaContableVentana.start(new Stage());
			System.out.println("Ejecuta la ventana???");			
		}else
		{
			CuentaContableVentana.mostrar();
		}
	}
	
	// ----------------------- Fabiola ----------------------- //
	@FXML
	private void botonCualidadAmbiente(){
		if (AmbienteVentana==null)
		{
			AmbienteVentana = new AmbienteV();
			AmbienteVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Ambiente? Root Layout Controller");
		}else
		{
			AmbienteVentana.mostrar();
		}		
	}
	@FXML
	private void botonCualidadEducacion(){
		if(EducacionVentana==null)
		{
			EducacionVentana = new EducacionV();
			EducacionVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Educación? Root Layout Controller");
		}else
		{
			EducacionVentana.mostrar();
		}
	}
	@FXML
	private void botonCualidadEsfuerzo(){
		if(EsfuerzoVentana==null)
		{
			EsfuerzoVentana = new EsfuerzoV();
			EsfuerzoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Esfuerzo? Root Layout Controller");
		}else
		{
			EsfuerzoVentana.mostrar();
		}
	}
	
	@FXML
	private void botonCualidadExperiencia(){
		if (ExperienciaVentana==null){
			ExperienciaVentana = new ExperienciaV();
			ExperienciaVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Experiencia? Root Layout Controller");
		}else
		{
			ExperienciaVentana.mostrar();
		}
	}
	
	@FXML
	private void botonCualidadResponsabilidad(){
		if (ResponsabilidadVentana==null){
			ResponsabilidadVentana = new ResponsabilidadV();
			ResponsabilidadVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Responsabilidad? Root Layout Controller");
		}else
		{
			ResponsabilidadVentana.mostrar();
		}
	}
	
	@FXML
	private void botonCualidadRiesgo(){
		if(RiesgoVentana == null){
			RiesgoVentana = new RiesgoV();
			RiesgoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Riesgo? Root Layout Controller");
		}else{
			RiesgoVentana.mostrar();
		}
	}

	@FXML
	private void botonPersonal(){
		if(PersonalVentana == null){
			PersonalVentana = new PersonalV();
			PersonalVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Riesgo? Root Layout Controller");
		}else{
			PersonalVentana.mostrar();
		}
	}

	
	@FXML
	private void botonCargo(){
		if(CargoVentana == null){
			CargoVentana = new CargoV();
			CargoVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Cargo? Root Layout Controller");
		}else{
			CargoVentana.mostrar();
		}
	}
	
	@FXML
	private void botonMontos(){
		if(MontosVentana == null){
			MontosVentana = new MontosV();
			MontosVentana.start(new Stage());
			System.out.println("Ejecuta la ventana de Montos? Root Layout Controller");
		}else{
			MontosVentana.mostrar();
		}
	}
	

	@FXML
	private void botonConfProducto(){
//		if(ConfInventarioVentana == null){
		ConfProductoVentana = null;
		ConfProductoVentana = new ConfProductoV();
		ConfProductoVentana.start(new Stage());
		System.out.println("Ejecuta la ventana de Montos? Root Layout Controller");
//		}else{
//			ConfInventarioVentana.mostrar();
//		}
	}

	// -------------------- Fin Fabiola ---------------------- //

	@FXML
	private void botonConfInventario(){
//		if(ConfInventarioVentana == null){
		ConfInventarioVentana = null;
		ConfInventarioVentana = new ConfInventarioV();
		ConfInventarioVentana.start(new Stage());
		System.out.println("Ejecuta la ventana de Montos? Root Layout Controller");
//		}else{
//			ConfInventarioVentana.mostrar();
//		}
	}

	@FXML
	private void botonRequisicion(){
		RequisicionVentana = null;
		RequisicionVentana = new RequisicionV();
		RequisicionVentana.start(new Stage());
		System.out.println("Ejecuta la ventana de Montos? Root Layout Controller");
	}
	
	 @FXML
	 private void botonHorariosTurnos(){
		 System.out.println("horario... turnos");
		 HorariosTurnosVentana = null;
		 HorariosTurnosVentana =  new HorariosTurnosV();
		 HorariosTurnosVentana.start(new Stage());
		 System.out.println("Ejecuta la ventana de horarios turnos Root Layout Controller");
	 }

	 @FXML
	 private void botonCitas(){
		 System.out.println("...citas....");
		 CitasOdonotologicasVentana = null;
		 CitasOdonotologicasVentana = new CitasOdontologicasV();
		 CitasOdonotologicasVentana.start(new Stage());		 
	 }
}
