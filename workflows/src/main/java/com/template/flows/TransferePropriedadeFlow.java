package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.template.contracts.PropertyCommand;
import com.template.contracts.PropriedadeContract;
import com.template.states.PropriedadeState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.identity.Party;
import net.corda.core.node.services.vault.QueryCriteria;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

public class TransferePropriedadeFlow extends FlowLogic<SignedTransaction> {

    private final Party novoProprietario;
    private final UniqueIdentifier linearId;

    public TransferePropriedadeFlow(Party novoProprietario, UniqueIdentifier linearId) {
        this.novoProprietario = novoProprietario;
        this.linearId = linearId;
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

        //Buscar a propriedade
        progressTracker.setCurrentStep(GERANDO_TRANSACAO);
        QueryCriteria queryCriteria = new QueryCriteria.
                LinearStateQueryCriteria(null, ImmutableList.of(linearId.getId()));

        StateAndRef<PropriedadeState> inputStateAndRef = getServiceHub().getVaultService()
                .queryBy(PropriedadeState.class, queryCriteria).getStates().get(0);
        PropriedadeState propriedadeState = inputStateAndRef.getState().getData()
                .transferir(novoProprietario);

        progressTracker.setCurrentStep(ASSINANDO_TRANSACAO);
        TransactionBuilder builder = new TransactionBuilder(inputStateAndRef.getState().getNotary())
                .addOutputState(propriedadeState, PropriedadeContract.ID)
                .addCommand(new PropertyCommand.Transferir(), getOurIdentity().getOwningKey());

        progressTracker.setCurrentStep(FINALIZANDO_TRANSACAO);

        return subFlow(new VerifySignAndFinaliseFlow(builder));

    }
}
