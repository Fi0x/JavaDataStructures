package io.fi0x.javadatastructures;

import java.util.ArrayList;

/**
 * This class is an extension of a normal {@link ArrayList}.
 * It is used to simplify the use of {@link Tuple Tuples} in an {@link ArrayList}.
 *
 * @param <O1> The type of {@link Object} that should be used for the first {@link Tuple} type.
 * @param <O2> The type of {@link Object} that should be used for the second {@link Tuple} type.
 */
public class TupleList<O1, O2> extends ArrayList<Tuple<O1, O2>>
{
    /**
     * The default constructor of this class.
     */
    public TupleList()
    {
        super();
    }
}
