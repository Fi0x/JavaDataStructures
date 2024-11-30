package io.fi0x.javadatastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This list supports averaging methods for data. The way, data is returned can be modified.
 */
public class AvgList
{
	//TODO: Generalize list-type to allow more number-types
	private List<Double> originalList;
	private List<Double> averagedList = new ArrayList<>();
	private Double highestValue = null;
	private Double lowestValue = null;

	private boolean preCalculate = false;
	private boolean dirty = false;

	private Double topCutoff = null;
	private Double lowCutoff = null;
	private Double topCutoffPercent = null;
	private Double lowCutoffPercent = null;

	private int additionalAveragingValues = 0;

	/**
	 * Default constructor of this class.
	 */
	public AvgList()
	{
		originalList = new ArrayList<>();
	}

	/**
	 * This constructor will copy the content of the provided list to a new one as its initial values.
	 *
	 * @param initialList The original list with values to initialize this list.
	 */
	public AvgList(List<Double> initialList)
	{
		originalList = List.copyOf(initialList);
		dirty = true;
		averagedList = List.copyOf(initialList);
	}

	/**
	 * Sets the provided data s new base-data.
	 *
	 * @param data A list of values.
	 */
	public void setData(List<Double> data)
	{
		originalList = data;
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * Adds a value to the already existing list.
	 *
	 * @param value The new value.
	 */
	public void add(double value)
	{
		originalList.add(value);
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * This will remove a value at the indexed position in the list and return it.
	 *
	 * @param index The index of the value to be removed.
	 */
	public Double remove(int index)
	{
		Double value = originalList.remove(index);

		dirty = true;
		if(preCalculate)
			reCalculate();

		return value;
	}

	/**
	 * Weather or not the results should be calculated before retrieving them.
	 * When setting this to true, every modification of the data will result in a re-calculation of the returned list.
	 * The returned list will also be re-calculated, when the settings for this list change.
	 * Setting this to false, will cause the returned list to be calculated, when it's requested.
	 *
	 * @param preCalculate Weather to calculate on modification or on data reading. (Default is false)
	 */
	public void setPreCalculate(boolean preCalculate)
	{
		this.preCalculate = preCalculate;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * All numbers that are equal or higher than this value, will be removed from the returned list.
	 * Calling this method, while preCalculate is true, will result in an immediate re-calculation of the data.
	 * Setting this to null, will ignore this filter.
	 *
	 * @param topCutoff (Default is null)
	 */
	public void setTopCutoff(Double topCutoff)
	{
		this.topCutoff = topCutoff;
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * All numbers that are equal or lower than this value, will be removed from the returned list.
	 * Calling this method, while preCalculate is true, will result in an immediate re-calculation of the data.
	 * Setting this to null, will ignore this filter.
	 *
	 * @param lowCutoff (Default is null)
	 */
	public void setLowCutoff(Double lowCutoff)
	{
		this.lowCutoff = lowCutoff;
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * All numbers that are equal or higher than the top X percent of the highest value in the list,
	 * will be removed from the returned list.
	 * Calling this method, while preCalculate is true, will result in an immediate re-calculation of the data.
	 * Setting this to null, will ignore this filter.
	 *
	 * @param topCutoffPercent (Default is null)
	 */
	public void setTopCutoffPercent(Double topCutoffPercent)
	{
		if(topCutoffPercent != null && (topCutoffPercent < 0 || topCutoffPercent > 100))
			throw new IllegalArgumentException("topCutoffPercent must be at least 0 and at most 100");

		this.topCutoffPercent = topCutoffPercent;
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * All numbers that are equal or lower than the lowest X percent of the highes value in the list,
	 * will be removed from the returned list.
	 * Calling this method, while preCalculate is true, will result in an immediate re-calculation of the data.
	 * Setting this to null, will ignore this filter.
	 *
	 * @param lowCutoffPercent (Default is null)
	 */
	public void setLowCutoffPercent(Double lowCutoffPercent)
	{
		if(lowCutoffPercent != null && (lowCutoffPercent < 0 || lowCutoffPercent > 100))
			throw new IllegalArgumentException("lowCutoffPercent must be at least 0 and at most 100");

		this.lowCutoffPercent = lowCutoffPercent;
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * This number defines, how many neighbouring values a value should be averaged with. When this is 1, it will be
	 * compared with its next and previous value and return the average of the 3 values instead of its original value.
	 * Calling this method, while preCalculate is true, will result in an immediate re-calculation of the data.
	 * Setting this to 0 will result in no averaging between neighbouring values.
	 *
	 * @param additionalAveragingValues (Default is 0)
	 */
	public void setAdditionalAveragingValues(int additionalAveragingValues)
	{
		this.additionalAveragingValues = additionalAveragingValues;
		dirty = true;
		if(preCalculate)
			reCalculate();
	}

	/**
	 * This will return the unmodified list that was provided to this class. (Added values are also included)
	 *
	 * @return The original list.
	 */
	public List<Double> getOriginalList()
	{
		return originalList;
	}

	/**
	 * This will return the averaged version of the original list.
	 * If all settings are set to their default values, this list and the original one are identical.
	 *
	 * @return A new list with averaged values.
	 */
	public List<Double> get()
	{
		if(!preCalculate)
			reCalculate();

		return averagedList;
	}

	/**
	 * Forces a re-calculation of the returned list. This will only re-calculate the results,
	 * if a change was made to the settings or values of this list since the last re-calculation.
	 */
	public void reCalculate()
	{
		if(dirty)
		{
			List<Double> adjustedList = removeCutoffs();
			if(additionalAveragingValues == 0)
				averagedList = List.copyOf(adjustedList);
			else
				averageList(adjustedList);

			dirty = false;
		}
	}

	private List<Double> removeCutoffs()
	{
		highestValue = getHighestValue();
		lowestValue = getLowestValue();

		List<Double> adjustedList = new ArrayList<>();

		Double highCutoff = topCutoff;
		Double highPercent = absoluteFromPercentage(100 - topCutoffPercent);
		if(highCutoff != null || highPercent != null)
		{
			if(highCutoff == null)
				highCutoff = highPercent;
			else if(highPercent != null)
				highCutoff = Math.min(highCutoff, highPercent);
		}
		else
			highCutoff = highestValue;

		Double minCutoff = lowCutoff;
		Double minPercent = absoluteFromPercentage(lowCutoffPercent);
		if(minCutoff != null || minPercent != null)
		{
			if(minCutoff == null)
				minCutoff = minPercent;
			else if(minPercent != null)
				minCutoff = Math.min(minCutoff, minPercent);
		}
		else
			minCutoff = lowestValue;

		for(Double value : originalList)
		{
			if(value < highCutoff && value > minCutoff)
				adjustedList.add(value);
		}

		return adjustedList;
	}

	private Double absoluteFromPercentage(Double percent)
	{
		if(percent == null || highestValue == null || lowestValue == null)
			return null;

		//TODO: Add an overflow check
		double range = highestValue - lowestValue;
		double percentAbsolute = (range / 100) * percent;

		return percentAbsolute + lowestValue;
	}

	private Double getHighestValue()
	{
		if(originalList.isEmpty())
			return null;

		return Collections.max(originalList);
	}

	private Double getLowestValue()
	{
		if(originalList.isEmpty())
			return null;

		return Collections.min(originalList);
	}

	private void averageList(List<Double> inputList)
	{
		averagedList = List.copyOf(inputList);
		if(averagedList.isEmpty())
			return;

		for(int idx = 0; idx < inputList.size(); idx++)
		{
			double amount = 0;
			double sum = 0;
			for(int aIdx = Math.max(0,
									idx - additionalAveragingValues); aIdx < inputList.size() && aIdx <= idx + additionalAveragingValues; aIdx++)
			{
				amount++;
				sum += inputList.get(aIdx);
			}

			averagedList.set(idx, sum / amount);
		}
	}
}
