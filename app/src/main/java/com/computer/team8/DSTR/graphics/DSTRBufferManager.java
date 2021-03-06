package com.computer.team8.DSTR.graphics;

import com.computer.team8.DSTR.graphics.element.Demon;
import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.element.Square;
import com.computer.team8.DSTR.graphics.service.ColourManager;
import java.util.ArrayList;

public class DSTRBufferManager {
    private static ArrayList<Element> elements;
    private static Demon demon;

    public DSTRBufferManager() {
        elements = new ArrayList<>();
    }

    public void add(Element elem) {
        if (elem.getClass() == Demon.class) {
            demon = (Demon)elem;
        }

        elements.add(elem);
    }
    public void remove(Element elem) { elements.remove(elem); }

    public static Element get(int index) {
        return elements.get(index);
    }
    public static Element get(Class<?> c) {
        for (Element te : elements) {
            if (te.getClass().equals(c)) {
                return te;
            }
        }

        return null;
    }

    public static Demon getDemon() {
        return demon;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void createLevel() {
        Square sq = new Square(ColourManager.getColour("dirt"));
        sq.setScale(100, 1, 100);
        sq.setBottom(0, -2, 0);
        this.add(sq);
        sq = new Square(ColourManager.getColour("chill grass"));
        sq.setScale(100, 1, 100);
        sq.setBottom(0, -1, 0);
        this.add(sq);

        sq = new Square(ColourManager.getColour("dirt"));
        sq.setScale(10, 1, 10);
        sq.setBottom(15, 0, 12);
        this.add(sq);
        sq = new Square(ColourManager.getColour("chill grass"));
        sq.setScale(10, 1, 10);
        sq.setBottom(15, 1, 12);
        this.add(sq);

        sq = new Square(ColourManager.getColour("grey 5"));
        sq.setScale(1, 10, 1);
        sq.setBottom(-2, 0, -7);
        this.add(sq);

        sq = new Square(ColourManager.getColour("grey 5"));
        sq.setScale(1, 6, 1);
        sq.setBottom(0, 0, 9);
        this.add(sq);
    }
}
