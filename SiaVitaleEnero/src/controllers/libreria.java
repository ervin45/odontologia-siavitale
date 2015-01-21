package controllers;

import data.Movinventario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class libreria {

	public static EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource();                
	            if (txt_TextField.getText().length() >= max_Lengh) {                    
	                e.consume();
	            }
	            
	            if(e.getCharacter().matches("[0-9.]")){ 
	                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
	                    e.consume();
	                }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
	                    e.consume(); 
	                }
	            }else{	            	
	                e.consume();
	            }
	        }
	    };
	}    

	public EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
	    return new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent e) {
	            TextField txt_TextField = (TextField) e.getSource();                
	            if (txt_TextField.getText().length() >= max_Lengh) {                    
	                e.consume();
	            }
	            if(e.getCharacter().matches("[A-Za-z ]")){ 

	            }else{
	                e.consume();
	            }
	        }
	    };
	}    


	public static ChangeListener<? super String> mayuscula(final TextField xx) {
		// TODO Auto-generated method stub
		return new ChangeListener<String>(){
		  	  @Override
		  	    public void changed(ObservableValue<? extends String> observable,
		  	            String oldValue, String newValue) {
		  		   xx.setText(newValue.toUpperCase());   
		  	    }
		  };
	}

	
	
    private class TableCellFormat extends TableCell<Movinventario, String>{
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty); 
            this.setText(item);
            this.setAlignment(Pos.BASELINE_RIGHT);
        }
    }

	
}
