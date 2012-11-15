package widge.model.dao;

import widge.model.Good;

import java.util.List;

public interface GoodDAO {

    public List<Good> getGoods();
    public Good getGoodById(Integer id);
    
}
