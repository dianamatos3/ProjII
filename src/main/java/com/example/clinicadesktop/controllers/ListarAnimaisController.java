package com.example.clinicadesktop.controllers;

import com.example.clinicadesktop.models.Animal;
import com.example.clinicadesktop.services.AnimalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.List;

@Controller
public class ListarAnimaisController {

    @FXML
    private TableView<Animal> tabelaAnimais;

    @FXML
    private TableColumn<Animal, String> nomeColumn;

    @FXML
    private TableColumn<Animal, Integer> idadeColumn;

    @FXML
    private TableColumn<Animal, String> especieColumn;

    @FXML
    private TableColumn<Animal, String> racaColumn;

    @FXML
    private TableColumn<Animal, String> clienteColumn;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ApplicationContext springContext;

    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idadeColumn.setCellValueFactory(new PropertyValueFactory<>("idade"));
        especieColumn.setCellValueFactory(new PropertyValueFactory<>("especie"));
        racaColumn.setCellValueFactory(new PropertyValueFactory<>("raca"));

        // vamos buscar o nome do cliente dono
        clienteColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getIdCliente() != null) {
                return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdCliente().getNome());
            } else {
                return new javafx.beans.property.SimpleStringProperty("Sem Dono");
            }
        });


        carregarAnimais();

        tabelaAnimais.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void carregarAnimais() {
        List<Animal> animais = animalService.findAll();
        ObservableList<Animal> animaisObservable = FXCollections.observableArrayList(animais);
        tabelaAnimais.setItems(animaisObservable);
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
