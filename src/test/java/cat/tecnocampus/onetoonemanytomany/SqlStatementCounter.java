package cat.tecnocampus.onetoonemanytomany;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Records every SQL statement Hibernate sends to the database, so the tests can
 * check how many inserts, selects, updates and deletes an operation needs.
 * It is registered in the tests with the property
 * {@code spring.jpa.properties.hibernate.session_factory.statement_inspector}.
 */
public class SqlStatementCounter implements StatementInspector {

    private static final List<String> statements = new CopyOnWriteArrayList<>();

    @Override
    public String inspect(String sql) {
        statements.add(sql);
        return sql;
    }

    public static void reset() {
        statements.clear();
    }

    public static long inserts() {
        return count("insert");
    }

    public static long selects() {
        return count("select");
    }

    public static long updates() {
        return count("update");
    }

    public static long deletes() {
        return count("delete");
    }

    public static List<String> statements() {
        return List.copyOf(statements);
    }

    private static long count(String keyword) {
        return statements.stream()
                .filter(sql -> sql.strip().toLowerCase(Locale.ROOT).startsWith(keyword))
                .count();
    }
}
