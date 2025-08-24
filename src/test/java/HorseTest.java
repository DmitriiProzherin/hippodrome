import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    private static final Random rand = new Random();

    static Stream<String> emptyLikeStrings() {
        return Stream.of("", "\t", " ", "\r", "\n", "\f");
    }

    @Test
    public void horseConstructorFirstParamIsNullAndMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        String expectedMessage = "Name cannot be null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @MethodSource("emptyLikeStrings")
    public void horseConstructorFirstParamIsEmptyAndMessage(String emptyString) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse(emptyString, 1, 2));

        String expectedMessage = "Name cannot be blank.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void horseConstructorSecondParamNegativeAndMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Test", -1, 2));

        String expectedMessage = "Speed cannot be negative.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void horseConstructorThirdParamNegativeAndMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Test", 1, -2));

        String expectedMessage = "Distance cannot be negative.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getNameProvidesActualName() {
        StringBuilder builder = new StringBuilder();
        int n = 100, start = 'a';
        for (int i = 0; i < n; i++) {
            builder.append((char) (start + rand.nextInt(26)));
        }
        String name = builder.toString();
        Horse horse = new Horse(name, 1, 2);

        assertEquals(name, horse.getName());
    }

    @Test
    public void getSpeedProvidesActualSpeed() {
        int speed = 10 + rand.nextInt(100);
        Horse horse = new Horse("Test", speed, 1);

        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistanceProvidesActualDistance() {
        int distance = 10 + rand.nextInt(100);
        Horse horse = new Horse("Test", 1, distance);

        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void getSpeedProvidesZeroWithTwoArgsConstructor() {
        Horse horse = new Horse("Test", 1);

        assertEquals(0, horse.getSpeed());
    }

    @Test
    public void moveCallsGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Test", 1, 1);
            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 0.8, 0.89})
    public void moveCalculationIsCorrect(double randomValue) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            double speed = 7, distance = 10, EPS = 1e-9;
            Horse horse = new Horse("Test", speed, distance);
            double correctDistance = distance + speed * randomValue;
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            horse.move();

            assertEquals(correctDistance, horse.getDistance(), EPS);
        }
    }


}
