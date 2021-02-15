package doa;

import beans.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

    public int savePerson(Person person){
        return jdbcTemplate.update("insert into Person values(?, ?, ?, ?)", person.getName(), person.getLastName(),
                person.getAge(), person.getId());
    }

    public int deletePerson(Person person){
        return jdbcTemplate.update("delete from Person where id = ?", person.getId());
    }

    public int updatePerson(Person person){
        return jdbcTemplate.update("update person set name = ?, lastName = ?, age = ? where id = ?",
                person.getName(), person.getLastName(), person.getAge(), person.getId());
    }

    public Boolean insertPersonByPreparedStatement(final Person person){
        return jdbcTemplate.execute("insert into Person values(?, ?, ?, ?)", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1, person.getName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setInt(3, person.getAge());
                preparedStatement.setInt(4, person.getId());

                return preparedStatement.execute();
            }
        });
    }

}
