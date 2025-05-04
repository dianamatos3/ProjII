package com.example.clinicadesktop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipopagamento")
public class Tipopagamento {
    @Id
    @ColumnDefault("nextval('tipopagamento_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @OneToMany(mappedBy = "tipoPagamento")
    private Set<com.example.clinicadesktop.models.Fatura> faturas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTipopagamento")
    private Set<com.example.clinicadesktop.models.Pagamento> pagamentos = new LinkedHashSet<>();

    public Set<com.example.clinicadesktop.models.Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Set<com.example.clinicadesktop.models.Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Set<com.example.clinicadesktop.models.Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(Set<com.example.clinicadesktop.models.Fatura> faturas) {
        this.faturas = faturas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

}