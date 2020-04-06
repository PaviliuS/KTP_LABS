package com.company;

import java.util.Objects;

/**
 * Класс представляет местополежение на 2D карте.
 * Координаты с целочисленными значениями
 **/
public class Location {

    /** X координаты местоположения. */
    public int xCoord;

    /** Y координаты местоположения. */
    public int yCoord;

    /** Создание нового местоположения с указанными целочисленными координатами. */
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создание местоположения с координатами (0,0). */
    public Location()
    {
        this(0, 0);
    }

    /** Сравнивает на эквивалентность координаты двух точек. */
    @Override public boolean equals(Object coord)
    {
        if (this == coord) {
            return true;
        }
        Location loc=(Location) coord;
        if (this.xCoord == loc.xCoord && this.yCoord == loc.yCoord)
        {
            return true;
        }
        return false;
    }

    /** Возвращает hashCode указанного местоположения точки. */
    @Override public int hashCode()
    {
        return Objects.hash(xCoord,yCoord);
    }
}
