package gay.fox.item_shop;

public class Customer
{
    private final String name;
    private final int budget;
    private final boolean prefersOffense;

    public Customer(String name, int budget, boolean prefersOffense)
    {
        this.name = name;
        this.budget = budget;
        this.prefersOffense = prefersOffense;
    }

    public String getName()
    {
        return this.name;
    }

    public int getBudget()
    {
        return this.budget;
    }

    public boolean prefersOffense()
    {
        return this.prefersOffense;
    }

    @Override
    public String toString()
    {
        return "Customer: " + this.name + " Budget: " + this.budget + "  Prefers Offense?: " + this.prefersOffense;
    }
}
