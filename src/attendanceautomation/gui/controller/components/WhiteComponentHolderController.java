/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class WhiteComponentHolderController implements Initializable {

    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Sets the node of the center of the borderPane.
     * @param node 
     */
    public void setBorderPaneRight(Node node){
        borderPane.setRight(node);
    }
    
    /**
     * Sets the node of the left of the borderPane.
     * @param node 
     */
    public void setBorderPaneLeft(Node node){
        borderPane.setLeft(node);
    }
    
    /**
     * Sets the node in the center of the borderPane.
     * @param node 
     */
    public void setBorderPaneCenter(Node node){
        borderPane.setCenter(node);
    }
    
    /**
     * Sets the node at the top of the borderPane.
     * @param node 
     */
    public void setBorderPaneTop(Node node){
        borderPane.setTop(node);
    }
    
    /**
     * Sets the node at the bottom of the borderPane.
     * @param node 
     */
    public void setBorderPaneBottom(Node node){
        borderPane.setBottom(node);
    }
}
