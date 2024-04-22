import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CopyExampleUnitTest {

    @Test
    public void givenOriginalPerson_whenShallowCopying_thenCopiedPersonHasSameAge() {
        Person originalPerson = new Person("John", 30, 18, 30, new Address("Unit Test Test Main St.", "Cape town", "South Africa"));

        Person copiedPerson = null;
        try {
            copiedPerson = originalPerson.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assertEquals(30, originalPerson.getAge(), "Original person's age should remain unchanged after shallow copying");
        assertEquals(originalPerson.getAge(), copiedPerson.getAge(), "Copied person should have the same age as the original person after shallow copying");


        copiedPerson.setAge(35);
        assertEquals(35, copiedPerson.getAge(), "Copied person's age should change");
        assertEquals(35, originalPerson.getAge(), "Changes in copied person should affect the original person for shallow copying");

        originalPerson.setAge(40);
        assertEquals(40, copiedPerson.getAge(), "Changes in original person should affect the copied person for shallow copying");
    }

    @Test
    public void givenOriginalPerson_whenDeepCopying_thenCopiedPersonHasDifferentAge() {
        Person originalPerson = new Person("John", 30, 18, 30, new Address("UnitTest  Main St.", "Pal Alto", "USA"));

        Person copiedPerson = originalPerson.deepClone();

        assertEquals(30, originalPerson.getAge(), "Original person's age should remain unchanged after deep copying");
        assertEquals(30, copiedPerson.getAge(), "Copied person's age should be independent of the original person after deep copying");

        copiedPerson.setAge(35);
        assertNotEquals(originalPerson.getAge(), copiedPerson.getAge(), "Changes in copied person should not affect the original person for deep copying");
    }

    @Test
    public void givenPersonWithMutableProperty_whenDeepCopying_thenOriginalAndCopiedPersonPropertiesAreDifferent() {
        Person originalPerson = new Person("Alice", 15, 18, 25, new Address("123 Main St.", "City", "Country"));
        originalPerson.setAddress(new Address("123 Main St.", "City", "Country"));

        Person copiedPerson = originalPerson.deepClone();
        copiedPerson.getAddress().setCity("New City");

        assertEquals("City", originalPerson.getAddress().getCity(), "Original person's address city should remain unchanged after deep copying");
        assertEquals("New City", copiedPerson.getAddress().getCity(), "Copied person's address city should be different from original after deep copying");
    }

    @Test
    public void givenOriginalPerson_whenCloneNotSupported_thenThrowException() {
        Person originalPerson = new Person("Jane", 30, 18, 28, new Address("Throw exception Test", "Dubai", "UAE"));

        Person originalPersonWithException = new Person(originalPerson.getName(), originalPerson.getAge(), originalPerson.getHeight(), originalPerson.getWeight(), originalPerson.getAddress()) {
            @Override
            public Person clone() throws CloneNotSupportedException {
                throw new CloneNotSupportedException();
            }
        };

        Throwable exception = assertThrows(CloneNotSupportedException.class, () -> originalPersonWithException.clone());


        assertNotNull(exception);
        assertEquals(CloneNotSupportedException.class, exception.getClass());
    }


    @Test
    public void givenOriginalPerson_whenShallowCopying_thenChangingCopiedPersonAffectsOriginal() {
        Person originalPerson = new Person("John", 30, 18, 30, new Address("Unit Test Test Main St.", "Cape town", "South Africa"));

        Person copiedPerson = null;
        try {
            copiedPerson = originalPerson.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        copiedPerson.setAge(35);
        assertEquals(35, copiedPerson.getAge(), "Copied person's age should change");
        assertEquals(35, originalPerson.getAge(), "Changes in copied person should affect the original person for shallow copying");

        originalPerson.setAge(40);
        assertEquals(40, copiedPerson.getAge(), "Changes in original person should affect the copied person for shallow copying");
    }
}
