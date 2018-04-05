package ru.avia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.avia.DAO.impl.AbstractDAOImpl;
import ru.avia.DAO.impl.FlightDAO;
import ru.avia.DAO.impl.ReservationDAO;
import ru.avia.model.*;
import ru.avia.utils.DialogManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MainController {

    @FXML
    public Label passengerLabel;

    @FXML
    public Button logoutButton;

    @FXML
    public Tab reserved;

    @FXML
    public Tab aircraft;

    @FXML
    public TabPane tabPane;

    private ObservableList<Flight> flightList = FXCollections.observableArrayList();

    @FXML
    public TableView<Flight> flightTableView;

    @FXML
    public TableColumn<Flight, String> numberFlightTableColumn;

    @FXML
    public TableColumn<Flight, City> cityDepartureTableColumn;

    @FXML
    public TableColumn<Flight, City> cityDestinationTableColumn;

    @FXML
    public TableColumn<Flight, String> departureDateTableColumn;

    @FXML
    public TableColumn<Flight, String> dateOfArrivalTableColumn;

    @FXML
    public TableColumn<Flight, Integer> countPlace;

    @FXML
    public TableColumn<Flight, Aircraft> aircraftTableColumn;

    private ObservableList<City> cityList = FXCollections.observableArrayList();

    @FXML
    public ComboBox<City> cityFromComboBox;

    @FXML
    public ComboBox<City> cityToComboBox;

    @FXML
    public DatePicker datePicker;

    private ObservableList<Classes> typeTicketList = FXCollections.observableArrayList();

    @FXML
    public ComboBox<Classes> typeTicketComboxBox;

    @FXML
    public Spinner<Integer> placeNumberSpinner;

    @FXML
    private void initialize() {
        initData();

        Passenger passenger = User.getInstance().getCurrent();
        passengerLabel.setText(passenger.getSurname() + " " + passenger.getName());

        if (User.getInstance().getCurrent().getStatusByStatusId().getName().equals("user")) {
            aircraft.setDisable(true);
            reserved.setDisable(true);
        }

        numberFlightTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("number"));
        cityDepartureTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, City>("cityByCityFromId"));
        cityDestinationTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, City>("cityByCityToId"));
        departureDateTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("dateDeparture"));
        dateOfArrivalTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("dateArrival"));
        countPlace.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("countPlace"));
        aircraftTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, Aircraft>("aircraftByAircraftId"));

        flightTableView.setItems(flightList);


        cityFromComboBox.setItems(cityList);
        cityToComboBox.setItems(cityList);
        typeTicketComboxBox.setItems(typeTicketList);

        initDatePicker();

        typeTicketComboxBox.getSelectionModel().selectedItemProperty().addListener(
                (_ob, _old, _new) -> {

                    int placeCount = typeTicketComboxBox.getValue().getPlaceCount();
                    SpinnerValueFactory<Integer> valueFactory = //
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, placeCount, 1);


                    placeNumberSpinner.setValueFactory(valueFactory);
                });

    }

    private void initDatePicker() {
        Locale.setDefault(Locale.US);
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.now())
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void initData() {
        AbstractDAOImpl<City, Integer> cityDAO = new AbstractDAOImpl<City, Integer>(City.class);
        initComboBox(cityDAO.list());

        FlightDAO flightDAO = new FlightDAO();
        initFlightList(flightDAO.getFutureFlights());

        setRowFactory();
    }

    private Flight selectedFlight;

    private void setRowFactory() {
        flightTableView.setRowFactory( tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedFlight = row.getItem();
                    if (selectedFlight != null) {
                        initTypeTicketComboBox(selectedFlight.getAircraftByAircraftId().getClassesById());

                    }
                }
            });
            return row;
        });
    }

    private void initFlightList(List<Flight> flights) {
        this.flightList.clear();

        for (Flight flight : flights) {
            this.flightList.add(flight);
        }
    }

    private void initComboBox(List<City> cities){
        cityList.clear();

        for (City city: cities){
            cityList.add(city);
        }
    }

    private void initTypeTicketComboBox(Collection<Classes> classes){
        typeTicketList.clear();

        for (Classes clas: classes){
            typeTicketList.add(clas);
        }
    }

    public void searchTicket(ActionEvent actionEvent) {
        selectedFlight = null;
        typeTicketList.clear();
        FlightDAO flightDAO = new FlightDAO();

        if (cityFromComboBox.getValue() == null || cityToComboBox.getValue() == null || datePicker.getValue() == null){
            DialogManager.showErrorDialog("Ошибка", "Заполните все поля!");
        } else {
            if (cityFromComboBox.getValue() == cityToComboBox.getValue()) {
                DialogManager.showErrorDialog("Ошибка", "Города должны быть разные");
            } else {
                City cityFrom = cityFromComboBox.getSelectionModel().getSelectedItem();
                City cityTo = cityToComboBox.getSelectionModel().getSelectedItem();
                initFlightList(flightDAO.getFilterFlight(cityFrom, cityTo, datePicker.getValue()));
            }
        }
    }

    public void actionLogout(ActionEvent actionEvent) {
        User.getInstance().setCurrent(null);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ru/avia/view/login.fxml"));
            Stage newstage = new Stage();
            newstage.setTitle("Авторизация");
            newstage.setResizable(false);
            newstage.setScene(new Scene(root, 300, 250));
            newstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reservation(ActionEvent actionEvent) {
        ReservationDAO reservationDAO = new ReservationDAO();

        if (selectedFlight == null){
            DialogManager.showErrorDialog("Ошибка", "Выберите рейс, который хотите забронировать");
        } else if (typeTicketComboxBox.getValue() == null) {
            DialogManager.showErrorDialog("Ошибка", "Выберите класс обслуживания");
        } else if (placeNumberSpinner == null) {
            DialogManager.showErrorDialog("Ошибка", "Выберите номер места");
        } else {
            if (reservationDAO.getBusy(typeTicketComboxBox.getValue(), placeNumberSpinner.getValue().intValue())) {  // занято ли место
                DialogManager.showErrorDialog("Ошибка", "Место занято");
            } else {
                Reservation reservation = new Reservation();
                reservation.setNumberPlace(placeNumberSpinner.getValue().intValue());
                reservation.setFlightByFlightId(selectedFlight);
                reservation.setPassengerByPassengerId(User.getInstance().getCurrent());
                reservation.setClassesByClassesId(typeTicketComboxBox.getValue());
                reservationDAO.save(reservation);
                DialogManager.showInfoDialog("Успешно", "Билет забронирован");
            }
        }
    }
}
