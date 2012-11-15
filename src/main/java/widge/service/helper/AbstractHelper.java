package widge.service.helper;

import widge.model.dao.handler.DAOHandler;
import widge.service.WidgeRequestStore;

public abstract class AbstractHelper {
    protected DAOHandler daoHandler;
    protected WidgeRequestStore requestStore;

    public AbstractHelper(DAOHandler daoHandler, WidgeRequestStore requestStore) {
        this.daoHandler = daoHandler;
        this.requestStore = requestStore;
    }
}
