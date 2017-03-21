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
        setCellFactory();
    }

    /**
     * Sets the students in the observableList.
     *
     * @param list
     */
    public void setItemsInList(ObservableList<Student> list) {
        listViewOfAttendance.setItems(list);
    }

    public void setCellFactory() {
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

                    return cell;
                } catch (IOException ex) {
                    Logger.getLogger(CurrentClassListViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return cell;
            }
        });
    }

    public void setCellFactoryRed() {
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
                    cell.setStyle("-fx-control-inner-background: red");

                    return cell;
                } catch (IOException ex) {
                    Logger.getLogger(CurrentClassListViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return cell;
            }
        });
    }

    public void setCellFactoryGreen() {
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
                    cell.setStyle("-fx-control-inner-background: green");

                    return cell;
                } catch (IOException ex) {
                    Logger.getLogger(CurrentClassListViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return cell;
            }
        });
    }

}
