package com.stevenstier.prm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stevenstier.prm.model.Variable;
import com.stevenstier.prm.model.VariableHistoryN;
import com.stevenstier.prm.model.Dao.VariableDao;
import com.stevenstier.prm.model.Dao.VariableHistoryNDao;

@RestController
@RequestMapping("/api")
public class VariableRestController {

  private VariableDao variableDao;
  private VariableHistoryNDao variableHistoryNDao;

  @Autowired
  public VariableRestController(VariableDao variableDao, VariableHistoryNDao variableHistoryNDao) {
    this.variableDao = variableDao;
    this.variableHistoryNDao = variableHistoryNDao;
  }

  @GetMapping("/variables")
  public List<Variable> getAllVariables() {
    return variableDao.getAllVariables();
  }

  @PostMapping("/variables")
  @ResponseStatus(HttpStatus.CREATED)
  public Variable createItem(@RequestBody Variable newVariable) {
    return variableDao.createVariable(newVariable);
  }

  @GetMapping("/variables/{id}")
  public Variable getVariable(@PathVariable int id) {
    Variable result = variableDao.getVariablebyID(id);
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return result;
  }

  @PutMapping("/variables/{id}")
  public Boolean UpdateVariable(@PathVariable Long id, @RequestBody Variable updatedVariable) {
    updatedVariable.setVarId(id);
    Variable existingVariable = variableDao.getVariablebyID(id);
    if (existingVariable == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return variableDao.updateVariable(updatedVariable);
  }

  @DeleteMapping("/variables/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteVariable(@PathVariable int id) {
    Variable existingVariable = variableDao.getVariablebyID(id);
    if (existingVariable == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    } else
      variableDao.deleteVariable(id);
  }

  
}
