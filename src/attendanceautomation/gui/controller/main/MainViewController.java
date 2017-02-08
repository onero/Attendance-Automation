/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

import attendanceautomation.gui.controller.components.PieChartViewController;
import attendanceautomation.AttendanceAutomationMain;
import attendanceautomation.be.EFXMLNames;
import attendanceautomation.gui.model.AttendanceModel;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private Node SEARCH_BAR;

    private static MainViewController instance;

    public static MainViewController getInstance() {
        return instance;
    }

    private SchoolClassModel schoolClassModel;

    public MainViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        try {
            PIE_CHART_NODE = createPieChartNode();
            LIST_VIEW = createListView();
            SEARCH_BAR = createSearchBarNode();
        } catch (IOException ex) {
            System.out.println("PieChart not loaded!");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        borderPane.setCenter(PIE_CHART_NODE);
        borderPane.setTop(SEARCH_BAR);
        borderPane.setLeft(LIST_VIEW);

    }

    /**
     * Creates the node for the PieChartView.
     *
     * @return
     * @throws IOException
     */
    private Node createPieChartNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.PIE_CHART_VIEW.toString()));
        Node node = loader.load();
        PieChartViewController controller = loader.getController();
        controller.setData(AttendanceModel.getInstance().getPieChartData());

        return node;
    }

    private Node createSearchBarNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.SEARCH_VIEW.toString()));
        Node node = loader.load();

        return node;
    }

    /**
     * Creates the node for the listView.
     *
     * @return
     * @throws IOException
     */
    private Node createListView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LIST_OF_NAMES_STATISTICS_VIEW.toString()));
        Node node = loader.load();
        ListOfNameStatisticsViewController controller = loader.getController();
        controller.setItemsInList(schoolClassModel);
        return node;
    }

    /**
     * Returns the instance of SchoolClassModel.
     *
     * @return
     */
    public SchoolClassModel getSchoolClassModel() {
        return schoolClassModel;
    }

}
