package controller;

import controllers.Actionlisteners.*;
import model.JModel;
import model.JRunnableEnableButtons;
import model.JRunnableInvokePlay;
import view.JAlphaNotationGUI;

/**
 * This class represents the handling between model and view. 
 * @author Maximus S.
 *    
 */

/*
 * lines \n[^\n]*
 * import  
 * variables protected protected([\s\S]).*;
 * variables private([\s\S]).*;
 * variables public([\s\S]).*; //constants  
 * methods public([\s\S]).*()([\s\S]).*\{
 *         private([\s\S]).*()([\s\S]).*\{         
 *  	   protected([\s\S]).*()([\s\S]).*\{
 * 
 */

public class JController {
	
	public JController() {
		
		model = new JModel();
		gui = new JAlphaNotationGUI();
		//this.CompileSuccessfull = false;
                
		initListenersView();		
		addListenersView();
		
		model.setRunnableInvokePlay(RunnableInvokePlay);
		model.setRunnableEnableButtons(RunnableEnableButtons);
	}
	
	//main components
	protected JAlphaNotationGUI gui;
	protected JModel model;
	
	//listeners 
	protected JButtonCompileControllerActionListener ButtonCompileControllerActionListener;
	protected JButtonLoadControllerActionListener ButtonLoadControllerActionListner;
	protected JButtonPauseControllerActionListener ButtonPauseControllerActionListener;
	protected JButtonPlayControllerActionListener ButtonPlayControllerActionListener;
	protected JButtonStepIntoControllerActionListener ButtonStepIntoControllerActionListener;
	protected JButtonStopControllerActionListener ButtonStopControllerActionListener;
	protected JMenuItemLoadControllerActionListener MenuItemLoadControllerActionListener;
	protected JRunnableInvokePlay RunnableInvokePlay;
	protected JRunnableEnableButtons RunnableEnableButtons;
        
    protected boolean CompileSuccessfull;
    
    public boolean getCompileSuccessfull() {
        return this.CompileSuccessfull;
    }
    
    public void setCompileSuccessfull(boolean var) {
        this.CompileSuccessfull = var;
    }

	protected void initListenersView() {
		this.ButtonCompileControllerActionListener = new JButtonCompileControllerActionListener(this.gui, this.model, this);
		this.ButtonLoadControllerActionListner = new JButtonLoadControllerActionListener(this.gui, this.model);
		this.ButtonPauseControllerActionListener = new JButtonPauseControllerActionListener(this.gui, this.model, this);
		this.ButtonPlayControllerActionListener = new JButtonPlayControllerActionListener(this.gui, this.model);
		this.ButtonStepIntoControllerActionListener = new JButtonStepIntoControllerActionListener(this.gui, this.model);
		this.ButtonStopControllerActionListener = new JButtonStopControllerActionListener(this.gui, this.model, this);
		this.MenuItemLoadControllerActionListener = new JMenuItemLoadControllerActionListener(this.gui, this.model);
		this.RunnableInvokePlay = new JRunnableInvokePlay(gui, model);
		this.RunnableEnableButtons = new JRunnableEnableButtons(gui, model);
	}
	
	protected void addListenersView() {
		this.gui.AddButtonCompileActionListener(ButtonCompileControllerActionListener);
		this.gui.AddButtonPauseActionListener(ButtonPauseControllerActionListener);
		this.gui.AddButtonPlayActionListener(ButtonPlayControllerActionListener);
		this.gui.AddButtonStopActionListener(ButtonStopControllerActionListener);
		this.gui.AddButtonStepIntoActionListener(ButtonStepIntoControllerActionListener);		
	}
	
	public void updatePanes() {
		
	}
	
	public static void main(String[] args) {
		
		JController con = new JController();
		
	}
}
