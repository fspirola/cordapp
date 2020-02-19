package com.template.webserver;

import com.template.flows.PropriedadeBean;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.transactions.SignedTransaction;
import org.apache.activemq.artemis.core.server.group.impl.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
public class Controller {
    private final CordaRPCOps proxy;
    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller(NodeRPCConnection rpc) {
        this.proxy = rpc.proxy;
    }

    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return "Define an endpoint here.";
    }

@POST
@Path("inicializaPropriedadeTransacao")
public Response inicializaPropriedadeTransacao(PropriedadeBean propriedadeBean) {
    Party proprietario = proxy.partiesFromName(PropriedadeBean
            .getProprietario(), false).iterator().next();
   try {
       final SignedTransaction signedTx = proxy.startFlowDynamic(inicializaPropriedadeFlow.class,
               propriedadeBean.getPropriedadeId(),
               propriedadeBean.getPropriedadeEndereco(),
               propriedadeBean.getPropriedadePreco(),
               propriedadeBean.getCompradorId(),
               propriedadeBean.getVendedorId(),
               propriedadeBean.getIsHipoteca(),
               proprietario).getReturnValue().get();

       final String msg = ("Transacao efetuada com sucesso", signedTX.getId());

       return null;
   } catch (Throwable ex){
       final String msg = ex.getMessage();
       return  Response.status(Response.Status.BAD_REQUEST).entity(msg).buils();
   }
}
}
