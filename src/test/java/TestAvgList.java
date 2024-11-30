import io.fi0x.javadatastructures.AvgList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TestAvgList
{
	@Test
	void testConstructor()
	{
		Assertions.assertDoesNotThrow(() -> new AvgList());
		Assertions.assertDoesNotThrow(() -> new AvgList(null));
		Assertions.assertDoesNotThrow(() -> new AvgList(new ArrayList()));
	}
	//TODO: Complete tests
}
