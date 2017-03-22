/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.currentClassView.controller;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.controls.CurrentClassListViewCell;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class CurrentClassListViewController implements Initializable {

    @FXML
    private ListView<Student> listViewOfAttendance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellFactory("white");
    }

    /**
     * Sets the students in the observableList.
     *
     * @param list
     */
    public void setItemsInList(ObservableList<Student> list) {
        listViewOfAttendance.setItems(list);
    }

    /**
     * Sets the cellFactory for the list.
     *
     * @param color
     */
    public void setCellFactory(String color) {
        listViewOfAttendance.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                CurrentClassListViewCell cell = new CurrentClassListViewCell();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.NAME_CURRENT_CLASS_LIST_VIEW.toString()));
                    Node node = loader.load();
                    NameCurrentClassListViewController controller = loader.getController();
                    cell.setController(controller);
                    cell.setView(node);
                    cell.setGraphic(node);
                    cell.setStyle(getColorOfCell(color));
                    return cell;
                } catch (IOException ex) {
                    Logger.getLogger(CurrentClassListViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return cell;
            }
        });
    }

    /**
     * Returns a CSS string with the given color for the cell.
     *
     * @param color
     * @return
     */
    private String getColorOfCell(String color) {
        switch (color) {
            case "white": {
                return "-fx-control-inner-background: white";
            }
            case "red": {
                return "-fx-control-inner-background: red";
            }
            case "green": {
                return "-fx-control-inner-background: green";
            }
            default: {
                return "-fx-control-inner-background: white";
            }
        }
    }

}
