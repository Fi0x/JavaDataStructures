import io.fi0x.javadatastructures.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestTuple
{
    @Test
    void testConstructor()
    {
        Assertions.assertDoesNotThrow(() -> new Tuple<Integer, Double>(null, null));
        Assertions.assertDoesNotThrow(() -> new Tuple<>(1, new Random()));
    }

    @Test
    void testObject1()
    {
        Tuple<Integer, Random> tuple = new Tuple<>(0, null);

        Assertions.assertEquals(0, tuple.getObject1());
        Assertions.assertDoesNotThrow(() -> tuple.setObject1(5));
        Assertions.assertEquals(5, tuple.getObject1());
    }

    @Test
    void testObject2()
    {
        Random r1 = new Random();
        Random r2 = new Random();
        Tuple<Integer, Random> tuple = new Tuple<>(0, r1);

        Assertions.assertEquals(r1, tuple.getObject2());
        Assertions.assertNotEquals(r2, tuple.getObject2());
        Assertions.assertDoesNotThrow(() -> tuple.setObject2(r2));
        Assertions.assertEquals(r2, tuple.getObject2());
        Assertions.assertNotEquals(r1, tuple.getObject2());
    }
}
