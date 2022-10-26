package nl.inholland.konradfigura.finalassignment.model;

import java.util.List;

public interface Loadable<T> {
    void load(List<T> list);
    void save();
}
