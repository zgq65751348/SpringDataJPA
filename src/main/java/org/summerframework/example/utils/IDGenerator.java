package org.summerframework.example.utils;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class IDGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        Object id = SnowflakeIdHelper.nextId();
        if (id != null) {
            return (Serializable) id;
        }
        return super.generate(session, object);
    }
}
