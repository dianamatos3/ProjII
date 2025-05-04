package com.example.clinicadesktop.controllers;

import com.example.clinicadesktop.models.Consulta;
import com.example.clinicadesktop.models.Pagamento;
import com.example.clinicadesktop.models.Tipopagamento;
import com.example.clinicadesktop.services.ConsultaService;
import com.example.clinicadesktop.services.PagamentoService;
import com.example.clinicadesktop.services.TipoPagamentoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



@Controller
public class RegistarPagamentoController {

    @FXML
    private ComboBox<Consulta> consultaComboBox;

    @FXML
    private TextField valorField;

    @FXML
    private TextField valorTotalField;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private ComboBox<Tipopagamento> tipoPagamentoComboBox;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private TipoPagamentoService tipoPagamentoService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ApplicationContext springContext;


    @FXML
    private void initialize() {
        List<Consulta> consultas = consultaService.findAll();
        consultaComboBox.getItems().addAll(consultas);

        List<Tipopagamento> tipos = tipoPagamentoService.findAll();
        tipoPagamentoComboBox.getItems().addAll(tipos);

        dataPicker.setValue(LocalDate.now());
    }


    @FXML
    private void guardarPagamento() {
        try {
            if (camposInvalidos()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Preencha todos os campos corretamente.");
                return;
            }

            Pagamento pagamento = new Pagamento();
            pagamento.setConsulta(consultaComboBox.getValue());
            pagamento.setValor(new BigDecimal(valorField.getText()));
            pagamento.setValorTotal(new BigDecimal(valorTotalField.getText()));
            pagamento.setData(dataPicker.getValue());
            pagamento.setIdTipopagamento(tipoPagamentoComboBox.getValue());

            pagamentoService.save(pagamento);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Pagamento registado com sucesso!");
            limparCampos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao registar pagamento.");
        }
    }

    private boolean camposInvalidos() {
        return consultaComboBox.getValue() == null ||
                tipoPagamentoComboBox.getValue() == null ||
                valorField.getText().isEmpty() ||
                valorTotalField.getText().isEmpty() ||
                dataPicker.getValue() == null;
    }

    private void limparCampos() {
        consultaComboBox.getSelectionModel().clearSelection();
        tipoPagamentoComboBox.getSelectionModel().clearSelection();
        valorField.clear();
        valorTotalField.clear();
        dataPicker.setValue(null);
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

    private void mostrarAlerta(Alert.AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Pagamento");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
