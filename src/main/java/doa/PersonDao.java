package doa;

import beans.Person;
import org.springframework.jdbc.core.JdbcTemplate;

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



}
