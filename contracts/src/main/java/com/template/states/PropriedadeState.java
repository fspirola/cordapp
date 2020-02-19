package com.template.states;

import com.google.common.collect.ImmutableList;
import com.template.contracts.PropriedadeContract;
import com.template.schema.PropertyDetailSchemaV1;
import com.template.schema.PropertyDetailSchemaV1;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// *********
// * State *
// *********
@BelongsToContract(PropriedadeContract.class)
public class PropriedadeState implements LinearState, QueryableState {
    private final int propriedadeId;
    private final String propriedadeEndereco;
    private final Party proprietario;
    private final int compradorId;
    private final int vendedorId;
    private final String dataTime;
    private final String isHipoteca;
    private final String isAprovacaoEngenheiro;
    private final int propriedadePreco;

    private final UniqueIdentifier LinearId;


    public PropriedadeState(int propriedadeId, String propriedadeEndereco, Party proprietario, int compradorId, int vendedorId, String dataTime, String isHipoteca, String isAprovacaoEngenheiro, int propriedadePreco, UniqueIdentifier linearId) {

        this.propriedadeId = propriedadeId;
        this.propriedadeEndereco = propriedadeEndereco;
        this.proprietario = proprietario;
        this.compradorId = compradorId;
        this.vendedorId = vendedorId;
        this.dataTime = dataTime;
        this.isHipoteca = isHipoteca;
        this.isAprovacaoEngenheiro = isAprovacaoEngenheiro;
        this.propriedadePreco = propriedadePreco;
        LinearId = linearId;
    }


    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList();
    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return null;
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {

        if( schema instanceof PropertyDetailSchemaV1) {

            return new PropertyDetailSchemaV1.PersistentPropertyDetails(
                    this.propriedadeId,
                    this.propriedadeEndereco,
                    this.compradorId,
                    this.propriedadePreco,
                    this.LinearId.getId()
            );
        } else {
            throw new IllegalArgumentException("Schema desconhecido");
        }
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return null;
    }



    public int getPropriedadeId() {
        return propriedadeId;
    }

    public String getPropriedadeEndereco() {
        return propriedadeEndereco;
    }

    public Party getProprietario() {
        return proprietario;
    }

    public int getCompradorId() {
        return compradorId;
    }

    public int getVendedorId() {
        return vendedorId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public String getIsHipoteca() {
        return isHipoteca;
    }

    public String getIsAprovacaoEngenheiro() {
        return isAprovacaoEngenheiro;
    }

    public int getPropriedadePreco() {
        return propriedadePreco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropriedadeState that = (PropriedadeState) o;
        return propriedadeId == that.propriedadeId &&
                compradorId == that.compradorId &&
                vendedorId == that.vendedorId &&
                propriedadeEndereco.equals(that.propriedadeEndereco) &&
                proprietario.equals(that.proprietario) &&
                dataTime.equals(that.dataTime) &&
                isHipoteca.equals(that.isHipoteca) &&
                isAprovacaoEngenheiro.equals(that.isAprovacaoEngenheiro) &&
                LinearId.equals(that.LinearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propriedadeId, propriedadeEndereco, proprietario, compradorId, vendedorId, dataTime, isHipoteca, isAprovacaoEngenheiro, LinearId);
    }

    public PropriedadeState transferir(Party novoProprietario) {
        return new PropriedadeState(
                propriedadeId,
                propriedadeEndereco,
                proprietario,
                compradorId,
                vendedorId,
                dataTime,
                isHipoteca,
                isAprovacaoEngenheiro,
                propriedadePreco,
                LinearId
        );
    }

    public PropriedadeState aprovacaoBanco(String  isAprovado) {
        return new PropriedadeState(
                propriedadeId,
                propriedadeEndereco,
                proprietario,
                compradorId,
                vendedorId,
                dataTime,
                isAprovado,
                isAprovacaoEngenheiro,
                propriedadePreco,
                LinearId
        );
    }

    public PropriedadeState aprovacaoDoEngenheiro(String isAprovado) {
        return new PropriedadeState(
                propriedadeId,
                propriedadeEndereco,
                proprietario,
                compradorId,
                vendedorId,
                dataTime,
                isHipoteca,
                isAprovado,
                propriedadePreco,
                LinearId
        );
    }

    public List<AbstractParty> getParticipantes() {return ImmutableList.of(proprietario); }
}