import io.fi0x.javadatastructures.TupleList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTupleList
{
    @Test
    void testConstructor()
    {
        Assertions.assertDoesNotThrow(() -> new TupleList<>());
    }
}