import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HorseTest {

    static Stream<String> emptyLikeStrings(){
        return Stream.of("", "\t", " ", "\r", "\n", "\f");
    };

//    @Test
//    public void horseConstructorFirstParamIsNull() {
//        assertThrows(IllegalArgumentException.class, () -> {new Horse(null, 1, 2);});
//    }

    @Test
    public void horseConstructorFirstParamIsNullAndMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Horse(null, 1, 2);});
        String expectedMessage = "Name cannot be null.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

//    @ParameterizedTest
//    @MethodSource("emptyLikeStrings")
//    public void horseConstructorFirstParamIsEmpty(String emptyString){
//        assertThrows(IllegalArgumentException.class, () -> {new Horse(emptyString, 1, 2);});
//    }

    @ParameterizedTest
    @MethodSource("emptyLikeStrings")
    public void horseConstructorFirstParamIsEmptyAndMessage(String emptyString){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Horse(emptyString, 1, 2);});

        String expectedMessage = "Name cannot be blank.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void horseConstructorSecondParamNegativeAndMessage(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Horse("Test", -1, 2);});

        String expectedMessage = "Speed cannot be negative.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void horseConstructorThirdParamNegativeAndMessage(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Horse("Test", 1, -2);});

        String expectedMessage = "Distance cannot be negative.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
