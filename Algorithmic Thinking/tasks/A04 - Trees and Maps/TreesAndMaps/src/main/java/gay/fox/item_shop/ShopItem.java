package gay.fox.item_shop;

import gay.fox.stuff.Item;

public class ShopItem extends Item implements Comparable<ShopItem>
{
    private final int price;
    private final int offense;
    private final int defense;
    private int stock;

    public ShopItem(String name, String type, int weight, int value, int price, int offense, int defense, int stock)
    {
        super(name, type, weight, value);
        this.price = price;
        this.offense = offense;
        this.defense = defense;
        setStock(stock);
    }

    public void increaseStock()
    {
        setStock(getStock() + 1);
    }

    public void decreaseStock()
    {
        setStock(getStock() - 1);
    }

    private void setStock(int stock)
    {
        if (stock < 0)
        {
            this.stock = 0;
            return;
        }

        this.stock = stock;
    }

    public int getStock()
    {
        return stock;
    }

    public int getPrice()
    {
        return price;
    }

    public int getOffense()
    {
        return offense;
    }

    public int getDefense()
    {
        return defense;
    }

    @Override
    public int compareTo(ShopItem o)
    {
        // Price
        int cmp = Integer.compare(this.price, o.price);

        if (cmp != 0) return cmp;

        // if same price, compare name
        cmp = this.getName().compareTo(o.getName());
        if (cmp != 0) return cmp;

        // same name, compare type
        return this.getType().compareTo(o.getType());
    }
}
