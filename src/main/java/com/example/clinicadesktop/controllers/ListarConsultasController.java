package com.example.clinicadesktop.controllers;

import com.example.clinicadesktop.models.Consulta;
import com.example.clinicadesktop.services.ConsultaService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ListarConsultasController {

    @FXML private TableView<Consulta> tableViewConsultas;
    @FXML private TableColumn<Consulta, String> colData;
    @FXML private TableColumn<Consulta, String> colMotivo;
    @FXML private TableColumn<Consulta, String> colVeterinario;
    @FXML private TableColumn<Consulta, String> colAnimal;

    @Autowired private ConsultaService consultaService;
    @Autowired private ApplicationContext springContext;

    @FXML
    public void initialize() {
        colData.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData().toString()));
        colMotivo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMotivo()));
        colVeterinario.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getVeterinarioResponsavel()));
        colAnimal.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAnimal().getNome()));

        tableViewConsultas.setItems(FXCollections.observableArrayList(consultaService.findAll()));
    }

    @FXML
    private void voltarMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menuPrincipal.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Clínica Veterinária - Menu Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
