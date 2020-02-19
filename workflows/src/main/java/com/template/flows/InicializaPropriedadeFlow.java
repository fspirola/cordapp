package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.PropertyCommand;
import com.template.contracts.PropriedadeContract;
import com.template.states.PropriedadeState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import java.time.Duration;
import java.time.Instant;

public class InicializaPropriedadeFlow extends FlowLogic<SignedTransaction> {

    private int propriedadeId;
    private String propriedadeEndereco;
    private Party proprietario;
    private int compradorId;
    private int vendedorId;
    private String dataTime;
    private String isHipoteca;
    private String isAprovacaoEngenheiro;
    private int propriedadePreco;

    public InicializaPropriedadeFlow(int propriedadeId, String propriedadeEndereco, Party proprietario, int compradorId, int vendedorId, String dataTime, String isHipoteca, String isAprovacaoEngenheiro, int propriedadePreco) {
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

    private static final ProgressTracker.Step GERANDO_TRANSACAO = new ProgressTracker.Step("Gerando a transacao");
    private static final ProgressTracker.Step ASSINANDO_TRANSACAO = new ProgressTracker.Step("Assinando a transacao");
    private static final ProgressTracker.Step FINALIZANDO_TRANSACAO = new ProgressTracker.Step("Finalizando a transacao");

    private final ProgressTracker progressTracker = new ProgressTracker(
            GERANDO_TRANSACAO,
            ASSINANDO_TRANSACAO,
            FINALIZANDO_TRANSACAO
    );
    public ProgressTracker getProgressTracker() {

        return progressTracker;
    }

    @Suspendable
    public SignedTransaction call() throws FlowException {
        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        progressTracker.setCurrentStep(GERANDO_TRANSACAO);
        PropriedadeState propriedadeState = new PropriedadeState(
                propriedadeId,
                propriedadeEndereco,
                proprietario,
                compradorId,
                vendedorId,
                dataTime,
                isHipoteca,
                isAprovacaoEngenheiro,
                propriedadePreco,
                new UniqueIdentifier()
        );

        progressTracker.setCurrentStep(ASSINANDO_TRANSACAO);
        TransactionBuilder builder = new TransactionBuilder(notary)
                .addOutputState(propriedadeState, PropriedadeContract.ID)
                .addCommand(new PropertyCommand.Criar(), getOurIdentity().getOwningKey())
                .setTimeWindow(Instant.now(), Duration.ofSeconds(10));

        progressTracker.setCurrentStep(FINALIZANDO_TRANSACAO);

        return subFlow(new VerifySignAndFinaliseFlow(builder));
    }
}
