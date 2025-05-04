package com.example.clinicadesktop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🔥 Adicionado para garantir auto incremento correto
    @Column(name = "id", nullable = false)
    private Long id; // 🔥 Alterado de Integer para Long

    @NotNull
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    /*@NotNull
    @Column(name = "tipo_pagamento", nullable = false)
    private Integer tipoPagamento; */

    @NotNull
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_tipopagamento", nullable = false)
    private Tipopagamento idTipopagamento;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_consulta", nullable = false)
    private com.example.clinicadesktop.models.Consulta consulta;

    //  Getter e Setter para Consulta
    public com.example.clinicadesktop.models.Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(com.example.clinicadesktop.models.Consulta consulta) {
        this.consulta = consulta;
    }

    //  Getter e Setter para ID atualizado para Long
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    /*public Integer getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(Integer tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }*/

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Tipopagamento getIdTipopagamento() {
        return idTipopagamento;
    }

    public void setIdTipopagamento(Tipopagamento idTipopagamento) {
        this.idTipopagamento = idTipopagamento;
    }
}
