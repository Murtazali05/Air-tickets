package ru.avia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.avia.DAO.impl.AircraftDAO;
import ru.avia.model.Aircraft;
import ru.avia.utils.DialogManager;

import java.util.List;

public class AircraftController {

    private ObservableList<Aircraft> aircraftList = FXCollections.observableArrayList();

    @FXML
    protected TableView<Aircraft> aircraftTableView;

    @FXML
    protected TableColumn<Aircraft, String> nameAircraftTableColumn;

    @FXML
    protected TextField nameAircraft;

    @FXML
    public Parent classesEditor;

    @FXML
    private ClassesController classesEditorController;

    @FXML
    private void initialize() {
        initData();

        nameAircraftTableColumn.setCellValueFactory(new PropertyValueFactory<Aircraft, String>("name"));

        aircraftTableView.setItems(aircraftList);
    }

    private void initData() {
        AircraftDAO aircraftDAO = new AircraftDAO();
        initAircraftList(aircraftDAO.getAllAircraft());

        this.setAircraftRowFactory();

    }

    private void initAircraftList(List<Aircraft> aircrafts) {
        this.aircraftList.clear();
        for (Aircraft aircraft : aircrafts) {
            this.aircraftList.add(aircraft);
        }
    }

    private Aircraft selectedAircraft;

    private void setAircraftRowFactory() {
        aircraftTableView.setRowFactory( tv -> {
            TableRow<Aircraft> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedAircraft = row.getItem();
                    if (selectedAircraft != null) {
                        nameAircraft.setText(selectedAircraft.getName());
                        classesEditorController.setSelectedAircraft(selectedAircraft);
                    }
                } else {
                    nameAircraft.setText("");
                }

                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedAircraft = row.getItem();
                    if (selectedAircraft != null) {
                        AircraftDAO aircraftDAO = new AircraftDAO();
                        selectedAircraft = aircraftDAO.get(selectedAircraft.getId());
                        classesEditorController.getClassesList().setAll(selectedAircraft.getClassesById());
                        classesEditorController.setSelectedAircraft(selectedAircraft);
                        classesEditorController.clear();
                    }
                }
            });
            return row;
        });
    }


    public void actionAddAircraft(ActionEvent actionEvent) {

        if (nameAircraft.getText().trim().length() == 0 || nameAircraft.getText() == null){
            DialogManager.showErrorDialog("Ошибка", "Введите название");
        } else{
            this.saveAircraft();
            AircraftDAO aircraftDAO = new AircraftDAO();
            initAircraftList(aircraftDAO.getAllAircraft());
            nameAircraft.setText("");
        }

    }

    private void saveAircraft() {

        Aircraft newAircraft = new Aircraft();
        newAircraft.setName(nameAircraft.getText().trim());

        AircraftDAO aircraftDAO = new AircraftDAO();
        newAircraft.setId(aircraftDAO.save(newAircraft));

        aircraftList.add(newAircraft);
    }

    public void actionUpdateAircraft(ActionEvent actionEvent) {
        if (selectedAircraft == null) {
            DialogManager.showInfoDialog("Подсказка", "Выберите объект для редактирования");
        } else {
            selectedAircraft.setName(nameAircraft.getText().trim());

            AircraftDAO aircraftDAO = new AircraftDAO();
            aircraftDAO.update(selectedAircraft);
            aircraftTableView.refresh();
        }

    }

    public void actionDeleteAircraft(ActionEvent actionEvent) {
        if (selectedAircraft == null) {
            DialogManager.showInfoDialog("Подсказка", "Выберите объект для удаления");
        } else {
            if (DialogManager.showConfirmationDialog("Удаление", "Вы действительно хотите удалить самолет?") == ButtonType.OK) {
                AircraftDAO aircraftDAO = new AircraftDAO();
                aircraftDAO.delete(selectedAircraft);
                aircraftList.remove(selectedAircraft);
                nameAircraft.setText("");
            }
        }
    }

}
