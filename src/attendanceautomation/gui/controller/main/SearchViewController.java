/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Your Name <Skovgaard>
 */
public class SearchViewController implements Initializable {

    @FXML
    private JFXTextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
//    private void handleSearch() {
//        if (txtSearch.getText().equals("")) {
//            handleClearSearch();
//        } else {
//            String search = txtSearch.getText();
//            if (!search.equals("")) {
//                songModel.searchSong(search);
//            }
//        }
//    }
//    
//    @FXML
//    private void handleEnterSearch(KeyEvent event) {
//        if (event.getCode().equals(KeyCode.ENTER)) {
//            handleSearch();
//        }
//    }
//    /**
//     * Clears the query and shows all students.
//     */
//    @FXML
//    private void handleClearSearch() {
////        songModel.clearSearch();
//        txtSearch.setText("");
//        updateSongTotals();
//    }

    @FXML
    private void handleEnterSearch(KeyEvent event) {
    }
    
}
