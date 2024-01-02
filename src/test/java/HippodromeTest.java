import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    public void constructorNullTest(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void constructorNullMessageTest(){
        try {
            new Hippodrome(null);
        } catch (Exception e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }
    @Test
    public void constructorEmptyListTest(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.EMPTY_LIST));
    }

    @Test
    public void constructorEmptyListMessageTest(){
        try {
            new Hippodrome(Collections.EMPTY_LIST);
        } catch (Exception e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void getHorsesRightSequenceTest(){
        List<Horse> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list.add(new Horse(i+" Horse", 1 ,1));
        }

        Hippodrome hippodrome = new Hippodrome(list);

        assertIterableEquals(list, hippodrome.getHorses());
    }

    @Test
    public void moveInvokedForEveryHorseTest(){
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();

        hippodrome.getHorses().forEach(h -> Mockito.verify(h).move());
    }

    @Test
    public void getWinnerTest(){
        List<Horse> list = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            Horse horse = Mockito.mock(Horse.class);
            Mockito.doReturn((double)i*10).when(horse).getDistance();
        }
        Horse maxHorse = Mockito.mock(Horse.class);
        Mockito.doReturn((double)500).when(maxHorse).getDistance();
        list.add(maxHorse);

        Collections.shuffle(list);

        Hippodrome hippodrome = new Hippodrome(list);
        assertSame(maxHorse, hippodrome.getWinner());
    }


}