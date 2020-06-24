package repository;

import domain.Clasament;

public interface IClasamentRepository {
    void add(Clasament c);
    Clasament findOne(Integer gameID);
}
