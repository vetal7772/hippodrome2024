import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void constructor_NullListParamPassed_ThrowsIllegalArgumentException() {
        String expectedName = "Horses cannot be null.";

        List<Horse> horses = null;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedName, exception.getMessage());
    }

    @Test
    void constructor_EmptyListParamPassed_ThrowsIllegalArgumentException() {
        String expectedName = "Horses cannot be empty.";

        List<Horse> horses = new ArrayList<>();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedName, exception.getMessage());
    }

    @Test
    void getHorses_ReturnsListWithAllHorsesInOrder() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, i, i * 2));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        assertNotNull(hippodrome.getHorses());
        assertEquals(30, hippodrome.getHorses().size());

        for (int i = 0; i < 30; i++) {
            assertEquals("Horse" + i, hippodrome.getHorses().get(i).getName());
        }
    }

    @Test
    void move_CallsMoveMethodForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }


    @Test
    void getWinner_ReturnsCorrectWinner() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("Horse10",1,10),
                new Horse("Horse20",3,20),
                new Horse("Horse30",5,30)
        ));
        assertEquals("Horse30",hippodrome.getWinner().getName());
    }
}