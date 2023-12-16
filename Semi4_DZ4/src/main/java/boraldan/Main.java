package boraldan;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Course.class);

        Course course = new Course("Arifmetika", 10);
        Course courseDB;

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            // Сохранение данных в БД
            session.persist(course);
            session.getTransaction().commit();
            System.out.println("Добавление произведено");
        } finally {
            sessionFactory.close();
        }

        configuration = new Configuration().addAnnotatedClass(Course.class);
        sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            // Получение из БД
            session.beginTransaction();
            courseDB = session.get(Course.class, course.getId());
            session.getTransaction().commit();
            System.out.println("Из БД получен объект: " + courseDB);
        } finally {
            sessionFactory.close();
        }

        configuration = new Configuration().addAnnotatedClass(Course.class);
        sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            // Изменение данных в БД
            courseDB.setDuration(6);
            session.beginTransaction();
            session.merge(courseDB);
            session.getTransaction().commit();
            System.out.println("Изменение данных в БД произведено");
        } finally {
            sessionFactory.close();
        }

        configuration = new Configuration().addAnnotatedClass(Course.class);
        sessionFactory = configuration.buildSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            // Удаление данных из БД
            session.beginTransaction();
            session.remove(courseDB);
            session.getTransaction().commit();
            System.out.println("Удаление данных из БД произведено");

        } finally {
            sessionFactory.close();
        }
    }
}