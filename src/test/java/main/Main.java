package main;

import beans.Person;
import doa.PersonDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    PersonDao personDao = (PersonDao) applicationContext.getBean("dao");

    @Before
    public void clearDataBase(){
        personDao.getJdbcTemplate().update("delete from Person");
    }

    @Test
    public void testInsert(){
        int result = personDao.savePerson(new Person("sepehr", "mollaei", 20, 15));
        System.out.println(result);
    }

    @Test
    public void testDelete(){
        int result = personDao.deletePerson(new Person("sepehr", "mollaei", 20, 13));
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        int result = personDao.updatePerson(new Person("parham", "kabiri", 19, 12));
        System.out.println(result);
    }

    @Test
    public void testInsertPersonByPreparedStatement(){
        boolean result = personDao.insertPersonByPreparedStatement(new Person("ahmad", "salimi", 30, 456454));
        System.out.println(result);
    }

    @Test
    public void testGetPeople(){
        List<Person> people = personDao.getPersons();
        Assert.assertEquals(people.size(), 0);
    }

    @Test
    public void testGetPeopleRowMapper(){
        personDao.savePerson(new Person("sepehr", "mollaei", 20, 9821160));
        personDao.savePerson(new Person("sepehr", "mollaei", 20, 9821456));
        personDao.savePerson(new Person("sepehr", "mollaei", 20, 982456));
        personDao.savePerson(new Person("sepehr", "mollaei", 20, 9824560));

        List<Person> people = personDao.getPeopleRowMapper();

        Assert.assertEquals(people.size(), 4);
    }

    @Test
    public void testSaveByMap(){
        personDao.saveByMap(new Person("sepehr", "mollaei", 20, 9822116));
    }
}
