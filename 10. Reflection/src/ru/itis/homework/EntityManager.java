package ru.itis.homework;

import javax.sql.DataSource;
import java.lang.reflect.Field;


public class EntityManager {
    private DataSource dataSource;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EntityManager() {

    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))


        StringBuilder builder = new StringBuilder();
        builder.append("create table ").append(tableName).append(" ( ");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getSimpleName().equals("Long")) {
                builder.append("bigint ").append(field.getName()).append(", ");
            }
            if (field.getType().getSimpleName().equals("Integer")) {
                builder.append("int ").append(field.getName()).append(", ");
            }
            if (field.getType().getSimpleName().equals("String")) {
                builder.append("varchar(255) ").append(field.getName()).append(", ");
            }
            if (field.getType().getSimpleName().equals("boolean")) {
                builder.append("boolean ").append(field.getName()).append(", ");
            }
        }
        String substring = builder.substring(0, builder.length() - 2);
        substring += " ); ";
        System.out.println(substring);
    }

    public void save(String tableName, Object entity) throws IllegalAccessException {
        Class<?> classOfEntity = entity.getClass();
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into

        StringBuilder builder = new StringBuilder();
        builder.append("insert into ").append(tableName).append(" ( ");
        Field[] fields = classOfEntity.getDeclaredFields();
        for (Field field : fields) {
            builder.append(field.getName()).append(", ");
        }
        StringBuilder substring = new StringBuilder(builder.substring(0, builder.length() - 2));
        substring.append(" ) values ( ");
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object value = field.get(entity);
            substring.append(value).append(", ");
        }
        builder = new StringBuilder(substring.substring(0, substring.length() - 2));
        builder.append(" );");


        System.out.println(builder);
    }


    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        // сгенеририровать select

        StringBuilder builder = new StringBuilder();
        StringBuilder substring = new StringBuilder();
        builder.append("select from ").append(tableName).append(" where id=");
        if (idType.getSimpleName().equals("Long")) {
            builder.append(idValue);
            substring = new StringBuilder(builder.substring(0, builder.length()));
        } else {
            substring.append(builder).append(idValue);
        }
        substring.append(" ;");
        System.out.println(substring);
        return null;
    }
}
