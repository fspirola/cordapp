package com.template.flows;

public class PropriedadeBean {
    private int propriedadeId;
    private String propriedadeEndereco;
    private String proprietario;
    private int compradorId;
    private int vendedorId;
    private String dataTime;
    private String isHipoteca;
    private String isAprovacaoEngenheiro;
    private int propriedadePreco;

    public PropriedadeBean(int propriedadeId, String propriedadeEndereco, String proprietario, int compradorId, int vendedorId, String dataTime, String isHipoteca, String isAprovacaoEngenheiro, int propriedadePreco) {
        this.propriedadeId = propriedadeId;
        this.propriedadeEndereco = propriedadeEndereco;
        this.proprietario = proprietario;
        this.compradorId = compradorId;
        this.vendedorId = vendedorId;
        this.dataTime = dataTime;
        this.isHipoteca = isHipoteca;
        this.isAprovacaoEngenheiro = isAprovacaoEngenheiro;
        this.propriedadePreco = propriedadePreco;
    }

    public int getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(int propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public String getPropriedadeEndereco() {
        return propriedadeEndereco;
    }

    public void setPropriedadeEndereco(String propriedadeEndereco) {
        this.propriedadeEndereco = propriedadeEndereco;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public int getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(int compradorId) {
        this.compradorId = compradorId;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(int vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getIsHipoteca() {
        return isHipoteca;
    }

    public void setIsHipoteca(String isHipoteca) {
        this.isHipoteca = isHipoteca;
    }

    public String getIsAprovacaoEngenheiro() {
        return isAprovacaoEngenheiro;
    }

    public void setIsAprovacaoEngenheiro(String isAprovacaoEngenheiro) {
        this.isAprovacaoEngenheiro = isAprovacaoEngenheiro;
    }

    public int getPropriedadePreco() {
        return propriedadePreco;
    }

    public void setPropriedadePreco(int propriedadePreco) {
        this.propriedadePreco = propriedadePreco;
    }
}
