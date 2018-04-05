package ru.avia.controller.Passenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.avia.DAO.impl.PassengerDAO;
import ru.avia.controller.Controller;
import ru.avia.model.Passenger;
import ru.avia.model.User;
import ru.avia.utils.DialogManager;
import ru.avia.utils.ValidateManager;

public class LoginController extends Controller {

    @FXML
    public TextField username;
    
    @FXML
    public PasswordField password;

    @FXML
    public Button loginButton;

    @FXML
    private void initialize() {
        ValidateManager.makeCharacter(username);
        ValidateManager.makeNumeric(password);
    }

    public void actionRegistration(ActionEvent actionEvent) {
        FXMLLoader loader = this.showModalityDialog(actionEvent, "/ru/avia/view/registration.fxml", "Регистрация", false);
        RegistrationController registrationController = loader.getController();
        registrationController.setLoginController(this);
    }

    public void actionLogin(ActionEvent actionEvent) {

        if (!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty()) {

            PassengerDAO passengerDAO = new PassengerDAO();
            Passenger passenger = passengerDAO.getByDocumentNumber(password.getText().trim());
            if (passenger != null) {
                if (passenger.getName().equals(username.getText().trim())) {
                    User.getInstance().setCurrent(passenger);
                    this.showDialog(loginButton, "/ru/avia/view/main.fxml", "Бронирование авиабилетов");
                } else {
                    DialogManager.showErrorDialog("Error!", "Некорректное имя");
                }
            } else {
                DialogManager.showErrorDialog("Error!", "Пользователь не найден");
            }
        } else {
            DialogManager.showErrorDialog("Error!", "Заполните все поля!");
        }
    }

}
