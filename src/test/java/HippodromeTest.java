import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    public static final Random rand = new Random();

    public static List<Horse> horses = new ArrayList<>();

    @BeforeAll
    public static void initHorsesList() {
        for (int i = 0; i < 30; i++) {
            String name = provideName(7 + rand.nextInt(10));
            double speed = 10 + rand.nextDouble() * 100;
            double distance = 5 + rand.nextDouble() * 10;
            horses.add(new Horse(name, speed, distance));
        }
    }

    @Test
    public void hippodromeConstructorNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        String expectedMessage = "Horses cannot be null.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void hippodromeConstructorEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));

        String expectedMessage = "Horses cannot be empty.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void hippodromeGetHorsesProvidesCorrectHorsesList() {
        Hippodrome hippodrome = new Hippodrome(horses);

        assertIterableEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void moveActuallyWorkWithEveryHorse(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (var h : hippodrome.getHorses()) {
            verify(h).move();
        }
    }

    @Test
    public void getWinnerActuallyProvidesWinnerWithHighestDistance(){
        Hippodrome hippodrome = new Hippodrome(horses);

        Horse actualWinner = horses.stream().max(Comparator.comparingDouble(Horse::getDistance)).orElseThrow();
        Horse providedWinner = hippodrome.getWinner();

        assertEquals(providedWinner, actualWinner);
    }


    private static String provideName(int length) {
        StringBuilder builder = new StringBuilder();
        int start = 'a';
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (start + rand.nextInt(26));
            builder.append(randomChar);
        }
        return builder.toString();
    }

}
