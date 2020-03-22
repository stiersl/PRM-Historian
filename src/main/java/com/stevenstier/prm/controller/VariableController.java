package com.stevenstier.prm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stevenstier.prm.model.Variable;
import com.stevenstier.prm.model.VariableHistoryN;
import com.stevenstier.prm.model.Dao.VariableDao;
import com.stevenstier.prm.model.Dao.VariableHistoryNDao;

@Controller
public class VariableController {

	@Autowired
	private VariableDao variableDao;

	@Autowired
	private VariableHistoryNDao variableHistoryNDao;

	@RequestMapping(path = "/homeTrend", method = RequestMethod.GET)
	public String showHomeTrend(ModelMap mm, @RequestParam(required = false) Long varId) {
		mm.put("variables", variableDao.getAllVariables());
		if (varId != null) {
			mm.put("variableHistories", variableHistoryNDao.getAllVarHistoryVarId(varId));
			mm.put("var", variableDao.getVariablebyID(varId));
		}
		return "homeTrend";
	}

	@GetMapping("/variables")
	public String showVariables(ModelMap mm) {
		mm.put("variables", variableDao.getAllVariables());
		return "variables";
	}

	@GetMapping("/variableInput")
	public String showVariableInput(ModelMap mm, @RequestParam(required = false) Long varId){
		if (varId != null) {
			mm.put("variableData",variableDao.getVariablebyID(varId));
		}
		
		if (mm.containsAttribute("variableData") == false) {
			Variable empty = new Variable();
			mm.put("variableData", empty);
		}
		return "variableInput";
	}

	@PostMapping("/variableInput")
	public String processHomeTrend(@Valid @ModelAttribute Variable variable, BindingResult result,
			RedirectAttributes ra, ModelMap mm) {

		if (result.hasErrors()) {
			ra.addFlashAttribute("variableData", variable);
			ra.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "variableData", result);
			return "redirect:/variableInput";
		} else {
			if (variable.getVarId() != null) {
				
				if (!variableDao.updateVariable(variable)) {
					ra.addFlashAttribute("variableData", variable);
					ra.addFlashAttribute("error", "Unable to Update Variable");
					return "redirect:/variableInput";
				} else return "redirect:/variables";
			} else {  
				Variable createdVariable = variableDao.createVariable(variable);
				if (createdVariable.getVarId() == null) {
					ra.addFlashAttribute("variableData", variable);
					ra.addFlashAttribute("error", "Unable to create Variable");
					return "redirect:/variableInput";
				} else return "redirect:/variables";
			}
			
		}
	}

	@GetMapping("/variableDelete")
	public String deleteVariable(ModelMap mm, @RequestParam(required = false) Long varId){
		
		Variable existingVariable = variableDao.getVariablebyID(varId);
		
		if (existingVariable!= null) variableDao.deleteVariable(varId);
		mm.put("variables", variableDao.getAllVariables());	
		return "variables";
	}
	
	@GetMapping("/variableHistoryInsert")
 public String InsertVariableHistory(ModelMap mm, @RequestParam Long varId, @RequestParam Double varValue){
  
  Variable existingVariable = variableDao.getVariablebyID(varId);
  
  if ((existingVariable!= null) && (varValue!= null)) {
    variableHistoryNDao.insertVarHistoryByVarId(varId, null, varValue, null);
  }
  mm.put("variables", variableDao.getAllVariables()); 
  return "variables";
 }
}
