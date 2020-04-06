package com.company;

import java.awt.geom.Rectangle2D;

/**
 * Этот класс является подклассом FractalGenerator. Исрользуется
 * для вычисления фрактала Burning Ship fractal.
 */
public class BurningShip extends FractalGenerator
{
    /** Константа для числа максимальных итераций. */
    public static final int MAX_ITERATIONS = 2000;

    /**
     * Этот метод позволяет генератору фракталов указать,
     * какая часть комплексной плоскости наиболее интересна для фрактала.
     * Передаётся объект прямоугольника и метод изменяет поля прямоугольника,
     * чтобы показать корректный начальный диапозон для фрактала.
     * Реализация устанавливает начальный диапозон x=-2, y=-2.5, ширина=высота=4.
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    /**
     * Этот метод реализует итерационную функцию для фрактала Mandelbrot.
     * Он принимает два параметра для действительной и мнимой части комплексной
     * плоскости и возвращает количество итераций для соответствующей координаты.
     */
    public int numIterations(double x, double y)
    {
        /** Начинает с итерации 0. **/
        int iteration = 0;
        /** Устанавливает zreal действительную и zimaginary мнимую часть. */
        double zreal = 0;
        double zimaginary = 0;

        /**
         * Вычисляет Zn = Zn-1^2 + c, где значения являются комплексными числами
         * предаставленные zreal и zimaginary, Z0=0, и c-конкретная точка фрактала,
         * которую мы отображаем. Повторяется пока Z^2 > 4 (абсолютное значение Z
         * больше чем 2) или достигнуто максимальное число итераций.
         */
        while (iteration < MAX_ITERATIONS &&
                zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * Math.abs(zreal)
                    * Math.abs(zimaginary) + y;

            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;

            iteration += 1;
        }

        /**
         * Если достигнуто значение максимальных итераций, возвращает -1,
         * чтобы указать, что точка не вышла за пределы границы.
         */
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }

        return iteration;
    }

    /** Возвращает название фрактала. */
    public String toString() {
        return "Burning Ship";
    }

}