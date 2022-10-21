package nl.inholland.konradfigura.finalassignment.model;

import java.io.Serializable;
import java.util.List;

public interface Loadable<Serializable> {
    void setAll(List<Serializable> list);
    List<Serializable> getAll();
}
