package widge.model.dao;

import widge.model.FabricationFormula;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGame;

import java.util.List;

public interface FabricationFormulaDAO {

    public List<FabricationFormula> getFabricationFormulas();
    public FabricationFormula getFabricationFormulaById(Integer id);

}
