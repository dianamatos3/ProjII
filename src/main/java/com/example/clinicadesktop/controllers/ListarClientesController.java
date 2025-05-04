package com.example.clinicadesktop.controllers;

import com.example.clinicadesktop.models.Cliente;
import com.example.clinicadesktop.services.ClienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

@Controller
public class ListarClientesController {

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, String> nomeColumn;

    @FXML
    private TableColumn<Cliente, String> contactoColumn;

    @FXML
    private TableColumn<Cliente, String> emailColumn;

    @FXML
    private TableColumn<Cliente, String> moradaColumn;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private org.springframework.context.ApplicationContext springContext;


    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        moradaColumn.setCellValueFactory(new PropertyValueFactory<>("morada"));

        carregarClientes();

        tabelaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void carregarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        ObservableList<Cliente> clientesObservable = FXCollections.observableArrayList(clientes);
        tabelaClientes.setItems(clientesObservable);
    }

    @FXML
    private void voltarMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registarCliente.fxml"));
            loader.setControllerFactory(springContext::getBean); // para permitir @Autowired nos controllers
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) tabelaClientes.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Clínica Veterinária - Registar Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
