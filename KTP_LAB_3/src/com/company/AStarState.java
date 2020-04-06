package com.company;

import java.util.HashMap;

/**
 * Класс хранит базовое состояние необходимое для алгоритма А*, чтобы вычислить
 * путь по карте. Это состояние включает коллекции "открытых точек" и другие коллеции
 * "закрытых точек". Также этот класс обеспечивает основные операци А* алгоритма,
 * для обработки алгоритма.
 */
public class AStarState {

    /** Это ссылка на карту, по которой работает алгоритм А*. */
    private Map2D map;

    /** Создание карты всех открытых точек и их местоположений. */
    public HashMap<Location, Waypoint> OpenedVertex
            = new HashMap<Location, Waypoint> ();

    /** Создание карты всех закрытых точек и их местоположений. */
    public HashMap<Location, Waypoint> ClosedVertex
            = new HashMap<Location, Waypoint> ();

    /** Создание нового объекта для алгоритма поиска А*. */
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Возвращает карту, по которой работает алгоритм А*. */
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод просматривает все открытые точки и возвращает точку с
     * минимальным общим значением.
     * Если нет открытых точек, метод возвращает null.
     */
    public Waypoint getMinOpenWaypoint()
    {
        // TODO:  Implement.
        if (OpenedVertex.size() == 0)
        {
            return null;
        }
        Waypoint first = (Waypoint) OpenedVertex.values().toArray()[0];
        float min = first.getTotalCost();
        for (Waypoint wp : OpenedVertex.values()){
            if (min >= wp.getTotalCost()){
                min = wp.getTotalCost();
                first = wp;
            }
        }
        return first;
    }

    /**
     * Этот метод добавляет точку (обновляет точку) в коллекцию открытых точек.
     * Если в местополодении новой точки нет уде открытой новой точки,
     * то она добавляется в коллекцию. Однако, если в коллекции есть точка,
     * новая точка заменяет старую точку, если значение новой точки меньше
     * текущей.
     */
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        // TODO:  Implement.
        if (!OpenedVertex.containsKey(newWP.getLocation())){
            OpenedVertex.put(newWP.getLocation(), newWP);
            return true;
        }
        else
        {
            if(OpenedVertex.get(newWP.getLocation()).getRemainingCost() > newWP.getRemainingCost())
            {
                OpenedVertex.put(newWP.getLocation(),newWP);
                return true;
            }
        }
        return false;
    }


    /** Возвраащет количество открытых точек. */
    public int numOpenWaypoints()
    {
        // TODO:  Implement.
        return OpenedVertex.size();
    }


    /**
     * Этот метод перемещает точку в указанном месте из открытого списка в закрытый.
     */
    public void closeWaypoint(Location loc)
    {
        // TODO:  Implement.
        Waypoint wp = OpenedVertex.get(loc);
        ClosedVertex.put(loc,wp);
        OpenedVertex.remove(loc);
    }

    /**
     * Возвращает true, если коллекция зыкрытых точек содержит точку
     * для указанного местоположения.
     */
    public boolean isLocationClosed(Location loc)
    {
        // TODO:  Implement.
        if (ClosedVertex.get(loc) != null)
        {
            return true;
        }
        return false;
    }
}
