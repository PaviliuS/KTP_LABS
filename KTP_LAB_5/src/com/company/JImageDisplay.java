package com.company;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

/**
 * Этот класс позволяет отображать фракталы.
 * Класс наследует javax.swing.JComponent.
 */
class JImageDisplay extends JComponent
{
    /**
     * Экземпляр Buffered Image.
     * Управляет изображением, содержимое которого можно написать.
     */
    private BufferedImage displayImage;

    /** Метод получает экранное изображение из другого класса. */
    public BufferedImage getImage() {
        return displayImage;
    }

    /**
     * Конструктор принимает целочисленную ширину и высоту и устанавливает
     * объект BufferedImage с новым изображением с шириной и высотой
     * изображения типа TYPE_INT_RGB.
     */
    public JImageDisplay(int width, int height) {
        displayImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        /**
         * Вызывает метод setPreferredSize() родительского класса
         * с заданной шириной и высотой.
         */
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);

    }
    /**
     * Реализация суперкласса paintComponent(g) реализована так, что границы
     * и объекты отображаются правильно. Затем изображение рисуется в компонент.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(),
                displayImage.getHeight(), null);
    }
    /** Устанавливает все пиксели изображения на чёрный. **/
    public void clearImage()
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }
    /** Устанавливает пиксель в определённый цвет. **/
    public void drawPixel(int x, int y, int rgbColor)
    {
        displayImage.setRGB(x, y, rgbColor);
    }
}