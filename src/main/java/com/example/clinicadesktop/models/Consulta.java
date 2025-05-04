package com.example.clinicadesktop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consulta_id_gen")
    @SequenceGenerator(name = "consulta_id_gen", sequenceName = "consulta_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "motivo", nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "veterinario_responsavel", length = 100)
    private String veterinarioResponsavel;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_animal", nullable = false)
    private Animal animal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = true)
    private Utilizador utilizador;

    @ManyToMany
    @JoinTable(
            name = "consultaproduto",
            joinColumns = @JoinColumn(name = "id_consulta"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private Set<Produto> produtos;

    @OneToMany(mappedBy = "consulta")
    private Set<Pagamento> pagamentos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "consulta")
    private Set<Tratamento> tratamentos = new LinkedHashSet<>();

    public Set<Tratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(Set<Tratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }

    public Set<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    // Construtor padrão
    public Consulta() {}

    // Construtor com parâmetros
    public Consulta(LocalDate data, String motivo, String diagnostico, String veterinarioResponsavel, Animal animal, Utilizador utilizador) {
        this.data = data;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.veterinarioResponsavel = veterinarioResponsavel;
        this.animal = animal;
        this.utilizador = utilizador;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    public void setVeterinarioResponsavel(String veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }


    @Override
    public String toString() {
        return "Consulta de " + animal.getNome() + " em " + data;
    }

}
