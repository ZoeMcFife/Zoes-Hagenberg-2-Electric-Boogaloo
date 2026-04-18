package gay.fox.item_shop;

import gay.fox.stuff.BinarySearchTree;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Shop extends BinarySearchTree<ShopItem>
{
    public void restock(ShopItem item)
    {
        if (contains(item))
        {
            remove(item);

            item.increaseStock();

            insert(item);

            return;
        }

        insert(item);
    }

    public void bought(ShopItem item)
    {
        if (contains(item))
        {
            remove(item);

            item.decreaseStock();

            if (item.getStock() != 0)
            {
                insert(item);
            }
            else
            {
                IO.println(item.getName() + " is out of stock!");
            }
        }
    }

    public ShopItem processSale(Customer customer)
    {
        ShopItem item = getBestShopItem(customer);

        if (item == null) return null;

        bought(item);

        IO.println(customer.getName() + " bought " + item.getName());
        IO.println("Current Stock: " + this);

        return item;
    }

    private ShopItem getBestShopItem(Customer customer)
    {
        ShopItem freeShopItem = new ShopItem("Free", "Free", 0, 0, 0, 0, 0, 0);
        ShopItem mostExpensiveShopItem = new ShopItem("Most Expensive", "Most Expensive", Integer.MAX_VALUE, Integer.MAX_VALUE, customer.getBudget(), Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        List<ShopItem> affordableItems = rangeSearch(freeShopItem, mostExpensiveShopItem);

        if (affordableItems.isEmpty()) return null;

        if (affordableItems.size() == 1) return affordableItems.getFirst();

        Optional<ShopItem> bestItem;

        if (customer.prefersOffense())
        {
            bestItem = affordableItems.stream().max(Comparator.comparingInt(ShopItem::getOffense));
        }
        else
        {
            bestItem = affordableItems.stream().max(Comparator.comparingInt(ShopItem::getDefense));
        }

        return bestItem.orElse(null);
    }
}
