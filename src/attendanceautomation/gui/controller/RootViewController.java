/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

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
 *
 * @author gta1
 */
public class RootViewController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    private Node MAIN_VIEW;
    
    public RootViewController(){
        try {
            MAIN_VIEW = createMainView();
        } catch (IOException ex) {
            
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(MAIN_VIEW);
    }
    
    /**
     * Creates the node containing MainView.
     * @return
     * @throws IOException 
     */
    private Node createMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/MainView.fxml"));
        Node node = loader.load();
        return node;
    }   
}
