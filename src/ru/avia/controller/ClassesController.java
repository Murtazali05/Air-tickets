package ru.avia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.avia.DAO.impl.ClassesDAO;
import ru.avia.model.Aircraft;
import ru.avia.model.Classes;
import ru.avia.utils.DialogManager;

public class ClassesController extends AircraftController {

    private ObservableList<Classes> classesList = FXCollections.observableArrayList();

    @FXML
    private TableView<Classes> classesTableView;

    @FXML
    private TableColumn<Classes, String> nameClassesTableColumn;

    @FXML
    private TableColumn<Classes, Integer> countPlaceTableColumn;

    @FXML
    private TextField nameClasses;

    @FXML
    private Spinner<Integer> countPlaceSpinner;

    @FXML
    private void initialize() {
        initData();

        nameClassesTableColumn.setCellValueFactory(new PropertyValueFactory<Classes, String>("name"));
        countPlaceTableColumn.setCellValueFactory(new PropertyValueFactory<Classes, Integer>("placeCount"));

        classesTableView.setItems(classesList);

    }

    private void initData() {
        this.setClassesRowFactory();
    }

    private Classes selectedClasses;
    private Aircraft selectedAircraft;

    private void setClassesRowFactory() {

        classesTableView.setRowFactory( tv -> {
            TableRow<Classes> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedClasses = row.getItem();
                    if (selectedClasses != null) {
                        nameClasses.setText(selectedClasses.getName());
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, selectedClasses.getPlaceCount());
                        countPlaceSpinner.setValueFactory(valueFactory);
                    }
                } else {
                    nameClasses.setText("");
                }

                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedClasses = row.getItem();
                }
            });
            return row;
        });
    }

    public void actionAddClasses(ActionEvent actionEvent) {

        if (nameClasses.getText().trim().length() == 0 || nameClasses.getText() == null) {
            DialogManager.showErrorDialog("Ошибка", "Введите название");
        } else {

            if (selectedAircraft == null){
                DialogManager.showErrorDialog("Ошибка", "Выберите самолет, для которого хотите добавить класс обслуживания");
            } else {
                Classes newClasses = new Classes();
                newClasses.setName(nameClasses.getText().trim());
                newClasses.setPlaceCount(countPlaceSpinner.getValue().intValue());
                newClasses.setAircraftByAircraftId(selectedAircraft);

                ClassesDAO classesDAO = new ClassesDAO();
                newClasses.setId(classesDAO.save(newClasses));

                this.classesList.add(newClasses);
                nameClasses.setText("");
                classesTableView.refresh();
            }
        }
    }

    public void actionUpdateClasses(ActionEvent actionEvent) {

        if (selectedAircraft == null){
            DialogManager.showErrorDialog("Ошибка", "Выберите самолет, для которого вы хотите отредактировать классы обслуживания");
        } else {
            if (nameClasses.getText().trim().length() == 0 || nameClasses.getText() == null) {
                DialogManager.showErrorDialog("Ошибка", "Введите название");
            } else {
                Classes itemClasses = classesTableView.getSelectionModel().getSelectedItem();
                if (itemClasses == null) {
                    DialogManager.showInfoDialog("Подсказка", "Выберите объект для редактирования");
                } else {
                    itemClasses.setName(nameClasses.getText().trim());
                    itemClasses.setPlaceCount(countPlaceSpinner.getValue().intValue());
                    itemClasses.setAircraftByAircraftId(selectedAircraft);
                    ClassesDAO classesDAO = new ClassesDAO();
                    classesDAO.update(itemClasses);
                    classesTableView.refresh();

                }
            }
        }

    }

    public void actionDeleteClasses(ActionEvent actionEvent) {
        Classes itemClasses = classesTableView.getSelectionModel().getSelectedItem();

        if (itemClasses == null) {
            DialogManager.showInfoDialog("Подсказка", "Выберите объект для удаления");
        } else {
            if (DialogManager.showConfirmationDialog("Удаление", "Вы действительно хотите удалить класс обслуживания для данного самолета?") == ButtonType.OK) {
                ClassesDAO classesDAO = new ClassesDAO();
                classesDAO.delete(itemClasses);
                this.classesList.remove(itemClasses);
                nameClasses.setText("");
                classesTableView.refresh();
            }
        }
    }

    public ObservableList<Classes> getClassesList(){
        return classesList;
    }

    public void setSelectedAircraft(Aircraft aircraft){
        selectedAircraft = aircraft;
    }

    public void clear(){
        nameClasses.setText("");
    }

}
