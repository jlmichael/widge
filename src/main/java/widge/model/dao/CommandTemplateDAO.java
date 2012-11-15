package widge.model.dao;

import widge.model.CommandTemplate;

import java.util.List;

public interface CommandTemplateDAO {

    public List<CommandTemplate> getCommandTemplates();
    public CommandTemplate getCommandTemplateById(Integer id);
}
