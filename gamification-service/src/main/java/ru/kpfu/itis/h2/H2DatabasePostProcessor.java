package ru.kpfu.itis.h2;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class H2DatabasePostProcessor implements InitializingBean {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.afterPropertiesSet();

        if (isH2(jdbcTemplate)) {
            jdbcTemplate.execute("create alias if not exists version for \"" + H2Functions.class.getName() + ".version\"");

            jdbcTemplate.execute("create alias if not exists date for \"" + H2Functions.class.getName() + ".date\"");

            jdbcTemplate.execute("create alias if not exists date_part for \"" + H2Functions.class.getName() + ".date_part\"");

            //PostGIS functions
            jdbcTemplate.execute("create alias if not exists ST_MakePoint for \"" + H2Functions.class.getName() + ".ST_MakePoint\"");
            jdbcTemplate.execute("create alias if not exists ST_Distance_Sphere for \"" + H2Functions.class.getName() + ".ST_Distance_Sphere\"");
            jdbcTemplate.execute("create alias if not exists ST_SetSRID for \"" + H2Functions.class.getName() + ".ST_SetSRID\"");
            jdbcTemplate.execute("create alias if not exists ST_Contains for \"" + H2Functions.class.getName() + ".ST_Contains\"");

            jdbcTemplate.execute("create alias if not exists StandGeometry for \"" + H2Functions.class.getName() + ".StandGeometry\"");
        }
    }

    private boolean isH2(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.execute(new ConnectionCallback<Boolean>() {
            @Override
            public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
                return "H2".equals(con.getMetaData().getDatabaseProductName());
            }
        });
    }

}
