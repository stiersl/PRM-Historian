package com.stevenstier.prm.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stevenstier.prm.model.Variable;
import com.stevenstier.prm.model.VariableHistoryN;
import com.stevenstier.prm.model.Dao.VariableDao;
import com.stevenstier.prm.model.Dao.VariableHistoryNDao;

@RestController
@RequestMapping("/api")
public class VariableHistoryRestController {

  private VariableDao variableDao;
  private VariableHistoryNDao variableHistoryNDao;

  private static class HistorianValue {
    
    public long varId;
    public String varValue;
    public String timeStamp;
    public int quality;
   }
  
  
  @Autowired
  public VariableHistoryRestController(VariableDao variableDao, VariableHistoryNDao variableHistoryNDao) {
    this.variableDao = variableDao;
    this.variableHistoryNDao = variableHistoryNDao;
  } 

  
  @PostMapping("/variableHistory")
  @ResponseStatus(HttpStatus.CREATED)
  public Boolean createVariableHistory(@RequestBody HistorianValue newHistorianValue) {
    
    Boolean result = false;
    long varIdToInsert = newHistorianValue.varId;
    Instant SampleTimeToInsert = null;
    
    // get the variable
    Variable existingVariable = variableDao.getVariablebyID(varIdToInsert);
    // check to see if one was found
    if ((existingVariable!= null)) {
      // check to see if a variable value was actually provided.
      if (newHistorianValue.varValue != null) {
        // convert the recieved timestamp to a localdatetime 
        try {
          if (newHistorianValue.timeStamp != null) {
          SampleTimeToInsert = Instant.parse(newHistorianValue.timeStamp); 
          }
          // TODO here is where we want to check to make sure this is a numeric variable
          // try to convert the value recieved into a double
          Double valueToInsert = Double.parseDouble(newHistorianValue.varValue);
          result = variableHistoryNDao.insertVarHistoryByVarId(varIdToInsert, SampleTimeToInsert, valueToInsert, newHistorianValue.quality);
        } catch (NumberFormatException e) {
          System.out.println("Error: " + LocalDateTime.now() + "|var_id:" + varIdToInsert + " varValue:(" + newHistorianValue.varValue + ") format failure" ); 
        }
        catch (DateTimeParseException e) {
          System.out.println("Error: " + LocalDateTime.now() + "|var_id:" + varIdToInsert + " sampletime:(" + newHistorianValue.timeStamp + ") parse failure" );
        } 
      } else {
        System.out.println("Error: " + LocalDateTime.now() + "|var_id:" + varIdToInsert + " varValue was null." ); 
      }
    } else {
      System.out.println("Error: " + LocalDateTime.now() + "|Existing var_id:" + varIdToInsert + " not found." ); 
    }
    
    return result;
  }

  @GetMapping("/variableHistory/{varId}")
  public List<VariableHistoryN> getVariableHistoryN(@PathVariable int varId) {
    
    List<VariableHistoryN> result = new ArrayList<>();
    
    // check to see if the 
    Variable existingVariable = variableDao.getVariablebyID(varId);
    
    if (existingVariable != null){
      result = variableHistoryNDao.getAllVarHistoryVarId(varId);
    }
    if (result == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return result;
  }

  
  
  
  
}
