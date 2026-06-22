package meow.Dto;

import java.util.Objects;

public class WeightDto
{
    private String imperial;
    private String metric;

    public void setImperial(String imperial)
    {
        this.imperial = imperial;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public String getImperial()
    {
        return imperial;
    }

    public String getMetric()
    {
        return metric;
    }

    public String getMetricUnit()
    {
        return "kg";
    }

    public  String getImperialUnit()
    {
        return "lbs";
    }

    @Override
    public String toString()
    {
        return getImperial() + " " + getImperialUnit() + " " + getMetric() + " " + getMetricUnit();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WeightDto weightDto = (WeightDto) o;
        return Objects.equals(getImperial(), weightDto.getImperial()) && Objects.equals(getMetric(), weightDto.getMetric());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImperial(), getMetric());
    }
}
