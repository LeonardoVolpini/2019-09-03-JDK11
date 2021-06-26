/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.VerticeRaggiungibile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	this.txtResult.clear();
    	if (!model.isGrafoCreato()) { //con variabile booleane all'interno del model
    		this.txtResult.setText("Prima crea il grafo!!!");
    		return;
    	}
    	String v= this.boxPorzioni.getValue();
    	if (v==null) {
    		this.txtResult.setText("Errore, selezionare un tipo di porzione");
    		return;
    	}
    	String nString =this.txtPassi.getText();
    	if (nString.isEmpty()) { //superfluo, basta il try e catch
    		this.txtResult.setText("Inserire un numero minimo di passi");
    		return;
    	}
    	int n; //o int o float...
    	try {
    		n=Integer.parseInt(nString);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inseire un valore numero come numero di passi");
    		return;
    	}
    	List<String> cammino= model.percorsoMax(v, n);
    	this.txtResult.setText("Il cammino massimo trovato con "+n+" passi è:\n");
    	for (String s : cammino)
    		this.txtResult.appendText(s+"\n");
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...");
    	if (!model.isGrafoCreato()) {
    		this.txtResult.setText("Errore, creare prima il grafo");
    		return;
    	}
    	String v= this.boxPorzioni.getValue();
    	if (v==null) {
    		this.txtResult.setText("Errore, selezionare un tipo di porzione");
    		return;
    	}
    	List<VerticeRaggiungibile> elenco = model.adiacenti(v);
    	this.txtResult.appendText("Raggiungibili: \n");
    	for (VerticeRaggiungibile ver : elenco) {
    		this.txtResult.appendText(ver.getVertice()+" "+ver.getPeso()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	String cString =this.txtCalorie.getText();
    	if (cString.isEmpty()) { //superfluo, basta il try e catch
    		this.txtResult.setText("Inserire un numero di calore");
    		return;
    	}
    	int c; //o int o float...
    	try {
    		c=Integer.parseInt(cString);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inseire un valore numero come numero di calore");
    		return;
    	}
    	this.model.creaGrafo(c);
    	this.txtResult.setText("GRAFO CREATO:\n");
    	this.txtResult.appendText("# Vertici: "+model.getNumVertici() );
    	this.txtResult.appendText("\n# Archi: "+model.getNumArchi() );
    	this.boxPorzioni.getItems().clear(); //pulisco le varie comboBox dipendenti dal grafo
    	this.boxPorzioni.getItems().addAll(model.getVertici()); //creo le varie comboBox dipendenti dal grafo
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
