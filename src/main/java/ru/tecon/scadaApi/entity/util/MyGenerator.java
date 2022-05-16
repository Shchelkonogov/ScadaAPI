package ru.tecon.scadaApi.entity.util;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Генератор для создания id в базе, для entity
 * @author Maksim Shchelkonogov
 */
public class MyGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        Dialect dialect = session.getJdbcServices().getDialect();

        if (dialect instanceof Oracle12cDialect) {
            return session.createNativeQuery("select GET_GUID_BASE64() from DUAL")
                    .getSingleResult()
                    .toString();
        }

        if (dialect instanceof PostgreSQL95Dialect) {
            return session.createNativeQuery("select GET_GUID_BASE64()")
                    .getSingleResult()
                    .toString();
        }

        throw new HibernateException("unknown dialect " + dialect);
    }
}
