package ru.avia.controller.Passenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.avia.DAO.impl.AbstractDAOImpl;
import ru.avia.DAO.impl.PassengerDAO;
import ru.avia.controller.PopupController;
import ru.avia.model.Passenger;
import ru.avia.model.Status;
import ru.avia.utils.DialogManager;
import ru.avia.utils.ValidateManager;

public class RegistrationController extends PopupController {

    @FXML
    public TextField surnameInput;

    @FXML
    public TextField nameInput;

    @FXML
    public TextField middleNameInput;

    @FXML
    public TextField documentNumberInput;

    @FXML
    public TextField emailInput;

    @FXML
    public TextField numberInput;

    LoginController loginController;

    @FXML
    private void initialize() {
        ValidateManager.makeCharacter(surnameInput);
        ValidateManager.makeCharacter(nameInput);
        ValidateManager.makeCharacter(middleNameInput);
        ValidateManager.makeNumeric(documentNumberInput);
        ValidateManager.makeNumeric(numberInput);
    }

    public void setLoginController(LoginController _loginController){
        loginController = _loginController;
        nameInput.setText(loginController.username.getText());
        documentNumberInput.setText(loginController.password.getText());
    }

    public void actionOk(ActionEvent actionEvent) {
        String error = this.isValid();

        if (error == null) {
            this.savePassenger();
            actionClose(actionEvent);
        } else {
            DialogManager.showErrorDialog("Ошибка", error);
        }
    }

    private void savePassenger(){
        Passenger passenger = new Passenger();

        passenger.setSurname(surnameInput.getText().trim());
        passenger.setName(nameInput.getText().trim());
        passenger.setMiddleName(middleNameInput.getText().trim());
        passenger.setDocumentNumber(Integer.parseInt(documentNumberInput.getText().trim()));
        passenger.setEmail(emailInput.getText().trim());
        passenger.setPhone(numberInput.getText().trim());

        AbstractDAOImpl<Status, Integer> statusDAO = new AbstractDAOImpl<Status, Integer>(Status.class);

        Status status = statusDAO.get(2);
        passenger.setStatusByStatusId(status);

        PassengerDAO passengerDAO = new PassengerDAO();
        passenger.setId(passengerDAO.save(passenger));

        loginController.username.setText(passenger.getName());
        loginController.password.setText(String.valueOf(passenger.getDocumentNumber()));
    }

    @Override
    protected String isValid(){
        String err = "";

        if (surnameInput.getText().trim().length() == 0 || surnameInput.getText() == null)
            err += "Поле {Фамилия} пусто.\n";

        if (nameInput.getText().trim().length() == 0 || nameInput.getText() == null)
            err += "Поле {Имя} пусто.\n";

        if (middleNameInput.getText().trim().length() == 0 || middleNameInput.getText() == null)
            err += "Поле {Отчество} пусто.\n";

        if (documentNumberInput.getText().trim().length() == 0 || documentNumberInput.getText() == null)
            err += "Поле {Паспортные данные} пусто.\n";
        else {
            try {
                Integer.parseInt(documentNumberInput.getText());
            } catch (NumberFormatException e){
                err += "В поле {Паспортные данные} должно быть числовое значение!\n";
            }
        }

        if (emailInput.getText().trim().length() == 0 || emailInput.getText() == null)
            err += "Поле {Email} пусто.\n";
        else {
            if (!emailInput.getText().trim().contains("@") || !emailInput.getText().trim().contains(".")) {
                err += "Email введен некорректно.\n";
            }
        }

        if (numberInput.getText().trim().length() == 0 || numberInput.getText() == null)
            err += "Поле {Телефон} пусто.\n";
        else {
            if (!this.checkPhone(numberInput.getText().trim()))
                err += "Номер телефона введен некорректно. \n";
        }

        if (err == "")
            return null;
        else
            return err;
    }

}
