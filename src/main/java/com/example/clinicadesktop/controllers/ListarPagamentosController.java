package com.example.clinicadesktop.controllers;

import com.example.clinicadesktop.models.Pagamento;
import com.example.clinicadesktop.services.PagamentoService;
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
import java.time.format.DateTimeFormatter;

@Controller
public class ListarPagamentosController {

    @FXML private TableView<Pagamento> tableViewPagamentos;
    @FXML private TableColumn<Pagamento, String> colData;
    @FXML private TableColumn<Pagamento, String> colValor;
    @FXML private TableColumn<Pagamento, String> colValorTotal;
    @FXML private TableColumn<Pagamento, String> colTipoPagamento;
    @FXML private TableColumn<Pagamento, String> colConsulta;

    @Autowired private PagamentoService pagamentoService;
    @Autowired private ApplicationContext springContext;

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colData.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData().format(formatter)));

        colValor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValor().toString()));

        colValorTotal.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValorTotal().toString()));

        colTipoPagamento.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getIdTipopagamento().getDescricao()));

        colConsulta.setCellValueFactory(cellData -> {
            var consulta = cellData.getValue().getConsulta();
            String descricao = "Consulta de " + consulta.getAnimal().getNome() + " em " + consulta.getData();
            return new javafx.beans.property.SimpleStringProperty(descricao);
        });

        tableViewPagamentos.setItems(FXCollections.observableArrayList(pagamentoService.findAll()));
        tableViewPagamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
