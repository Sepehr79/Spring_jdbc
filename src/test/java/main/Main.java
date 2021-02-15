package main;

import beans.Person;
import doa.PersonDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    @Test
    public void testInsert(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        PersonDao personDao = (PersonDao) applicationContext.getBean("dao");

        int result = personDao.savePerson(new Person("sepehr", "mollaei", 20, 15));

        System.out.println(result);


    }

    @Test
    public void testDelete(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        PersonDao personDao = (PersonDao) applicationContext.getBean("dao");

        int result = personDao.deletePerson(new Person("sepehr", "mollaei", 20, 13));

        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        PersonDao personDao = (PersonDao) applicationContext.getBean("dao");

        int result = personDao.updatePerson(new Person("parham", "kabiri", 19, 12));

        System.out.println(result);
    }

    @Test
    public void testInsertPersonByPreparedStatement(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        PersonDao personDao = (PersonDao) applicationContext.getBean("dao");

        boolean result = personDao.insertPersonByPreparedStatement(new Person("ahmad", "salimi", 30, 456454));

        System.out.println(result);
    }
}
