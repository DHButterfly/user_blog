package com.dl.blog.config.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.Date;

@MappedTypes(DateTime.class)
@SuppressWarnings("rawtypes")
public class JodaDateTimeTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {

        Timestamp timestamp = null;

        if (parameter != null) {
            DateTime date = (DateTime) parameter;
            timestamp = Timestamp.valueOf(date.toString("yyyy-MM-dd HH:mm:ss"));
        }

        ps.setTimestamp(i, timestamp);
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        Object o = rs.getObject(columnName);

        if (rs.wasNull()) {
            return null;
        }

        if (o instanceof Date || o instanceof String) {
            return new DateTime(o);
        } else {
            return o;
        }

    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object o = cs.getObject(columnIndex);

        if (cs.wasNull()) {
            return null;
        }

        if (o instanceof Date || o instanceof String) {
            return new DateTime(o);
        } else if (o instanceof Timestamp) {
            Timestamp t = (Timestamp) o;
            return new DateTime(t.getTime());
        } else {
            return o;
        }

    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        Object o = rs.getObject(columnIndex);

        if (rs.wasNull()) {
            return null;
        }

        if (o instanceof Date || o instanceof String) {
            return new DateTime(o);
        } else {
            return o;
        }
    }

}

