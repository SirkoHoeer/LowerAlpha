package view.translations;

public interface ITranslation {
	
	
	//Translation Name
	public String getTranslationName();
	
	//Title
	public String getFrameTitle();	
	
	//Menus
	public String getMenuFile();
	public String getMenuFileItemNew();
	public String getMenuFileItemSave();
	public String getMenuFileItemSaveAs();
	public String getMenuFileItemLoad();
	public String getMenuFileItemExit();
	
	public String getMenuEdit();
	
	public String getMenuOptions();
	public String getMenuOptionsOptions();
	public String getMenuOptionsLanguage();
	
	public String getMenuAbout();
	public String getMenuAboutAbout();

	
	//Main Tabs
	public String getMainMiddleTabbedPaneSource();
	public String getMainMiddleTabbedPaneDebugRuntime();
	
	//Down Tabs
	public String getMainDownTabbedPaneConsole();
	public String getMainDownTabbedPaneProblems();
	
	//Left Tabs
	public String getMainRightTabbedPaneMemory();
	public String getMainRightTabbedPaneStack();
	public String getMainRightTabbedPaneRegister();
	
	
	//Buttons
	public String getButtonCompile();
	public String getButtonNew();
	public String getButtonSave();
	public String getButtonSaveAs();
	public String getButtonLoad();
	public String getButtonPlay();
	public String getButtonPause();
	public String getButtonStop();
	public String getButtonStepInto();
	
	
		
}
