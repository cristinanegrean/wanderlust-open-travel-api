package cristina.tech.blog.travel.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.EnhancedUserType;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.*;

import static org.hibernate.type.StandardBasicTypes.TIMESTAMP;

/**
 * Replace usage with org.joda.time.contrib.hibernate.PersistentDateTime.
 */
public class PersistentDateTime implements EnhancedUserType, Comparable {

    private static final int[] SQL_TYPES = new int[]{Types.TIMESTAMP};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class<?> returnedClass() {
        return DateTime.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        DateTime dtx = (DateTime) x;
        DateTime dty = (DateTime) y;

        return dtx.equals(dty);
    }

    @Override
    public int compareTo(Object o) {
        PersistentDateTime dtx = (PersistentDateTime) o;
        return dtx.compareTo(this);
    }

    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        return nullSafeGet(rs, names[0]);
    }

    public Object nullSafeGet(ResultSet resultSet, String string) throws SQLException {
        Object timestamp = resultSet.getTimestamp(string);
        if (timestamp == null || resultSet.wasNull()) {
            return null;
        } else {
            return new DateTime(timestamp);
        }
    }

    /**
     * Trouble method
     */
    public Object nullSafeGet(ResultSet rs, String columnName, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        return nullSafeGet(rs, new String[]{columnName}, session, owner);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (value == null) {
            TIMESTAMP.nullSafeSet(preparedStatement, null, index, sessionImplementor);
        } else {
            // Store in Amsterdam time zone.
            DateTime dateTime = (DateTime) value;
            Timestamp timeStamp = new Timestamp(dateTime.toDate().getTime());
            preparedStatement.setTimestamp(index, timeStamp);
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        return new DateTime(value);
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object value) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    public String objectToSQLString(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public String toXMLString(Object value) {
        return value.toString();
    }

    @Override
    @Deprecated
    public Object fromXMLString(String xmlValue) {
        return new DateTime(xmlValue);
    }

}

