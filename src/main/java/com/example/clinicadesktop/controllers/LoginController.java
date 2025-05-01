package com.example.clinicadesktop.controllers;

import com.example.clinicadesktop.models.Utilizador;
import com.example.clinicadesktop.reps.UtilizadorRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    // 🔒 Armazena o utilizador autenticado para uso global
    public static Utilizador utilizadorAutenticado;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private ImageView logoImageView;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll("Administrador", "Veterinário", "Recepcionista");

        try {
            logoImageView.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));
        } catch (Exception e) {
            System.out.println("Logo não encontrado.");
        }
    }

    public void setTipoUtilizadorPredefinido(String tipo) {
        userTypeComboBox.getItems().setAll("Administrador", "Veterinário", "Rececionista");
        userTypeComboBox.setValue(tipo);
        userTypeComboBox.setDisable(true);
    }

    @FXML
    private void login() {
        String email = usernameField.getText().trim();
        String senha = passwordField.getText().trim();
        String tipo = userTypeComboBox.getValue();

        if (email.isEmpty() || senha.isEmpty() || tipo == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Por favor, preencha todos os campos.");
            return;
        }

        Utilizador utilizador = utilizadorRepository.findByEmailAndSenhaAndTipoutilizador_Nome(email, senha, tipo);

        if (utilizador != null) {
            try {
                // 🔐 Guardar o utilizador autenticado
                utilizadorAutenticado = utilizador;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menuPrincipal.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();

                // Passar o tipo de utilizador para o menu principal
                MenuPrincipalController menuController = loader.getController();
                menuController.setTipoUtilizador(tipo);

                Stage stage = (Stage) usernameField.getScene().getWindow();
                Scene scene = new Scene(root, 1100, 700);

                stage.setScene(scene);
                stage.setTitle("Menu Principal");
                stage.setResizable(true);
                stage.centerOnScreen();
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao carregar o menu: " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Credenciais incorretas ou tipo de utilizador inválido.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Autenticação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
