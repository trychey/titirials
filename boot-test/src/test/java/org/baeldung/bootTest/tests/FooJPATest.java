package org.baeldung.bootTest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.baeldung.bootTest.model.Foo;
import org.baeldung.bootTest.repository.FooRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FooJPATest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FooRepository repository;

    @Test
    public void findFooByName() {
        this.entityManager.persist(new Foo("Foo_Name_2"));
        Foo foo = this.repository.findOneByName("Foo_Name_2");
        assertNotNull(foo);
        assertEquals("Foo_Name_2",foo.getName());
        // Due to having Insert query for Foo with Id 1, so TestEntityManager generates new Id of 2       
        assertEquals(2l,foo.getId().longValue());
    }
}