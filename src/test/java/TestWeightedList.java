import io.fi0x.javadatastructures.WeightedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestWeightedList
{
    @Test
    void testConstructor()
    {
        Random r = new Random();

        Assertions.assertDoesNotThrow(() -> new WeightedList<>(null));
        Assertions.assertDoesNotThrow(() -> new WeightedList<>(r));
    }

    @Test
    void testElements()
    {
        WeightedList<Integer> list = new WeightedList<>(null);

        Assertions.assertEquals(0, list.size());
        Assertions.assertDoesNotThrow(() -> list.add(1, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.add(-1, -2));
        Assertions.assertEquals(1, list.size());
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.get(-1));
        Assertions.assertEquals(-1, list.get(0));
        Assertions.assertEquals(-1, list.get(0.9));
        Assertions.assertNull(list.get(1));
        Assertions.assertDoesNotThrow(() -> list.add(10, -3));
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void testRandom()
    {
        WeightedList<Integer> list = new WeightedList<>(null);
        list.add(10, -1);

        Assertions.assertEquals(-1, list.random());
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(-1, list.randomRemove());
        Assertions.assertEquals(0, list.size());
        Assertions.assertNull(list.randomRemove());
    }

    @Test
    void testToList()
    {
        WeightedList<Integer> list = new WeightedList<>(null);
        list.add(2, 1);
        list.add(5, 2);
        list.add(5, 3);
        List<Integer> normalList = list.toList();

        Assertions.assertEquals(3, normalList.size());
        Assertions.assertDoesNotThrow(() -> Collections.sort(normalList));
        Assertions.assertEquals(1, normalList.get(0));
        Assertions.assertEquals(2, normalList.get(1));
        Assertions.assertEquals(3, normalList.get(2));
    }
}
