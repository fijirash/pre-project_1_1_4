package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Util {
    private final static String url = "jdbc:mysql://localhost:3306/user_db";
    private final static String user = "root";
    private final static String password = "root";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", url)
                        .setProperty("hibernate.connection.username", user)
                        .setProperty("hibernate.connection.password", password)
                        .setProperty("hibernation.dialect", "org.hibernate.dialect.MySQLDialect")
                        .setProperty("hibernate.show_sql", "true")
                        .setProperty("hibernate.format_sql", "true")
                        .setProperty("hibernate.hbm2ddl.auto", "create")
                        .addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
