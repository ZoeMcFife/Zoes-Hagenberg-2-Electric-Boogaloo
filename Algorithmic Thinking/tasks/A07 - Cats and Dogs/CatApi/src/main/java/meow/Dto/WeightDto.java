package meow.Dto;

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
}
