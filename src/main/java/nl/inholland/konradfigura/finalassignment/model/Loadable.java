package nl.inholland.konradfigura.finalassignment.model;

import java.util.List;

public interface Loadable<Serializable> {
    void load(List<Serializable> list);
    void save();
}
