package com.alibeta.easydict.cache;

import com.alibeta.easydict.util.JdbcUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态数据处理类
 */
public class CacheFactory {

    private static Log logger = LogFactory.getLog(CacheFactory.class);

    public static List<Map<Object, Object>> items = new ArrayList<Map<Object, Object>>();

    public static List<Map<Object, Object>> getDictData(String table, String typeCol, String codeCol, String textCol) {
        if (items.isEmpty()) {
            Connection connection = null;
            Statement stmt = null;
            try {
                connection = JdbcUtil.getConnection();
                StringBuilder builder = new StringBuilder();
                builder.append(" SELECT ").append(typeCol + ",").append(codeCol + ",").append(textCol)
                        .append(" FROM ").append(table);
                stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(builder.toString());
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    HashMap<Object, Object> item = new HashMap<Object, Object>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String columnValue = resultSet.getString(columnName);
                        item.put(columnName, columnValue);
                    }
                    items.add(item);
                }
            } catch (Exception e) {
                logger.error("数据库字典表查询报错！", e);
            } finally {
                JdbcUtil.closeDb(connection, stmt, null);
            }
        }
        return items;
    }

}