package com.template.schema;

import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

public class PropertyDetailSchemaV1 extends MappedSchema {

    public PropertyDetailSchemaV1() {
        super(PropertyDetailSchemaV1.class, 1, ImmutableList.of(PersistentPropertyDetails.class));

    }

    @Entity
    @Table(name= "registro_propriedade")
    public static class PersistentPropertyDetails extends PersistentState {
        @Column(name="propriedadeId")
        private final int propriedadeId;
        @Column(name="propriedadeEndereco")
        private final String propriedadeEndereco;
        @Column(name="propriedadePreco")
        private final int propriedadePreco;
        @Column(name="compradorId")
        private final int compradorId;
        @Column(name="linearId")
        private final UUID linearId;

        public PersistentPropertyDetails() {
            this.propriedadeId = 0;
            this.propriedadeEndereco = "";
            this.propriedadePreco = 0;
            this.compradorId = 0;
            this.linearId = null;
        }

        public PersistentPropertyDetails(int propriedadeId, String propriedadeEndereco, int propriedadePreco, int compradorId, UUID linearId) {
            this.propriedadeId = propriedadeId;
            this.propriedadeEndereco = propriedadeEndereco;
            this.propriedadePreco = propriedadePreco;
            this.compradorId = compradorId;
            this.linearId = linearId;
        }
    }
}
