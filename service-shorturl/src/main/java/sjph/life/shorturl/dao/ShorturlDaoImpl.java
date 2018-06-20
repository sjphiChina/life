package sjph.life.shorturl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sjph.life.shorturl.database.ShorturlSchema;
import sjph.life.shorturl.model.Shorturl;

@Repository
public class ShorturlDaoImpl implements ShorturlDao {

    //@formatter:off
    private static final String FULL_TABLE_SQL =
            ShorturlSchema.ID + ", " +
            ShorturlSchema.SHORTURL + ", " +
            ShorturlSchema.LONGURL;

    private static final String CREATE_SHORTURL =
            "INSERT INTO " +
                    ShorturlSchema.TABLE_NAME +
                    " (" +
                    ShorturlSchema.LONGURL + ", " +
                    ShorturlSchema.CREATED_DT +
                    ") " +
            "VALUES " +
                    "(?, ?)";

    private static final String SELECT_FULL_TABLE =
            "SELECT " +
                    FULL_TABLE_SQL + " " +
            "FROM " +
                    ShorturlSchema.TABLE_NAME + " " +
            "WHERE ";

    private static final String FIND_BY_SHORTURL =
            SELECT_FULL_TABLE + ShorturlSchema.SHORTURL + " = ?";

    private static final String FIND_BY_LONGURL =
            SELECT_FULL_TABLE + ShorturlSchema.LONGURL + " = ?";

    private static final String UPDATE_TABLE_SQL =
            "UPDATE " +
                     ShorturlSchema.TABLE_NAME;

    private static final String UPDATE_SHORTURL =
            UPDATE_TABLE_SQL + " " +
                    "SET " +
            ShorturlSchema.SHORTURL + " = ? " +
                     "WHERE " +
            ShorturlSchema.ID + " = ?";
    //@formatter:on

    private final ShorturlRowMapper shorturlRowMapper = new ShorturlRowMapper();
    @Autowired(required = true)
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate            jdbcTemplate;

    @Override
    public long createShorturlRecord(Shorturl shorturlObject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(CREATE_SHORTURL,
                        new String[] { ShorturlSchema.ID.name() });
                ps.setString(1, shorturlObject.getLongurl());
                ps.setObject(2, new Date());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Shorturl findByShorturl(String shorturlString) {
        final Object[] sqlParameters = new Object[] { shorturlString };
        return jdbcTemplate.queryForObject(FIND_BY_SHORTURL, sqlParameters, shorturlRowMapper);
    }

    @Override
    public Shorturl findByLongurl(String longurlString) {
        final Object[] sqlParameters = new Object[] { longurlString };
        return jdbcTemplate.queryForObject(FIND_BY_LONGURL, sqlParameters, shorturlRowMapper);
    }

    @Override
    public int updateShorturlRecord(Shorturl shorturlObject) {
        return jdbcTemplate.update(UPDATE_SHORTURL, new PreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement stmt) throws SQLException {
                stmt.setString(1, shorturlObject.getShorturl());
                stmt.setLong(2, shorturlObject.getId());
            }
        });
    }

    private static final class ShorturlRowMapper implements RowMapper<Shorturl> {
        @Override
        public Shorturl mapRow(ResultSet arg0, int arg1) throws SQLException {
            return populateShorturlRecord(arg0);
        }
    }

    private static Shorturl populateShorturlRecord(final ResultSet rs) throws SQLException {
        Shorturl shorturl = new Shorturl(rs.getLong(ShorturlSchema.ID.name()),
                rs.getString(ShorturlSchema.SHORTURL.name()),
                rs.getString(ShorturlSchema.LONGURL.name()));
        return shorturl;
    }

    @Override
    public int deleteById(long id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteByShorturl(String shorturlString) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteByLongurl(String longurlString) {
        // TODO Auto-generated method stub
        return 0;
    }

}
