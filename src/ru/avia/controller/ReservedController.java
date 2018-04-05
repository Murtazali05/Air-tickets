package ru.avia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.avia.DAO.impl.FlightDAO;
import ru.avia.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReservedController {

    private ObservableList<Flight> flightList = FXCollections.observableArrayList();

    @FXML
    public TableView<Flight> flightTableView;

    @FXML
    public TableColumn<Flight, String> numberFlightTableColumn;

    @FXML
    public TableColumn<Flight, Aircraft> aircraftTableColumn;

    @FXML
    public TableColumn<Flight, String> departureDateTableColumn;

    @FXML
    public TableColumn<Flight, String> dateOfArrivalTableColumn;

    @FXML
    public TableColumn<Flight, City> cityDepartureTableColumn;

    @FXML
    public TableColumn<Flight, City> cityDestinationTableColumn;

    private ObservableList<Reservation> reservedList = FXCollections.observableArrayList();

    @FXML
    public TableView<Reservation> reservedTableView;

    @FXML
    public TableColumn<Reservation, Passenger> fioTableColumn;

    @FXML
    public TableColumn<Reservation, Classes> classesTableColumn;

    @FXML
    public TableColumn<Reservation, Integer> numberPlaceTableColumn;

    @FXML
    private void initialize() {
        initData();

        numberFlightTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("number"));
        aircraftTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, Aircraft>("aircraftByAircraftId"));
        departureDateTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("dateDeparture"));
        dateOfArrivalTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("dateArrival"));
        cityDepartureTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, City>("cityByCityFromId"));
        cityDestinationTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, City>("cityByCityToId"));

        fioTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Passenger>("passengerByPassengerId"));
        classesTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Classes>("classesByClassesId"));
        numberPlaceTableColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("numberPlace"));


        flightTableView.setItems(flightList);
        reservedTableView.setItems(reservedList);
    }

    private void initData() {
        FlightDAO flightDAO = new FlightDAO();
        initFlightList(flightDAO.getAllFlight());
        setRowFactory();
    }

    private void initFlightList(List<Flight> flights) {
        this.flightList.clear();

        for (Flight flight : flights) {
            this.flightList.add(flight);
        }
    }

    private Flight selectedFlight;

    private void setRowFactory() {
        flightTableView.setRowFactory( tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedFlight = row.getItem();
                    int idReserved = selectedFlight.getId();
                    if (selectedFlight != null) {
                        FlightDAO flightDAO = new FlightDAO();
                        selectedFlight = flightDAO.get(idReserved);

                        Collection<Reservation> reservationCollection = selectedFlight.getReservationsById();
                        List<Reservation> reservationList;

                        if (reservationCollection instanceof List) reservationList = (List<Reservation>) reservationCollection;
                        else reservationList = new ArrayList(reservationCollection);

                        reservedList.setAll(reservationList);
                    }
                }

            });
            return row;
        });
    }

}
