package ru.kpfu.itis;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Mappings;
import org.springframework.orm.hibernate3.TypeDefinitionBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

/**
 * Created by romanplatonov on 16.06.15.
 */
public class GamificationFactoryBean extends LocalSessionFactoryBean {

    private TypeDefinitionBean[] typeDefinitions;

    public TypeDefinitionBean[] getTypeDefinitions() {
        return typeDefinitions;
    }

    public void setTypeDefinitions(TypeDefinitionBean[] aTypeDefinitions) {
        typeDefinitions = aTypeDefinitions;
    }

    @Override
    protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder aSfb) {
        if (this.typeDefinitions != null) {
            // Register specified Hibernate type definitions.
            Mappings mappings = aSfb.createMappings();
            for (TypeDefinitionBean typeDef : this.typeDefinitions) {
                mappings.addTypeDef(typeDef.getTypeName(), typeDef.getTypeClass(), typeDef.getParameters());
            }
        }
        return super.buildSessionFactory(aSfb);
    }

}