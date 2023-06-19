package br.com.villa.person.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class FlywayService {

    private final DataSource dataSource;
    private Flyway flyway;

    @Autowired
    public FlywayService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initialize() {
        FluentConfiguration configuration = Flyway.configure()
                .dataSource(dataSource);
        flyway = configuration.load();
        flyway.migrate();
    }

    public void printMigrationInfo() {
        MigrationInfo[] migrationInfo = flyway.info().all();
        for (MigrationInfo info : migrationInfo) {
            System.out.println(info.getDescription());
        }
    }

}

