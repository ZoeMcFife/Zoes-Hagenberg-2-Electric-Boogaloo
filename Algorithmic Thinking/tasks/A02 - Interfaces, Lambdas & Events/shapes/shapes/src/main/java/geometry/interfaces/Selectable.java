package geometry.interfaces;

import geometry.Rectangle;

public interface Selectable
{
    Rectangle getBoundingBox();
    void setSelected(boolean selected);
    boolean isSelected();
}
