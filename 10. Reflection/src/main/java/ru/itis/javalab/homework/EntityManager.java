package ru.itis.javalab.homework;


import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;


public class EntityManager {
    private DataSource dataSource;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))

        StringBuilder builder = new StringBuilder();
        builder.append("create table if not exists ").append("\"").append(tableName).append("\"").append(" ( ");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            builder.append(field.getName());
            if (field.getType().getSimpleName().equals("Long")) {
                builder.append(" bigint").append(", ");
            }
            if (field.getType().getSimpleName().equals("Integer")) {
                builder.append(" int").append(", ");
            }
            if (field.getType().getSimpleName().equals("String")) {
                builder.append(" varchar(255)").append(", ");
            }
            if (field.getType().getSimpleName().equals("boolean")) {
                builder.append(" boolean").append(", ");
            }
        }
        String substring = builder.substring(0, builder.length() - 2);
        substring += " ); ";
        System.out.println(substring);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(substring)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into

        StringBuilder builder = new StringBuilder();
        builder.append("insert into ").append("\"").append(tableName).append("\"").append(" ( ");
        Field[] fields = classOfEntity.getDeclaredFields();
        for (Field field : fields) {
            builder.append(field.getName()).append(", ");
        }
        StringBuilder substring = new StringBuilder(builder.substring(0, builder.length() - 2));
        substring.append(" ) values ( ");
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            if (field.getType().getSimpleName().equals("String")) {
                substring.append("'").append(value).append("'").append(", ");
            } else {
                substring.append(value).append(", ");
            }
        }
        builder = new StringBuilder(substring.substring(0, substring.length() - 2));
        builder.append(" );");
        System.out.println(builder);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(builder.toString())) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        // сгенеририровать select

        StringBuilder builder = new StringBuilder();
        StringBuilder substring = new StringBuilder();
        builder.append("select * from ").append("\"").append(tableName).append("\"").append(" where id=");
        if (idType.getSimpleName().equals("Long")) {
            builder.append(idValue);
            substring = new StringBuilder(builder.substring(0, builder.length()));
        } else {
            substring.append(builder).append(idValue);
        }
        substring.append(" ;");
        System.out.println(substring);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(substring.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {

            Field[] fields = resultType.getDeclaredFields();
            T result = resultType.newInstance();

            if (resultSet.next()) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType().getSimpleName().equals("String")) {
                        field.set(result, resultSet.getString(field.getName()));
                    }
                    if (field.getType().getSimpleName().equals("Long")) {
                        field.set(result, resultSet.getLong(field.getName()));
                    }
                    if (field.getType().getSimpleName().equals("Integer")) {
                        field.set(result, resultSet.getInt(field.getName()));
                    }
                    if (field.getType().getSimpleName().equals("boolean")) {
                        field.set(result, resultSet.getBoolean(field.getName()));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
