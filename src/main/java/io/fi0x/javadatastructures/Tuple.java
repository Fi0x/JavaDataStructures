package io.fi0x.javadatastructures;

/**
 * This class is used as a data-structure to support tuples of {@link Object objects}.
 *
 * @param <O1> The Type of the first {@link Object} that is stored in this {@link Tuple}.
 * @param <O2> The Type of the second {@link Object} that is stored in this {@link Tuple}.
 */
public class Tuple<O1, O2>
{
    private O1 object1;
    private O2 object2;

    /**
     * The main constructor of this data-structure.
     *
     * @param value1 The {@link Object} that should be stored as the first part of this {@link Tuple}.
     * @param value2 The {@link Object} that should be stored as the second part of this {@link Tuple}.
     */
    public Tuple(O1 value1, O2 value2)
    {
        object1 = value1;
        object2 = value2;
    }

    /**
     * This method changes the first {@link Object} of this {@link Tuple}.
     *
     * @param value The new {@link Object}.
     */
    public void setObject1(O1 value)
    {
        object1 = value;
    }
    /**
     * This method returns the first {@link Object} of this {@link Tuple}.
     *
     * @return The first {@link Object} of this {@link Tuple}.
     */
    public O1 getObject1()
    {
        return object1;
    }

    /**
     * This method changes the second {@link Object} of this {@link Tuple}.
     *
     * @param value The new {@link Object}.
     */
    public void setObject2(O2 value)
    {
        object2 = value;
    }
    /**
     * This method returns the second {@link Object} of this {@link Tuple}.
     *
     * @return The second {@link Object} of this {@link Tuple}.
     */
    public O2 getObject2()
    {
        return object2;
    }

}
