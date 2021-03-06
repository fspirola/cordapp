package com.template.contracts;

import com.template.schema.PropertyDetails;
import com.template.states.PropriedadeState;
import jdk.nashorn.internal.runtime.Property;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;
import static net.corda.core.contracts.ContractsDSL.requireThat;

// ************
// * Contract *
// ************
public class PropriedadeContract implements Contract {
    // This is used to identify our contract when building a transaction.
    public static final String ID = "com.template.contracts.PropriedadeContract";

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException {
        CommandWithParties<PropertyCommand> command =
                requireSingleCommand(tx.getCommands(), PropertyCommand.class);
        PropertyCommand commandType = command.getValue();

        if( commandType instanceof PropertyCommand.Criar ) verificaCriar(tx, command);
        else if (commandType instanceof PropertyCommand.Transferir) verificaTransferir(tx, command);
        else if (commandType instanceof  PropertyCommand.AprovacaoBanco) verificaAprovacaoBanco(tx, command);

    }

    private void verificaAprovacaoBanco(LedgerTransaction tx, CommandWithParties<PropertyCommand> command) {
        requireThat(require -> {
            return null;
        });
    }

    private void verificaTransferir(LedgerTransaction tx, CommandWithParties<PropertyCommand> command) {
        requireThat(require -> {

            require.using( "A propriedade deve possuir apenas um input",
                    tx.getInputs().size()==1);
            require.using( "A propriedade deve possuir apenas um output",
                    tx.getOutputs().size()==1);

            final PropriedadeState in =  tx.inputsOfType(PropriedadeState.class).get(0);
            final PropriedadeState out =  tx.outputsOfType(PropriedadeState.class).get(0);

            return null;
        });
    }

    private void verificaCriar(LedgerTransaction tx, CommandWithParties<PropertyCommand> command) {
        requireThat(require -> {
            require.using( "A propriedade nao deve possuir nenhum input",
                    tx.getInputs().isEmpty());
            require.using( "A propriedade deve possuir apenas um output",
                    tx.getInputs().size() == 1);
         final PropriedadeState out = tx.outputsOfType(PropriedadeState.class).get(0);
         return null;
        });
    }

    // Used to indicate the transaction's intent.
    public interface Commands extends CommandData {
        class Action implements Commands {}
    }
}