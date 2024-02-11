package io.fi0x.javadatastructures;

import java.util.*;

/**
 * This class represents a weighted list that can be used for easy weighted randomization.
 *
 * @param <Type> The type of {@link Object} that should be stored in this {@link WeightedList}.
 */
public class WeightedList<Type>
{
    private final NavigableMap<Double, Type> ITEMS = new TreeMap<>();
    private final Random RANDOM;
    private double total = 0;

    /**
     * The main constructor of this class requires a {@link Random} to be able to return random elements later.
     *
     * @param random The {@link Random} that should be used.
     *               If {@code null}, a new {@link Random} will be generated.
     */
    public WeightedList(Random random)
    {
        RANDOM = Objects.requireNonNullElseGet(random, Random::new);
    }

    /**
     * This method allows new elements to be added to the {@link WeightedList}.
     *
     * @param weight The weight which the element has in the {@link WeightedList}.
     * @param item   The item that should be added.
     */
    public void add(float weight, Type item)
    {
        if(weight <= 0)
            throw new IllegalArgumentException("Weight of a WeightedList item must be greater than 0");

        total += weight;
        ITEMS.put(total, item);
    }
    /**
     * This method returns an {@link Object} from the {@link WeightedList} with a specified weighted value.
     * The weightIndex does not represent a specific weight of an entry.
     * The element at the position of the parameter is dependent on the order in which elements got added
     * and what the weights of the previous elements are.
     *
     * @param weightIndex The number, at which the {@link WeightedList} should be searched.
     * @return The element that is stored at the weighted location.
     */
    public Type get(double weightIndex)
    {
        if(weightIndex < 0)
            throw new IllegalArgumentException("WeightIndex must not be lower than 0");

        Map.Entry<Double, Type> e = ITEMS.higherEntry(weightIndex);
        if(e == null)
            return null;
        return e.getValue();
    }
    /**
     * This method returns a random element from the {@link WeightedList}.
     * Elements with higher weights have a higher chance to get selected.
     *
     * @return A random element from the {@link WeightedList}.
     */
    public Type random()
    {
        double value = RANDOM.nextDouble() * total;
        return get(value);
    }
    /**
     * This method returns a random element from the {@link WeightedList} and removes it.
     * Elements with higher weights have a higher change to get selected.
     *
     * @return A random element from the {@link WeightedList}, that is also removed by this method.
     */
    public Type randomRemove()
    {
        if(ITEMS.size() == 0)
            return null;

        double value = RANDOM.nextDouble() * total;

        Map.Entry<Double, Type> entry = ITEMS.higherEntry(value);
        value = entry.getKey();
        Type element = entry.getValue();

        if(value == total && ITEMS.size() > 1)
            total = ITEMS.lowerKey(value);
        else if(ITEMS.size() == 1)
            total = 0;

        ITEMS.remove(value);

        return element;
    }

    /**
     * This method returns the amount of entries that are stored inside the {@link WeightedList}.
     *
     * @return The size of the {@link WeightedList}.
     */
    public int size()
    {
        return ITEMS.size();
    }
}
