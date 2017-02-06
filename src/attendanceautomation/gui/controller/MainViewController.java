/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

import attendanceautomation.gui.model.MockModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class MainViewController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    private Node PIE_CHART_NODE;
    private Node LIST_VIEW;
    
    private MockModel mockModel = new MockModel(); //TODO RKL: Replace with real data.
    
    public MainViewController(){
        try {
            PIE_CHART_NODE = createPieChartNode();
            LIST_VIEW = createListView();
        } catch (IOException ex) {
            System.out.println("PieChart not loaded!");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setRight(PIE_CHART_NODE);
        borderPane.setCenter(LIST_VIEW);
    }    
    
    /**
     * Creates the node for the PieChartView.
     * @return
     * @throws IOException 
     */
    private Node createPieChartNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/PieChartView.fxml"));
        Node node = loader.load();
        return node;
    }
    
    /**
     * Creates the node for the listView.
     * @return
     * @throws IOException 
     */
    private Node createListView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/ListOfNameStatisticsView.fxml"));
        Node node = loader.load();
        ListOfNameStatisticsViewController controller = loader.getController();
        controller.setItemsInList(mockModel);
        return node;
    }
    
}
