package ru.itis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.itis.front.Front;

public class Application {
    public static void main(String[] args) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:postgresql://localhost:5432/11-901");
//        config.setDriverClassName("org.postgresql.Driver");
//        config.setUsername("postgres");
//        config.setPassword("qwerty007");
//        config.setMaximumPoolSize(20);
//        HikariDataSource dataSource = new HikariDataSource(config);
//
//        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(namedParameterJdbcTemplate);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        MailsService mailsService = new MailsServiceMockImpl();
//        UsersService usersService = new UsersServiceImpl(usersRepository, passwordEncoder, mailsService);
//        Front front = new FrontImpl(usersService);
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Front front = context.getBean("front", Front.class);
        front.run();
    }
}

