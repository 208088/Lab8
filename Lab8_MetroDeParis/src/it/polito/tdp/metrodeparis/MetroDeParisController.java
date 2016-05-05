package it.polito.tdp.metrodeparis;

	import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.ResourceBundle;

import it.polito.tpd.metrodeparis.model.Fermata;
import it.polito.tpd.metrodeparis.model.Fermata2;
import it.polito.tpd.metrodeparis.model.Model2;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.TextArea;

	public class MetroDeParisController {

		private List<Fermata> ferm= new ArrayList<Fermata>();
		
		private Model2 model= new Model2();
		@FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private ComboBox<String> comboPartenza;

	    @FXML
	    private ComboBox<String> comboArrivo;

	    @FXML
	    private Button btnCalcola;

	    @FXML
	    private TextArea txtArea;

	    @FXML
	    void DoCalcola(ActionEvent event) {
	    	String f1=comboPartenza.getValue();
	    	String f2=comboArrivo.getValue();
	    	Fermata p=model.getFermataByNome(f1);
	    	Fermata a=model.getFermataByNome(f2);
	    	
	    	List<Fermata2> cammino= model.trovaCammino(p, a);
	    	if(cammino==null)
	    	{
	    		txtArea.setText("Queste due fermate non risultano collegate..");
	    		return;
	    	}
	    	double tempo=0.0;
	    	txtArea.appendText(String.format("Percorso minimo da %s a %s : \n", f1, f2));
	    	for (Fermata f : cammino) {
				txtArea.appendText(f.getNome()+ "\n");
			}
	    	tempo+=model.getWeight()*60;
	    	double sosta=30/60*(cammino.size()-2);
	    	//int secTot=30*cammino.size()+(int)tempo*3600;
	    	//int ora=(int)secTot/3600;
	    	//int minuti = ((int)(secTot/60))- (ora*60);
	    	tempo+=sosta;
	    	txtArea.appendText("\n Tempo impiegato : " + (float)(Math.ceil(tempo*Math.pow(10, 2))/Math.pow(10, 2))+" minuti");
	    }

	    @FXML
	    void initialize() {
	        assert comboPartenza != null : "fx:id=\"comboPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert comboArrivo != null : "fx:id=\"comboArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	       
	        ferm= model.getFermate();
	        for (Fermata a : ferm) 
	        {
				comboArrivo.getItems().add(a.getNome());
				comboPartenza.getItems().add(a.getNome());
			}
	        
	        model.creaGrafo();
	    }
}
