import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    public void constructor_NullNameParamPassed_ThrowsIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
    }

    @Test
    public void constructor_NullNameParamPassed_EqualsMessageCannotBeNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n", "\t", "\t\t", "\t \t"})
    public void constructor_BlankNameParamPassed_ThrowsIllegalArgumentException(String name) {
        //Arrange
        String expectedName = "Name cannot be blank.";

        //Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));

        //Assert
        assertEquals(expectedName, exception.getMessage());
    }

    @Test
    public void constructor_NegativeSpeedParamPassed_ThrowsIllegalArgumentException() {
        String expectedName = "Speed cannot be negative.";
        String name = "testName";
        double speed = -3;
        double distance = 10;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals(expectedName, exception.getMessage());
    }

    @Test
    public void constructor_NegativeDistanceParamPassed_ThrowsIllegalArgumentException() {
        String expectedName = "Distance cannot be negative.";
        String name = "testName";
        double speed = 3;
        double distance = -10;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals(expectedName, exception.getMessage());
    }

    @Test
    void getName_ReturnsCorrectName() {
        String name = "TestName";
        double speed = 3;
        double distance = 10;
        Horse horse = new Horse(name, speed, distance);

        String actualName = horse.getName();

        assertEquals(name, actualName);
    }

    @Test
    void getSpeed_ReturnsCorrectSpeed() {
        String name = "TestName";
        double speed = 3;
        double distance = 10;
        Horse horse = new Horse(name, speed, distance);

        double actualSpeed = horse.getSpeed();

        assertEquals(speed, actualSpeed);
    }

    @Test
    void getDistance_ReturnsCorrectDistance() {
        String name = "TestName";
        double speed = 3;
        double distance = 10;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance);
    }

    @Test
    void getDistance_ReturnsCorrectConstructorWhenDistanceIsZero() {
        String name = "TestName";
        double speed = 3;
        double distance = 0;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance);
    }


    @Test
    void moveCallsGetRandomDoubleMethodWithCorrectParams() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("testName", 1, 2);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 0.7, 0.9, 100, -200, 0})
    public void move_UsedFormulaIsCorrect(double fakeRandomValue) {
        //Arrange
        double min = 0.2;
        double max = 0.9;
        String name = "testName";
        double speed = 2.5;
        int distance = 200;
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = distance + speed * fakeRandomValue;

        //Act
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeRandomValue);
            horse.move();
        }

        //Assert
        assertEquals(expectedDistance, horse.getDistance());

    }
}
