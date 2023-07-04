/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	
    	if (this.cmbSquadra.getValue() == null) {
    		txtResult.appendText("Scegli una squadra");
    		return;
    	}
    	
    	// DefaultDirectedWeightedGraph<Team,DefaultWeightedEdge> graph = model.getGraph();
    	
    	Team team = this.cmbSquadra.getValue();
    	
    	List<Team> peggiori = model.getPeggiori(team);
    	List<Team> migliori = model.getMigliori(team);
    	
    	txtResult.setText("SQUADRE MIGLIORI:\n");
    		for (Team t : migliori)
    			txtResult.appendText(t.getName() + " (" + (t.getPunteggio()-team.getPunteggio()) + ")\n");

    		txtResult.appendText(" \n");	
    		
    	txtResult.appendText("SQUADRE PEGGIORI:\n");
    		for (Team t : peggiori)
    			txtResult.appendText(t.getName() + " (" + (team.getPunteggio()-t.getPunteggio()) + ")\n");

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	DefaultDirectedWeightedGraph<Team,DefaultWeightedEdge> graph = model.creaGrafo();
    	
    	txtResult.setText("Grafo creato con " + graph.vertexSet().size() + " vertici e " 
    			+ graph.edgeSet().size() + " archi.\n\n" );
    	
    	cmbSquadra.getItems().addAll(graph.vertexSet());
    	
    	this.btnClassifica.setDisable(false);
    	this.btnSimula.setDisable(false);

    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	model.run(Integer.parseInt(txtN.getText()), Integer.parseInt(txtX.getText()));
    	
    	txtResult.setText("Media reporter: " + model.getMediaRep() + ". Critici: " + model.getCritici() +".\n");
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.btnClassifica.setDisable(true);
    	this.btnSimula.setDisable(true);
    }
}
