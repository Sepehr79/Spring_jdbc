package doa;

import beans.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Injection by constructor
     */
    public PersonDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Injection by setter
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

    public List<Person> getPersons(){
        return jdbcTemplate.query("select * from Person", new ResultSetExtractor<List<Person>>() {
            @Override
            public List<Person> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Person> people = new ArrayList<>();

                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    int age = resultSet.getInt("age");
                    int id = resultSet.getInt("id");

                    Person person = new Person(name, lastName, age, id);
                    people.add(person);
                }

                return people;
            }
        });
    }

    public List<Person> getPeopleRowMapper(){
        return jdbcTemplate.query("select * from Person", new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet resultSet, int index) throws SQLException {
                Person person = new Person(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getInt("age"), resultSet.getInt("id"));

                System.out.println(index);

                return person;
            }
        });
    }

    public void saveByMap(Person person){
        String query = "insert into Person values(:name, :lastName, :age, :id)";

        Map<String, Object> map = new HashMap<>();
        map.put("name", person.getName());
        map.put("lastName", person.getLastName());
        map.put("age", person.getAge());
        map.put("id", person.getId());

        namedParameterJdbcTemplate.execute(query, map, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                return preparedStatement.executeUpdate();
            }
        });
    }
}
