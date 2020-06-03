package zoomapi.handlers;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteGenericTableHandler<T> {
    protected Field[] fields;
    private Class<T> type;
    protected static Connection c;
    private static boolean cInitialized;

    public SQLiteGenericTableHandler(Class<T> type) {
        this.type = type;
        this.fields = type.getDeclaredFields();

        if (!cInitialized) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:zoom_cache.db");
                c.setAutoCommit(false);
                cInitialized = true;
                System.out.println("Opened database successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public boolean isValidCache(long timestamp) {
        long curTime = System.currentTimeMillis();
        return curTime - timestamp <= 60 * 60 * 1000;
    }

    public String matchSQLType(String s) {
        switch (s) {
            case "Boolean":
            case "Integer":
            case "long":
                return "INTEGER";
            default: return "TEXT";
        }
    }

    public void createTable() {
        List<String> terms = new ArrayList<>();
        for (Field field : fields) {
            String fieldType = matchSQLType(field.getType().getSimpleName());
            String currentFieldName = field.getName();
            boolean isNotNull = false;
            if (currentFieldName.contains("id") || currentFieldName.contains("Id")) {
                isNotNull = true;
            }
            if (isNotNull) terms.add(currentFieldName + " " + fieldType + " NOT NULL");
            else terms.add(currentFieldName + " " + fieldType);
        }
        String body = String.join(", ", terms);
        body = "id INTEGER PRIMARY KEY AUTOINCREMENT, " + body;
        String query = "CREATE TABLE " + type.getSimpleName() + "s (" + body + ");";
        //System.out.println(query);
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet get() {
        ResultSet rs = null;
        String query = String.format("SELECT * FROM %s;", type.getSimpleName() + "s");
        try {
            Statement stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet get(String field, String key) {
        HashMap<String, String> pairs = new HashMap<>();
        pairs.put(field, key);
        return get(pairs);
    }

    public ResultSet get(HashMap<String, String> pairs) {
        ResultSet rs = null;
        List<String> terms = new ArrayList<>();
        for (HashMap.Entry<String, String> pair : pairs.entrySet()) {
            terms.add(pair.getKey() + " = '" + pair.getValue() + "'");
        }

        String where = String.join(" AND ", terms);
        String query = String.format("SELECT * FROM %s WHERE %s;", type.getSimpleName() + "s", where);
        try {
            Statement stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public void insert(HashMap<String, String> pairs) {
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (HashMap.Entry<String, String> pair : pairs.entrySet()) {
            keys.add(pair.getKey());
            try {
                String currentType = matchSQLType(type.getDeclaredField(pair.getKey()).getType().getSimpleName());
                //System.out.println(pair.getKey() + " " + currentType);
                if (currentType.equals("TEXT")) values.add("'" + pair.getValue() + "'");
                else values.add(pair.getValue());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        String query = String.format("INSERT INTO %s (%s) VALUES (%s);", type.getSimpleName() + "s", String.join(", ", keys), String.join(", ", values));
        System.out.println(query);

        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(HashMap<String, String> wherePairs, HashMap<String, String> pairs) {
        List<String> whereTerms = new ArrayList<>();
        List<String> terms = new ArrayList<>();

        for (HashMap.Entry<String, String> pair : wherePairs.entrySet()) {
            try {
                String currentType = matchSQLType(type.getDeclaredField(pair.getKey()).getType().getSimpleName());
                if (currentType.equals("TEXT")) whereTerms.add(pair.getKey() + " = '" + pair.getValue() + "'");
                else whereTerms.add(pair.getKey() + " = " + pair.getValue());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        for (HashMap.Entry<String, String> pair : pairs.entrySet()) {
            try {
                String currentType = matchSQLType(type.getDeclaredField(pair.getKey()).getType().getSimpleName());
                if (currentType.equals("TEXT")) terms.add(pair.getKey() + " = '" + pair.getValue() + "'");
                else terms.add(pair.getKey() + " = " + pair.getValue());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        String query = String.format("UPDATE %s SET %s WHERE %s;", type.getSimpleName() + "s", String.join(", ", terms), String.join(" AND ", whereTerms));
        //System.out.println(query);

        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(HashMap<String, String> wherePairs) {
        List<String> whereTerms = new ArrayList<>();

        for (HashMap.Entry<String, String> pair : wherePairs.entrySet()) {
            try {
                String currentType = matchSQLType(type.getDeclaredField(pair.getKey()).getType().getSimpleName());
                if (currentType.equals("TEXT")) whereTerms.add(pair.getKey() + " = '" + pair.getValue() + "'");
                else whereTerms.add(pair.getKey() + " = " + pair.getValue());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        String query = String.format("DELETE FROM %s WHERE %s;", type.getSimpleName() + "s", String.join(" AND ", whereTerms));
        //System.out.println(query);

        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
