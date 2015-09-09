package ru.kpfu.itis.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import ru.kpfu.itis.model.Badge;
import ru.kpfu.itis.model.Subject;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.enums.BadgeCategory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * Created by Rigen on 08.09.2015.
 */
public class BadgeWriter {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {

        Properties hibernateProperties = getHibernateProperties();
        DataSource dataSource = getDatasourceConfiguration();
        LocalSessionFactoryBean localSessionFactoryBean = generateSessionFactoryBean(new String[]{"ru.kpfu.itis.model"},
                dataSource, hibernateProperties);

        SessionFactory sessionFactory = localSessionFactoryBean.getObject();

        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return sessionFactory;
    }

    private static DataSource getDatasourceConfiguration() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://188.226.151.91:5432/gamification_2015");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    private static LocalSessionFactoryBean generateSessionFactoryBean(String[] basePackage, DataSource dataSource,
                                                                      Properties hibernateProperties) {

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan(basePackage);
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        try {
            localSessionFactoryBean.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localSessionFactoryBean;
    }

    private static Properties getHibernateProperties() {

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.put("hibernate.show_sql", false);
        hibernateProperties.put("hibernate.generate_statistics", false);
        hibernateProperties.put("hibernate.use_sql_comments", false);
        hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

        return hibernateProperties;
    }

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        boolean wantContinue = true;

        while (wantContinue) {
            Badge newBadge = new Badge();
            Scanner scanner = new Scanner(System.in);
            System.out.println("!!! If you want enter null value, write \"null\" !!!");
            System.out.println("Enter name:");
            newBadge.setName(checkNull(scanner.nextLine()));
            System.out.println("Description:");
            newBadge.setDescription(checkNull(scanner.nextLine()));
            System.out.println("Image: (Path to source in disk space. Example: \"/images/badges/java.png\")");
            newBadge.setImage(checkNull(scanner.nextLine()));
            if (newBadge.getImage() != null && !newBadge.getImage().startsWith("/"))
                newBadge.setImage("/" + newBadge.getImage());
            System.out.println("Badge's type: (\"COMMON\" or \"SPECIAL\", 0 or 1)");
            boolean argumentIsWrong = true;
            while (argumentIsWrong) {
                String type = checkNull(scanner.nextLine().trim());
                if (type == null) {
                    argumentIsWrong = false;
                    break;
                }
                switch (type) {
                    case "0":
                        newBadge.setType(BadgeCategory.COMMON);
                        argumentIsWrong = false;
                        break;
                    case "1":
                        newBadge.setType(BadgeCategory.SPECIAL);
                        argumentIsWrong = false;
                        break;
                    default:
                        try {
                            newBadge.setType(BadgeCategory.valueOf(type));
                            argumentIsWrong = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Wrong type! Please enter right type:");
                            argumentIsWrong = true;
                        }
                }
            }
            System.out.println("Badge's tasks: (IDs on database, separated with comma. Example: 13, 15, 17)");
            StringTokenizer idTokenizer = new StringTokenizer(scanner.nextLine().trim(), ",");
            Long id;
            String rowId;
            ArrayList<Task> tasks = new ArrayList<>();
            while (idTokenizer.hasMoreTokens()) {
                argumentIsWrong = true;
                rowId = checkNull(idTokenizer.nextToken());
                if (rowId == null)
                    break;
                while (argumentIsWrong) {
                    try {
                        id = Long.valueOf(rowId);
                        argumentIsWrong = false;
                    } catch (NumberFormatException e) {
                        System.out.println(rowId + "is invalid ID. Please enter this ID again:");
                        rowId = scanner.nextLine();
                        argumentIsWrong = true;
                        continue;
                    }
                    Task task = (Task) session.get(Task.class, id);
                    if (task != null) {
                        tasks.add(task);
                        argumentIsWrong = false;
                    } else {
                        System.out.println("Task with ID " + id + " not found. Please enter ID again:");
                        rowId = scanner.nextLine();
                        argumentIsWrong = true;
                    }
                }
            }
            newBadge.setTasks(tasks);
            if (BadgeCategory.COMMON.equals(newBadge.getType())) {
                System.out.println("Max mark:");
                argumentIsWrong = true;
                while (argumentIsWrong) {
                    try {
                        rowId = checkNull(scanner.nextLine());
                        if (rowId == null) {
                            argumentIsWrong = false;
                            break;
                        }
                        newBadge.setMaxMark(Integer.parseInt(rowId));
                        argumentIsWrong = false;
                    } catch (NumberFormatException e) {
                        System.out.println("Entered max mark is not number. Please enter again:");
                        argumentIsWrong = true;
                    }
                }
                System.out.println("Subject id:");
                argumentIsWrong = true;
                while (argumentIsWrong) {
                    try {
                        rowId = checkNull(scanner.nextLine());
                        if (rowId == null) {
                            argumentIsWrong = false;
                            break;
                        }
                        id = Long.parseLong(rowId);
                        argumentIsWrong = false;
                    } catch (NumberFormatException e) {
                        System.out.println("You entered invalid ID. Enter this ID again:");
                        argumentIsWrong = true;
                        continue;
                    }
                    Subject subject = (Subject) session.get(Subject.class, id);
                    if (subject != null) {
                        newBadge.setSubject(subject);
                        argumentIsWrong = false;
                    } else {
                        System.out.println("Subject with ID " + id + " not found. Enter right ID:");
                        rowId = scanner.nextLine();
                        argumentIsWrong = true;
                    }
                }
            } else {
                newBadge.setMaxMark(tasks.size());
            }
            newBadge = (Badge) session.get(Badge.class, (Long) session.save(newBadge));
            for (Task task : tasks) {
                task.setBadge(newBadge);
                session.save(task);
            }

            System.out.println("Do you want to add another badge? Y/N");
            String answer = scanner.nextLine();
            if(!answer.toUpperCase().equals("Y")) {
                wantContinue = false;
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    private static String checkNull(String string) {
        return string.trim().equals("null") ? null : string;
    }

}
