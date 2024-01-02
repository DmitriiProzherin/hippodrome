import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class HorseTest {
    static Throwable nullException = new Throwable();
    static Throwable blankException = new Throwable();
    static Throwable speedException = new Throwable();
    static Throwable distanceException = new Throwable();

    @Test
    public void TetConstructorForNull(){
        nullException = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void TestConstructorForNullMessage(){
        assertEquals("Name cannot be null.", nullException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings =
            {"", " ", "\n", "\t"})
    public void TestConstructorForEmptyInputs(String string){
        blankException = assertThrows(IllegalArgumentException.class, () -> new Horse(string, 1,1));
    }

    @Test
    public void TestConstructorForEmptyMessage(){
        assertEquals("Name cannot be blank.", blankException.getMessage());
    }

    @Test
    public void TestConstructorForNegativeSpeed(){
        speedException = assertThrows(IllegalArgumentException.class, () -> new Horse("One", -1, 1));
    }

    @Test
    public void TestConstructorMessageForNegativeSpeed(){
        assertEquals("Speed cannot be negative.", speedException.getMessage());
    }

    @Test
    public void TestConstructorForNegativeDistance(){
        distanceException = assertThrows(IllegalArgumentException.class, () -> new Horse("One", 1, -1));
    }

    @Test
    public void TestConstructorMessageForNegativeDistance(){
        assertEquals("Distance cannot be negative.", distanceException.getMessage());
    }

    @Test
    public void getNameTest(){
        String name = "One";
        Horse horse = new Horse(name, 1, 1);
        assertSame(name, horse.getName());
    }

    @Test
    public void getSpeedTest(){
        double speed = 181.89912;
        Horse horse = new Horse("one", speed, 1);
        assertEquals(181.89912, horse.getSpeed());
    }


    @Test
    public void getDistanceTest(){
        double distance = 792.14325;
        Horse horse = new Horse("one", 1, distance);
        assertEquals(792.14325, horse.getDistance());
    }

    @Test
    public void getDistanceZeroTest(){
        Horse horse = new Horse("one", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveRandomInvokedTest(){
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            Horse horse = new Horse("One", 1, 1);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }


    @ParameterizedTest
    @CsvSource({
            "4, 5, 0.9",
            "1, 3, 0.1",
            "0, 0, 0.1",
            "0.1, 0.1, 01",
            "20.234, 12.3214, 0.345",
            "100000, 100000, 0.99",
            "100000, 100000, 0.01"
    })
    public void moveRandomValuesTest(double speed, double distance, double random){
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)){
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("One", speed, distance);
            double result = horse.getDistance() + horse.getSpeed() * random;
            horse.move();
            assertEquals(result, horse.getDistance());
        }
    }
}