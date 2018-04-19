package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.text.Document;
import view.ActionListeners.JButtonCompileViewActionListener;
import view.ActionListeners.JButtonLoadViewActionListener;
import view.ActionListeners.JButtonNewViewActionListener;
import view.ActionListeners.JButtonPauseViewActionListener;
import view.ActionListeners.JButtonPlayViewActionListener;
import view.ActionListeners.JButtonSaveViewActionListener;
import view.ActionListeners.JButtonSaveAsViewActionListener;
import view.ActionListeners.JButtonStepIntoViewActionListener;
import view.ActionListeners.JButtonStopViewActionListener;
import view.ActionListeners.JCheckBoxDarkThemeActionListener;
import view.ActionListeners.JComboBoxLookAndFeelActionListener;
import view.ActionListeners.JMenuItemAboutViewActionListener;
import view.ActionListeners.JMenuItemExitViewActionListener;
import view.ActionListeners.JMenuItemLanguageViewActionListener;
import view.ActionListeners.JMenuItemLoadViewActionListener;
import view.ActionListeners.JMenuItemNewViewActionListener;
import view.ActionListeners.JMenuItemSaveViewActionListener;
import view.ActionListeners.JMenuItemSaveAsViewActionListener;
import view.ActionListeners.JSourceViewDocumentListener;
import view.translations.ITranslation;
import view.translations.JTranslationEnglish;
import view.translations.JTranslationFrench;
import view.translations.JTranslationGerman;
import view.translations.JTranslationJapanese;
import view.translations.JTranslationPolish;
import view.translations.JTranslationRussian;

public class JAlphaNotationGUI {
	
	protected JFrame MainFrame;
	
	protected BorderLayout MainFrameLayout;
	protected BorderLayout MainLeftPanelLayout;
	protected BorderLayout MainMiddlePanelLayout;
	protected BorderLayout MainRightPanelLayout;
	protected BorderLayout MainUpPanelLayout;
	protected BorderLayout MainDownPanelLayout;
	
	protected JPanel MainLeftPanel;		
	protected JPanel MainMiddlePanel;
	protected JPanel MainRightPanel;
	protected JPanel MainUpPanel;
	protected JPanel MainDownPanel;	
	
	public static final Dimension MainFrameDimension = new Dimension(1024, 768);
	public static final Dimension MainFrameMinimumDimension = new Dimension(1024, 768);
	
	public static final Dimension MainLeftPanelDimension = new  Dimension(0, 0);
	public static final Dimension MainMiddlePanelDimension = new Dimension(200, 300);
	public static final Dimension MainRightPanelDimension = new Dimension(360, 300);
	public static final Dimension MainUpPanelDimension = new Dimension(0, 0);
	public static final Dimension MainDownPanelDimension = new Dimension(0, 130);
	public static final Dimension MainMiddleDownPanelDimension = new Dimension(0, 0);
	public static final Dimension MainMiddleUpPanelDimension = new Dimension(0, 0);
	
	protected ITranslation IGUITranslation;
	
	protected List<ITranslation> GUITranslations;	
	
	protected JMenuBar MainMenuBar;
	
	protected JMenu MenuFile;
	protected JMenu MenuEdit;
	protected JMenu MenuOptions;
	protected JMenu MenuAbout;	

	protected JMenuItem MenuItemFileNew;
	protected JMenuItem MenuItemFileSave;
	protected JMenuItem MenuItemFileSaveAs;
	protected JMenuItem MenuItemFileLoad;	
	protected JMenuItem MenuItemFileExit;
	
	//placeholder
	protected JMenuItem MenuItemEdit;
	
	protected JMenuItem MenuItemOptionsOptions;
	protected JMenuItem MenuItemOptionsLanguage;
	
	protected JMenuItem MenuItemAboutAbout;
	
	protected JSeparator MenuSeperator_New;
	protected JSeparator MenuSeperator_Save;
	protected JSeparator MenuSeperator_Load;
	
	protected JMenuItemNewViewActionListener MenuItemNewActionListener;
	protected JMenuItemSaveViewActionListener MenuItemSaveActionListener;
	protected JMenuItemSaveAsViewActionListener MenuItemSaveAsActionListener;
	protected JMenuItemLoadViewActionListener MenuItemLoadActionListener;
	protected JMenuItemExitViewActionListener MenuItemExitActionListener;
	protected JMenuItemAboutViewActionListener MenuItemAboutActionListener;
	protected JMenuItemLanguageViewActionListener MenuItemLanguageActionListener;
	
	protected JButtonCompileViewActionListener ButtonCompileActionListener;
	protected JButtonNewViewActionListener ButtonNewActionListener;
	protected JButtonSaveViewActionListener ButtonSaveActionListener;
	protected JButtonSaveAsViewActionListener ButtonSaveAsActionListener;
	protected JButtonLoadViewActionListener ButtonLoadActionListener;
	protected JButtonPlayViewActionListener ButtonPlayActionListener;
	protected JButtonPauseViewActionListener ButtonPauseActionListener;
	protected JButtonStopViewActionListener ButtonStopActionListener;
	protected JButtonStepIntoViewActionListener ButtonStepIntoActionListener;
	
	protected JSourceViewDocumentListener SourceDocumentListener;
	
	protected JCheckBoxDarkThemeActionListener CheckBoxDarkThemeActionListener;
	protected JComboBoxLookAndFeelActionListener ComboBoxLookAndFeelActionListener;
	
	protected JButton ButtonCompile;		
	protected JButton ButtonNew;
	protected JButton ButtonSave;	
	protected JButton ButtonLoad;	
	protected JButton ButtonPlay;
	protected JButton ButtonPause;
	protected JButton ButtonStop;
	protected JButton ButtonStepInto;
	
	protected Icon IconPlay;
	protected Icon IconPause;
	protected Icon IconStop;
	protected Icon IconStepInto;
	protected Icon IconCompile;
	protected Icon IconNew;
	protected Icon IconSave;
	protected Icon IconLoad;
	
	//TODO might change the source jtextarea to jeditorpane
	protected JTextArea TextAreaSource;	
	protected JTextArea TextAreaConsole;
	
	protected JScrollPane ScrollPaneTextAreaSource;
	protected JScrollPane ScrollPaneTextAreaConsole;	
	
	protected JTabbedPane MainMiddleTabbedPane;
	protected JTabbedPane MainDownTabbedPane;
	protected JTabbedPane MainRightTabbedPane;
	
	protected JList<String> ListRegister;
	protected JList<String> ListMemory;
	protected JList<String> ListStack;
    protected JList<String> ListRuntimeDebug;
	
	protected JCheckBox CheckBoxDarkTheme;
	
	protected JComboBox<String> ComboBoxLookAndFeel;
    
    
	public static final boolean DEBUG = true;
	public static final boolean ANSI_CONSOLE = true;	
	
	public static final Color DARK_THEME_BACKGROUND = Color.BLACK;
	public static final Color DARK_THEME_FOREGROUND = Color.DARK_GRAY;
	
	public static final Color NORMAL_THEME_BACKGROUND = Color.WHITE;
	public static final Color NORMAL_THEME_FOREGROUND = Color.BLACK;
	
	public static final Dimension BUTTON_SIZE = new Dimension(42, 42);

	public JAlphaNotationGUI() {
		this.GUITranslations = new ArrayList<ITranslation>();
		this.GUITranslations.add(new JTranslationEnglish());
		this.GUITranslations.add(new JTranslationGerman());
		this.GUITranslations.add(new JTranslationJapanese());
		this.GUITranslations.add(new JTranslationRussian());
		this.GUITranslations.add(new JTranslationPolish());
		this.GUITranslations.add(new JTranslationFrench());
		
		this.IGUITranslation = GUITranslations.get(0);
		
		this.MainFrame = new JFrame(IGUITranslation.getFrameTitle());		
		
		MainFrame.setBounds(400, 400, JAlphaNotationGUI.MainFrameDimension.width, JAlphaNotationGUI.MainFrameDimension.height);		
		
		MainFrame.setMinimumSize(JAlphaNotationGUI.MainFrameMinimumDimension);		
		
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		MainFrame.setLocation(dim.width / 2 - MainFrame.getSize().width / 2, dim.height / 2 - MainFrame.getSize().height / 2 );		
		
		this.Init();
		
		this.setVisible(true);		
	}
	
	public JAlphaNotationGUI(int x, int y, int width, int height) {
		this();
		MainFrame.setBounds(x, y, width, height);
	}
	
	public JAlphaNotationGUI(int x, int y, Dimension2D dim) {
		this(x, y, (int)dim.getWidth(), (int)dim.getHeight());		
	}
	
	public void setVisible(boolean _set) {
		MainFrame.setVisible(_set);
	}
	
	protected void InitMenuBar() {
		this.MainMenuBar = new JMenuBar();		
	}	
	protected void AddMenuBar() {
		this.MainFrame.setJMenuBar(MainMenuBar);
	}
	
	public void InitCheckBox() {
		this.CheckBoxDarkTheme = new JCheckBox("Dark Theme");
	}
	
	public void AddCheckBox() {
		this.MainUpPanel.add(this.CheckBoxDarkTheme);		
	}
	
	protected void InitMenus() {
		this.MenuFile = new JMenu(IGUITranslation.getMenuFile());
		this.MenuEdit = new JMenu(IGUITranslation.getMenuEdit());
		this.MenuOptions = new JMenu(IGUITranslation.getMenuOptions());
		this.MenuAbout = new JMenu(IGUITranslation.getMenuAbout());
	}
	
	protected void AddMenus() {
		this.MainMenuBar.add(MenuFile);
		this.MainMenuBar.add(MenuEdit);
		this.MainMenuBar.add(MenuOptions);
		this.MainMenuBar.add(MenuAbout);
	}	
	
	protected void InitMenuItems() {
		this.MenuItemFileNew = new JMenuItem(IGUITranslation.getMenuFileItemNew(), UIManager.getIcon("FileView.fileIcon"));
        //TODO Menu Item Save
		//this.MenuItemFileSave = new JMenuItem(IGUITranslation.getMenuFileItemSave(), UIManager.getIcon("FileView.floppyDriveIcon"));
		this.MenuItemFileSaveAs = new JMenuItem(IGUITranslation.getMenuFileItemSaveAs(), UIManager.getIcon("FileView.floppyDriveIcon"));
		this.MenuItemFileLoad = new JMenuItem(IGUITranslation.getMenuFileItemLoad(), UIManager.getIcon("FileView.hardDriveIcon"));
		this.MenuItemFileExit = new JMenuItem(IGUITranslation.getMenuFileItemExit(), UIManager.getIcon("FileChooser.homeFolderIcon"));
		
		//menu item edit here		
		this.MenuItemOptionsOptions = new JMenuItem(IGUITranslation.getMenuOptionsOptions(), UIManager.getIcon("FileView.computerIcon"));
		this.MenuItemOptionsLanguage = new JMenuItem(IGUITranslation.getMenuOptionsLanguage());
		
		this.MenuItemAboutAbout = new JMenuItem(IGUITranslation.getMenuAboutAbout());
		
		this.MenuSeperator_New = new JSeparator();
		this.MenuSeperator_Save = new JSeparator();
		this.MenuSeperator_Load = new JSeparator();
	}
	
	protected void AddMenuItems() {
		this.MenuFile.add(MenuItemFileNew);
		this.MenuFile.add(MenuSeperator_New);
        //TODO Menu Item Save
        //this.MenuFile.add(MenuItemFileSave);
		this.MenuFile.add(MenuItemFileSaveAs);
		this.MenuFile.add(MenuSeperator_Save);
		this.MenuFile.add(MenuItemFileLoad);
		this.MenuFile.add(MenuSeperator_Load);
		this.MenuFile.add(MenuItemFileExit);
		
		//this.MenuEdit.add(MenuItemEdit);
		
		this.MenuOptions.add(MenuItemOptionsOptions);
		this.MenuOptions.add(MenuItemOptionsLanguage);
		
		this.MenuAbout.add(MenuItemAboutAbout);		
	}
	
	public void InitComboBoxLookAndFeel() {
		String[] arr = new String[UIManager.getInstalledLookAndFeels().length];
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = UIManager.getInstalledLookAndFeels()[i].getName();
		}
		
		this.ComboBoxLookAndFeel = new JComboBox(arr); 
	}
	public void AddComboBoxLookAndFeel() {
		this.MainUpPanel.add(this.ComboBoxLookAndFeel);		
	}
	
	
	public void AddCheckBoxDarkThemeActionListener(ActionListener ac) {
		this.CheckBoxDarkTheme.addActionListener(ac);
	}
		
	public void AddButtonCompileActionListener(ActionListener ac) {		
		this.ButtonCompile.addActionListener(ac);
	}
	
	public void AddButtonPauseActionListener(ActionListener ac) {
		this.ButtonPause.addActionListener(ac);
	}
	
	public void AddButtonPlayActionListener(ActionListener ac) {
		this.ButtonPlay.addActionListener(ac);
	}
	
	public void AddButtonStopActionListener(ActionListener ac) {
		this.ButtonStop.addActionListener(ac);
	}
	
	public void AddButtonStepIntoActionListener(ActionListener ac) {
		this.ButtonStepInto.addActionListener(ac);
	}

	
	protected void InitListeners() {
		this.MenuItemNewActionListener = new JMenuItemNewViewActionListener(this);
		this.MenuItemSaveActionListener = new JMenuItemSaveViewActionListener(this);
		this.MenuItemSaveAsActionListener = new JMenuItemSaveAsViewActionListener(this);
		this.MenuItemLoadActionListener = new JMenuItemLoadViewActionListener(this);
		this.MenuItemExitActionListener = new JMenuItemExitViewActionListener(this);
		this.MenuItemAboutActionListener = new JMenuItemAboutViewActionListener(this);
		this.MenuItemLanguageActionListener = new JMenuItemLanguageViewActionListener(this);
		
		this.ButtonCompileActionListener = new JButtonCompileViewActionListener(this);
		this.ButtonNewActionListener = new JButtonNewViewActionListener(this);
		this.ButtonSaveActionListener = new JButtonSaveViewActionListener(this);
		this.ButtonSaveAsActionListener = new JButtonSaveAsViewActionListener(this);
		this.ButtonLoadActionListener = new JButtonLoadViewActionListener(this);
		this.ButtonPlayActionListener = new JButtonPlayViewActionListener(this);
		this.ButtonPauseActionListener = new JButtonPauseViewActionListener(this);
		this.ButtonStopActionListener = new JButtonStopViewActionListener(this);
		this.ButtonStepIntoActionListener = new JButtonStepIntoViewActionListener(this);
		
		this.SourceDocumentListener = new JSourceViewDocumentListener(this);
		
		this.CheckBoxDarkThemeActionListener = new JCheckBoxDarkThemeActionListener(this);
		
		this.ComboBoxLookAndFeelActionListener = new JComboBoxLookAndFeelActionListener(this);
	}

	protected void AddListeners() {		
		this.MenuItemFileNew.addActionListener(MenuItemNewActionListener);
        //TODO MenuItem 
		//this.MenuItemFileSave.addActionListener(MenuItemSaveActionListener);
		this.MenuItemFileSaveAs.addActionListener(MenuItemSaveAsActionListener);
		this.MenuItemFileLoad.addActionListener(MenuItemLoadActionListener);
		this.MenuItemFileExit.addActionListener(MenuItemExitActionListener);
		this.MenuItemAboutAbout.addActionListener(MenuItemAboutActionListener);
		this.MenuItemOptionsLanguage.addActionListener(MenuItemLanguageActionListener);
		
		this.ButtonCompile.addActionListener(ButtonCompileActionListener);
		this.ButtonNew.addActionListener(ButtonNewActionListener);
		this.ButtonSave.addActionListener(ButtonSaveActionListener);		
		this.ButtonLoad.addActionListener(ButtonLoadActionListener);
		this.ButtonPlay.addActionListener(ButtonPlayActionListener);
		this.ButtonPause.addActionListener(ButtonPauseActionListener);
		this.ButtonStop.addActionListener(ButtonStopActionListener);
		this.ButtonStepInto.addActionListener(ButtonStepIntoActionListener);
		
		Document document = TextAreaSource.getDocument();
		document.addDocumentListener(SourceDocumentListener);
		this.TextAreaSource.setDocument(document);
		
		this.CheckBoxDarkTheme.addActionListener(CheckBoxDarkThemeActionListener);
		
		this.ComboBoxLookAndFeel.addActionListener(ComboBoxLookAndFeelActionListener);
	}
	
	protected void InitScrollPanes() {
		this.ScrollPaneTextAreaConsole = new JScrollPane(TextAreaConsole);
		this.ScrollPaneTextAreaSource = new JScrollPane(TextAreaSource);
	}
	
	
	protected void InitTextArea() {
		this.TextAreaSource = new JTextArea(10, 10);
		this.TextAreaConsole = new JTextArea(10, 10);
		
		//this.TextAreaSource.setBackground(Color.BLACK);
	}	
        
    public void setListRuntimeDebugCompileError(String [] arr, int index, String error) {
        arr[index] += " //error: " + error;
        
        ListRuntimeDebug.setListData(arr);
        SetSelectedAfterCompile(1);
    }

	protected void InitLists() {
		this.ListMemory = new JList<>();
		this.ListMemory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		
		this.ListRegister = new JList<>();
		this.ListRegister.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.ListStack = new JList<>();
		this.ListStack.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
        this.ListRuntimeDebug = new JList<>();
        this.ListRuntimeDebug.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}	
	
	public void SetCaretPositonSourceTextField(int i) {
		this.TextAreaSource.setCaretPosition(i);
	}
	
	public int GetCaretPositonSourceTextField() {
		return this.TextAreaSource.getCaretPosition();
	}
	
	public void SetListMemorey(String[] memory) {
		this.ListMemory.setListData(memory);		
	}
	
	public void SetListRegister(String[] register) {
		this.ListRegister.setListData(register);		
	}
	
	public void SetListStack(String[] stack) {
		this.ListStack.setListData(stack);		
	}
        
    public void SetSelectedAfterCompile(int i) {
    	this.MainMiddleTabbedPane.setSelectedIndex(i);
    }
	
    public void SetListRuntimeDebug(String[] source, int selected) {
        this.ListRuntimeDebug.setListData(source);        
        this.ListRuntimeDebug.setSelectedIndex(selected);
    }
    
	protected void InitTabbedPane() {
		this.MainDownTabbedPane = new JTabbedPane();
		this.MainMiddleTabbedPane = new JTabbedPane();
		this.MainRightTabbedPane = new JTabbedPane();
	}
	
	protected void AddTabbedPane() {
		this.MainDownPanel.add(MainDownTabbedPane);
		this.MainMiddlePanel.add(MainMiddleTabbedPane);
		this.MainRightPanel.add(MainRightTabbedPane);
	}
	
	protected void AddTabbedPaneContent() {
		this.MainMiddleTabbedPane.addTab(IGUITranslation.getMainMiddleTabbedPaneSource(), this.ScrollPaneTextAreaSource);
                
        //TODO Translation and Interface Update for languages
        this.MainMiddleTabbedPane.addTab(IGUITranslation.getMainMiddleTabbedPaneDebugRuntime(), this.ListRuntimeDebug);
                
		//this.MainMiddleTabbedPane.addTab(IGUITranslation.getMainMiddleTabbedPaneSource(), this.ScrollPaneTextSource);
		
		this.MainDownTabbedPane.addTab(IGUITranslation.getMainDownTabbedPaneConsole(), this.ScrollPaneTextAreaConsole);
		
		this.MainRightTabbedPane.addTab(IGUITranslation.getMainRightTabbedPaneRegister(), this.ListRegister);
		this.MainRightTabbedPane.addTab(IGUITranslation.getMainRightTabbedPaneMemory(), this.ListMemory);
		this.MainRightTabbedPane.addTab(IGUITranslation.getMainRightTabbedPaneStack(), this.ListStack);						
	}	
	
	protected void ColorPanels() {
		this.MainLeftPanel.setBackground(Color.black);
		this.MainMiddlePanel.setBackground(Color.blue);
		this.MainRightPanel.setBackground(Color.red);
		this.MainUpPanel.setBackground(Color.green);
		this.MainDownPanel.setBackground(Color.magenta);		
	}
	
	public void ColorNormalTheme() {
		
		//this.NORMAL_THEME_BACKGROUND;
		//this.NORMAL_THEME_FOREGROUND;
		
		this.ListMemory.setBackground(NORMAL_THEME_BACKGROUND);
		this.ListMemory.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.ListRegister.setBackground(NORMAL_THEME_BACKGROUND);
		this.ListRegister.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.ListRuntimeDebug.setBackground(NORMAL_THEME_BACKGROUND);
		this.ListRuntimeDebug.setForeground(NORMAL_THEME_FOREGROUND);
		
		
		this.ListStack.setBackground(NORMAL_THEME_BACKGROUND);
		this.ListStack.setForeground(NORMAL_THEME_FOREGROUND);
						
		this.MainDownPanel.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainDownPanel.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainUpPanel.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainUpPanel.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainLeftPanel.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainLeftPanel.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainRightPanel.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainRightPanel.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainMiddlePanel.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainMiddlePanel.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainDownTabbedPane.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainDownTabbedPane.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainMiddleTabbedPane.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainMiddleTabbedPane.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainRightTabbedPane.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainRightTabbedPane.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuAbout.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuAbout.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuEdit.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuEdit.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuFile.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuFile.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemAboutAbout.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemAboutAbout.setForeground(NORMAL_THEME_FOREGROUND);
		
		//this.MenuItemEdit.setBackground(DARK_THEME);
		//this.MenuItemEdit.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemFileExit.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemFileExit.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemFileLoad.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemFileLoad.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemFileNew.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemFileNew.setForeground(NORMAL_THEME_FOREGROUND);
		
		//this.MenuItemFileSave.setBackground(DARK_THEME);
		//this.MenuItemFileSave.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemFileSaveAs.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemFileSaveAs.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemOptionsLanguage.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemOptionsLanguage.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuItemOptionsOptions.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuItemOptionsOptions.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MenuOptions.setBackground(NORMAL_THEME_BACKGROUND);
		this.MenuOptions.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.TextAreaConsole.setBackground(NORMAL_THEME_BACKGROUND);
		this.TextAreaConsole.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.TextAreaSource.setBackground(NORMAL_THEME_BACKGROUND);
		this.TextAreaSource.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainMenuBar.setBackground(NORMAL_THEME_BACKGROUND);
		this.MainMenuBar.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.CheckBoxDarkTheme.setBackground(NORMAL_THEME_BACKGROUND);
		this.CheckBoxDarkTheme.setForeground(NORMAL_THEME_FOREGROUND);
		
		this.MainFrame.setBackground(NORMAL_THEME_BACKGROUND);
			
	}
	
	public void ColorDarkTheme() {
		
		this.ListMemory.setBackground(DARK_THEME_BACKGROUND);
		this.ListMemory.setForeground(DARK_THEME_FOREGROUND);
		
		this.ListRegister.setBackground(DARK_THEME_BACKGROUND);
		this.ListRegister.setForeground(DARK_THEME_FOREGROUND);
		
		this.ListRuntimeDebug.setBackground(DARK_THEME_BACKGROUND);
		this.ListRuntimeDebug.setForeground(DARK_THEME_FOREGROUND);
		
		
		this.ListStack.setBackground(DARK_THEME_BACKGROUND);
		this.ListStack.setForeground(DARK_THEME_FOREGROUND);
						
		this.MainDownPanel.setBackground(DARK_THEME_BACKGROUND);
		this.MainDownPanel.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainUpPanel.setBackground(DARK_THEME_BACKGROUND);
		this.MainUpPanel.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainLeftPanel.setBackground(DARK_THEME_BACKGROUND);
		this.MainLeftPanel.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainRightPanel.setBackground(DARK_THEME_BACKGROUND);
		this.MainRightPanel.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainMiddlePanel.setBackground(DARK_THEME_BACKGROUND);
		this.MainMiddlePanel.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainDownTabbedPane.setBackground(DARK_THEME_BACKGROUND);
		this.MainDownTabbedPane.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainMiddleTabbedPane.setBackground(DARK_THEME_BACKGROUND);
		this.MainMiddleTabbedPane.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainRightTabbedPane.setBackground(DARK_THEME_BACKGROUND);
		this.MainRightTabbedPane.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuAbout.setBackground(DARK_THEME_BACKGROUND);
		this.MenuAbout.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuEdit.setBackground(DARK_THEME_BACKGROUND);
		this.MenuEdit.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuFile.setBackground(DARK_THEME_BACKGROUND);
		this.MenuFile.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemAboutAbout.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemAboutAbout.setForeground(DARK_THEME_FOREGROUND);
		
		//this.MenuItemEdit.setBackground(DARK_THEME);
		//this.MenuItemEdit.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemFileExit.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemFileExit.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemFileLoad.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemFileLoad.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemFileNew.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemFileNew.setForeground(DARK_THEME_FOREGROUND);
		
		//this.MenuItemFileSave.setBackground(DARK_THEME);
		//this.MenuItemFileSave.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemFileSaveAs.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemFileSaveAs.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemOptionsLanguage.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemOptionsLanguage.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuItemOptionsOptions.setBackground(DARK_THEME_BACKGROUND);
		this.MenuItemOptionsOptions.setForeground(DARK_THEME_FOREGROUND);
		
		this.MenuOptions.setBackground(DARK_THEME_BACKGROUND);
		this.MenuOptions.setForeground(DARK_THEME_FOREGROUND);
		
		this.TextAreaConsole.setBackground(DARK_THEME_BACKGROUND);
		this.TextAreaConsole.setForeground(DARK_THEME_FOREGROUND);
		
		this.TextAreaSource.setBackground(DARK_THEME_BACKGROUND);
		this.TextAreaSource.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainMenuBar.setBackground(DARK_THEME_BACKGROUND);
		this.MainMenuBar.setForeground(DARK_THEME_FOREGROUND);
		
		this.CheckBoxDarkTheme.setBackground(DARK_THEME_BACKGROUND);
		this.CheckBoxDarkTheme.setForeground(DARK_THEME_FOREGROUND);
		
		this.MainFrame.setBackground(DARK_THEME_BACKGROUND);
	}
	
	
	protected void InitPanels() {
		this.MainLeftPanel = new JPanel();
		this.MainMiddlePanel = new JPanel();
		this.MainRightPanel = new JPanel();
		this.MainUpPanel = new JPanel();
		this.MainDownPanel = new JPanel();		

		this.MainLeftPanel.setPreferredSize(MainLeftPanelDimension);
		this.MainMiddlePanel.setPreferredSize(MainMiddlePanelDimension);
		this.MainRightPanel.setPreferredSize(MainRightPanelDimension);
		
		this.MainDownPanel.setPreferredSize(MainDownPanelDimension);
	}	
	
	protected void InitLayout() {
		this.MainFrameLayout = new BorderLayout();
		this.MainFrame.setLayout(MainFrameLayout);
		
		this.MainLeftPanelLayout = new BorderLayout();
		this.MainLeftPanel.setLayout(MainLeftPanelLayout);
		
		this.MainRightPanelLayout = new BorderLayout();
		this.MainRightPanel.setLayout(MainRightPanelLayout);
		
		this.MainUpPanelLayout = new BorderLayout();
		//this.MainUpPanel.setLayout(MainUpPanelLayout);
		
		this.MainDownPanelLayout = new BorderLayout();
		this.MainDownPanel.setLayout(MainDownPanelLayout);
		
		this.MainMiddlePanelLayout = new BorderLayout();
		this.MainMiddlePanel.setLayout(MainMiddlePanelLayout);
	}
	
	protected void AddPanels() {
		this.MainFrame.add(MainLeftPanel, BorderLayout.WEST);
		this.MainFrame.add(MainMiddlePanel, BorderLayout.CENTER);
		this.MainFrame.add(MainRightPanel, BorderLayout.EAST);
		this.MainFrame.add(MainDownPanel, BorderLayout.SOUTH);
		this.MainFrame.add(MainUpPanel, BorderLayout.NORTH);		
	}
	
	public void ChangeLanguage(ITranslation translation) {
		
		this.ButtonCompile.setToolTipText(translation.getButtonCompile());
		this.ButtonNew.setToolTipText(translation.getButtonNew());
		this.ButtonSave.setToolTipText(translation.getButtonSave());		
		this.ButtonLoad.setToolTipText(translation.getButtonLoad());
		this.ButtonPlay.setToolTipText(translation.getButtonPlay());
		this.ButtonPause.setToolTipText(translation.getButtonPause());
		this.ButtonStop.setToolTipText(translation.getButtonStop());
		this.ButtonStepInto.setToolTipText(translation.getButtonStepInto());
		
		this.MenuFile.setText(translation.getMenuFile());
		this.MenuItemFileNew.setText(translation.getMenuFileItemNew());
		//TODO: File Save Menu Item disabled
        //this.MenuItemFileSave.setText(translation.getMenuFileItemSave());
		this.MenuItemFileSaveAs.setText(translation.getMenuFileItemSaveAs());
		this.MenuItemFileLoad.setText(translation.getMenuFileItemLoad());
		this.MenuItemFileExit.setText(translation.getMenuFileItemExit());
		
		this.MenuEdit.setText(translation.getMenuEdit());
		//this.MenuItemEdit.setText();
		
		this.MenuOptions.setText(translation.getMenuOptions());
		this.MenuItemOptionsOptions.setText(translation.getMenuOptionsOptions());
		
		this.MenuAbout.setText(translation.getMenuAbout());
		this.MenuItemAboutAbout.setText(translation.getMenuAboutAbout());
				
		this.MainMiddleTabbedPane.setTitleAt(0, translation.getMainMiddleTabbedPaneSource());
		this.MainMiddleTabbedPane.setTitleAt(1, translation.getMainMiddleTabbedPaneDebugRuntime());
		this.MainDownTabbedPane.setTitleAt(0, translation.getMainDownTabbedPaneConsole());
		
		this.MainRightTabbedPane.setTitleAt(0, translation.getMainRightTabbedPaneRegister());
		this.MainRightTabbedPane.setTitleAt(1, translation.getMainRightTabbedPaneMemory());
		this.MainRightTabbedPane.setTitleAt(2, translation.getMainRightTabbedPaneStack());				
	}
	
	public void SetLanguage(int index) {
		this.IGUITranslation = this.GUITranslations.get(index);
		ChangeLanguage(IGUITranslation);
	}
	
	public void CompileSuccessfull() {
		this.ButtonCompile.setBackground(Color.green);
	}
	
	public void CompileException() {
		this.ButtonCompile.setBackground(Color.red);
	}
	
	public void Init() {		
		InitPanels();		
		InitLayout();		
		AddPanels();		
		//ColorPanels();
		
		InitCheckBox();
		InitComboBoxLookAndFeel();
		
		InitTextArea();	
		InitScrollPanes();
		InitLists();
		InitTabbedPane();
		AddTabbedPane();
		AddTabbedPaneContent();
		
		InitMenuBar();		
		InitMenus();		
		InitMenuItems();		
		AddMenuBar();				
		AddMenus();		
		AddMenuItems();
		
		InitIcons();
		
		AddComboBoxLookAndFeel();
		AddCheckBox();
		
		InitButtons();	
		SetButtonSizes();		
		AddButtons();		
		
		InitListeners();				
		AddListeners();		
		
		SetUp();
		
	}
	
	public void SetUp() {
		this.ButtonCompile.setBackground(Color.red);
		this.ButtonPause.setBackground(Color.red);
		this.ButtonPlay.setBackground(Color.red);
		this.ButtonStepInto.setBackground(Color.red);
		this.ButtonStop.setBackground(Color.red);
		
		ButtonPause.setEnabled(false);
		ButtonPlay.setEnabled(false);
		ButtonStepInto.setEnabled(false);
		ButtonStop.setEnabled(false);
	}
	
	public boolean ToggleButtonCompile(boolean b) {
		
		if (b) {
			ButtonCompile.setBackground(Color.green);
			
			ButtonPause.setBackground(Color.green);
			ButtonPause.setEnabled(true);
			
			ButtonPlay.setBackground(Color.green);
			ButtonPlay.setEnabled(true);
			
			ButtonStepInto.setBackground(Color.green);
			ButtonStepInto.setEnabled(true);
			
			ButtonStop.setBackground(Color.green);
			ButtonStop.setEnabled(true);
			
			return true;
			
		} else {
			ButtonCompile.setBackground(Color.red);			
			
			ButtonPause.setBackground(Color.red);
			ButtonPause.setEnabled(false);
			
			ButtonPlay.setBackground(Color.red);
			ButtonPlay.setEnabled(false);
			
			ButtonStepInto.setBackground(Color.red);
			ButtonStepInto.setEnabled(false);
			
			ButtonStop.setBackground(Color.red);
			ButtonStop.setEnabled(false);
			
			return false;
		}
	}
	
	public void setEnableButtonPause(boolean b) {
		this.ButtonPause.setEnabled(b);
	}
	
	public void setEnableButtonStop(boolean b) {
		this.ButtonStop.setEnabled(b);
	}
	
	public void setEnableButtonNew(boolean b) {
		this.ButtonNew.setEnabled(b);
	}
	
	public void setEnableButtonSave(boolean b) {
		this.ButtonSave.setEnabled(b);
	}
	
	public void setEnableButtonLoad(boolean b) {
		this.ButtonLoad.setEnabled(b);
	}
	
	public void setEnableButtonStepInto(boolean b) {
		this.ButtonStepInto.setEnabled(b);
	}
	
	public void setEnableButtonPlay(boolean b) {
		this.ButtonPlay.setEnabled(b);
	}
	
	public void setEnableButtonCompile(boolean b) {
		this.ButtonCompile.setEnabled(b);
	}
	
	public void SetTextSource(String msg) {
		this.TextAreaSource.setText(msg);
	}
	public String GetTextSource() {
		return this.TextAreaSource.getText();
	}
	public void AppendTextSource(String msg) {
		this.TextAreaSource.setText(this.TextAreaSource.getText() + msg);
	}
	public void ClearTextSource() {
		this.SetTextSource("");
	}
	
	public void AppendTextConsole(String msg) {
		this.TextAreaConsole.setText(this.TextAreaConsole.getText() + msg + "\n");
	}
	public void SetTextConsole(String msg) {
		this.TextAreaConsole.setText(msg);
	}
	public String GetTextConsole() {
		return this.TextAreaConsole.getText();
	}
	public void ClearTextConsole() {
		this.TextAreaConsole.setText("");
	}
	
	protected void InitIcons() {
		this.IconPause = new ImageIcon("src/icons/pause32.png");
		this.IconPlay = new ImageIcon("src/icons/play32.png");
		this.IconStop = new ImageIcon("src/icons/stop32.png");
		this.IconStepInto = new ImageIcon("src/icons/stepinto32.png");
		this.IconCompile = new ImageIcon("src/icons/compile32.png");
		this.IconLoad = new ImageIcon("src/icons/load32.png");
		this.IconSave = new ImageIcon("src/icons/save32.png");
		this.IconNew = new ImageIcon("src/icons/new32.png");
		
		
		this.IconPause = new ImageIcon(getClass().getResource("/icons/pause32.png"));
		this.IconPlay = new ImageIcon(getClass().getResource("/icons/play32.png"));
		this.IconStop = new ImageIcon(getClass().getResource("/icons/stop32.png"));
		this.IconStepInto = new ImageIcon(getClass().getResource("/icons/stepinto32.png"));
		this.IconCompile = new ImageIcon(getClass().getResource("/icons/compile32.png"));
		this.IconLoad = new ImageIcon(getClass().getResource("/icons/load32.png"));
		this.IconSave = new ImageIcon(getClass().getResource("/icons/save32.png"));
		this.IconNew = new ImageIcon(getClass().getResource("/icons/new32.png"));
		
	}
	
	protected void InitButtons() {
		/*
		this.ButtonCompile = new JButton(IGUITranslation.getButtonCompile());
		this.ButtonNew = new JButton(IGUITranslation.getButtonNew());
		this.ButtonSave = new JButton(IGUITranslation.getButtonSave());
		this.ButtonSaveAs = new JButton(IGUITranslation.getButtonSaveAs());
		this.ButtonLoad = new JButton(IGUITranslation.getButtonLoad());
		this.ButtonPlay = new JButton(IGUITranslation.getButtonPlay());
		this.ButtonPause = new JButton(IGUITranslation.getButtonPause());
		this.ButtonStepInto = new JButton(IGUITranslation.getButtonStepInto());
		this.ButtonStop = new JButton(IGUITranslation.getButtonStop());
		*/		
		
		this.ButtonCompile = new JButton();
		this.ButtonCompile.setIcon(IconCompile);
		this.ButtonCompile.setToolTipText(IGUITranslation.getButtonCompile());
		
		this.ButtonNew = new JButton();
		this.ButtonNew.setIcon(IconNew);		
		this.ButtonNew.setToolTipText(IGUITranslation.getButtonNew());
		
		this.ButtonSave = new JButton();
		this.ButtonSave.setIcon(IconSave);
		this.ButtonSave.setToolTipText(IGUITranslation.getButtonSave());		
		
		this.ButtonLoad = new JButton();
		this.ButtonLoad.setIcon(IconLoad);
		this.ButtonLoad.setToolTipText(IGUITranslation.getButtonLoad());
		
		this.ButtonPlay = new JButton();
		this.ButtonPlay.setIcon(IconPlay);
		this.ButtonPlay.setToolTipText(IGUITranslation.getButtonPlay());
		
		this.ButtonPause = new JButton();
		this.ButtonPause.setIcon(IconPause);
		this.ButtonPause.setToolTipText(IGUITranslation.getButtonPause());
				
		this.ButtonStepInto = new JButton();
		this.ButtonStepInto.setIcon(IconStepInto);
		this.ButtonStepInto.setToolTipText(IGUITranslation.getButtonStepInto());		
		
		this.ButtonStop = new JButton();
		this.ButtonStop.setIcon(IconStop);
		this.ButtonStop.setToolTipText(IGUITranslation.getButtonStop());		
	}
	
	protected void SetButtonSizes() {
		this.ButtonCompile.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
		this.ButtonNew.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
		this.ButtonSave.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);		
		this.ButtonLoad.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
		this.ButtonPause.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
		this.ButtonPlay.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
		this.ButtonStepInto.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
		this.ButtonStop.setPreferredSize(JAlphaNotationGUI.BUTTON_SIZE);
	}
	
	protected void AddButtons() {		
		this.MainUpPanel.add(this.ButtonNew);
		this.MainUpPanel.add(this.ButtonSave);		
		this.MainUpPanel.add(this.ButtonLoad);
		this.MainUpPanel.add(this.ButtonCompile);
		this.MainUpPanel.add(this.ButtonPlay);
		this.MainUpPanel.add(this.ButtonPause);
		this.MainUpPanel.add(this.ButtonStop);
		this.MainUpPanel.add(this.ButtonStepInto);
	}	
	
	public List<ITranslation> getGUITranslations() {
		return GUITranslations;
	}
	public ITranslation getIGUITranslation() {
		return IGUITranslation;
	}
	public void setIGUITranslation(ITranslation iGUITranslation) {
		IGUITranslation = iGUITranslation;
	}

	public JFrame getMainFrame() {
		return MainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		MainFrame = mainFrame;
	}

	public BorderLayout getMainFrameLayout() {
		return MainFrameLayout;
	}

	public void setMainFrameLayout(BorderLayout mainFrameLayout) {
		MainFrameLayout = mainFrameLayout;
	}

	public BorderLayout getMainLeftPanelLayout() {
		return MainLeftPanelLayout;
	}

	public void setMainLeftPanelLayout(BorderLayout mainLeftPanelLayout) {
		MainLeftPanelLayout = mainLeftPanelLayout;
	}

	public BorderLayout getMainMiddlePanelLayout() {
		return MainMiddlePanelLayout;
	}

	public void setMainMiddlePanelLayout(BorderLayout mainMiddlePanelLayout) {
		MainMiddlePanelLayout = mainMiddlePanelLayout;
	}

	public BorderLayout getMainRightPanelLayout() {
		return MainRightPanelLayout;
	}

	public void setMainRightPanelLayout(BorderLayout mainRightPanelLayout) {
		MainRightPanelLayout = mainRightPanelLayout;
	}

	public BorderLayout getMainUpPanelLayout() {
		return MainUpPanelLayout;
	}

	public void setMainUpPanelLayout(BorderLayout mainUpPanelLayout) {
		MainUpPanelLayout = mainUpPanelLayout;
	}

	public BorderLayout getMainDownPanelLayout() {
		return MainDownPanelLayout;
	}

	public void setMainDownPanelLayout(BorderLayout mainDownPanelLayout) {
		MainDownPanelLayout = mainDownPanelLayout;
	}

	public JPanel getMainLeftPanel() {
		return MainLeftPanel;
	}

	public void setMainLeftPanel(JPanel mainLeftPanel) {
		MainLeftPanel = mainLeftPanel;
	}

	public JPanel getMainMiddlePanel() {
		return MainMiddlePanel;
	}

	public void setMainMiddlePanel(JPanel mainMiddlePanel) {
		MainMiddlePanel = mainMiddlePanel;
	}

	public JPanel getMainRightPanel() {
		return MainRightPanel;
	}

	public void setMainRightPanel(JPanel mainRightPanel) {
		MainRightPanel = mainRightPanel;
	}

	public JPanel getMainUpPanel() {
		return MainUpPanel;
	}

	public void setMainUpPanel(JPanel mainUpPanel) {
		MainUpPanel = mainUpPanel;
	}

	public JPanel getMainDownPanel() {
		return MainDownPanel;
	}

	public void setMainDownPanel(JPanel mainDownPanel) {
		MainDownPanel = mainDownPanel;
	}

	public JMenuBar getMainMenuBar() {
		return MainMenuBar;
	}

	public void setMainMenuBar(JMenuBar mainMenuBar) {
		MainMenuBar = mainMenuBar;
	}

	public JMenu getMenuFile() {
		return MenuFile;
	}

	public void setMenuFile(JMenu menuFile) {
		MenuFile = menuFile;
	}

	public JMenu getMenuEdit() {
		return MenuEdit;
	}

	public void setMenuEdit(JMenu menuEdit) {
		MenuEdit = menuEdit;
	}

	public JMenu getMenuOptions() {
		return MenuOptions;
	}

	public void setMenuOptions(JMenu menuOptions) {
		MenuOptions = menuOptions;
	}

	public JMenu getMenuAbout() {
		return MenuAbout;
	}

	public void setMenuAbout(JMenu menuAbout) {
		MenuAbout = menuAbout;
	}

	public JMenuItem getMenuItemFileNew() {
		return MenuItemFileNew;
	}

	public void setMenuItemFileNew(JMenuItem menuItemFileNew) {
		MenuItemFileNew = menuItemFileNew;
	}

	public JMenuItem getMenuItemFileSave() {
		return MenuItemFileSave;
	}

	public void setMenuItemFileSave(JMenuItem menuItemFileSave) {
		MenuItemFileSave = menuItemFileSave;
	}

	public JMenuItem getMenuItemFileSaveAs() {
		return MenuItemFileSaveAs;
	}

	public void setMenuItemFileSaveAs(JMenuItem menuItemFileSaveAs) {
		MenuItemFileSaveAs = menuItemFileSaveAs;
	}

	public JMenuItem getMenuItemFileLoad() {
		return MenuItemFileLoad;
	}

	public void setMenuItemFileLoad(JMenuItem menuItemFileLoad) {
		MenuItemFileLoad = menuItemFileLoad;
	}

	public JMenuItem getMenuItemFileExit() {
		return MenuItemFileExit;
	}

	public void setMenuItemFileExit(JMenuItem menuItemFileExit) {
		MenuItemFileExit = menuItemFileExit;
	}

	public JMenuItem getMenuItemEdit() {
		return MenuItemEdit;
	}

	public void setMenuItemEdit(JMenuItem menuItemEdit) {
		MenuItemEdit = menuItemEdit;
	}

	public JMenuItem getMenuItemOptionsOptions() {
		return MenuItemOptionsOptions;
	}

	public void setMenuItemOptionsOptions(JMenuItem menuItemOptionsOptions) {
		MenuItemOptionsOptions = menuItemOptionsOptions;
	}

	public JMenuItem getMenuItemOptionsLanguage() {
		return MenuItemOptionsLanguage;
	}

	public void setMenuItemOptionsLanguage(JMenuItem menuItemOptionsLanguage) {
		MenuItemOptionsLanguage = menuItemOptionsLanguage;
	}

	public JMenuItem getMenuItemAboutAbout() {
		return MenuItemAboutAbout;
	}

	public void setMenuItemAboutAbout(JMenuItem menuItemAboutAbout) {
		MenuItemAboutAbout = menuItemAboutAbout;
	}

	public JSeparator getMenuSeperator_New() {
		return MenuSeperator_New;
	}

	public void setMenuSeperator_New(JSeparator menuSeperator_New) {
		MenuSeperator_New = menuSeperator_New;
	}

	public JSeparator getMenuSeperator_Save() {
		return MenuSeperator_Save;
	}

	public void setMenuSeperator_Save(JSeparator menuSeperator_Save) {
		MenuSeperator_Save = menuSeperator_Save;
	}

	public JSeparator getMenuSeperator_Load() {
		return MenuSeperator_Load;
	}

	public void setMenuSeperator_Load(JSeparator menuSeperator_Load) {
		MenuSeperator_Load = menuSeperator_Load;
	}

	public JMenuItemNewViewActionListener getMenuItemNewActionListener() {
		return MenuItemNewActionListener;
	}

	public void setMenuItemNewActionListener(JMenuItemNewViewActionListener menuItemNewActionListener) {
		MenuItemNewActionListener = menuItemNewActionListener;
	}

	public JMenuItemSaveViewActionListener getMenuItemSaveActionListener() {
		return MenuItemSaveActionListener;
	}

	public void setMenuItemSaveActionListener(JMenuItemSaveViewActionListener menuItemSaveActionListener) {
		MenuItemSaveActionListener = menuItemSaveActionListener;
	}

	public JMenuItemSaveAsViewActionListener getMenuItemSaveAsActionListener() {
		return MenuItemSaveAsActionListener;
	}

	public void setMenuItemSaveAsActionListener(JMenuItemSaveAsViewActionListener menuItemSaveAsActionListener) {
		MenuItemSaveAsActionListener = menuItemSaveAsActionListener;
	}

	public JMenuItemLoadViewActionListener getMenuItemLoadActionListener() {
		return MenuItemLoadActionListener;
	}

	public void setMenuItemLoadActionListener(JMenuItemLoadViewActionListener menuItemLoadActionListener) {
		MenuItemLoadActionListener = menuItemLoadActionListener;
	}

	public JMenuItemExitViewActionListener getMenuItemExitActionListener() {
		return MenuItemExitActionListener;
	}

	public void setMenuItemExitActionListener(JMenuItemExitViewActionListener menuItemExitActionListener) {
		MenuItemExitActionListener = menuItemExitActionListener;
	}

	public JMenuItemAboutViewActionListener getMenuItemAboutActionListener() {
		return MenuItemAboutActionListener;
	}

	public void setMenuItemAboutActionListener(JMenuItemAboutViewActionListener menuItemAboutActionListener) {
		MenuItemAboutActionListener = menuItemAboutActionListener;
	}

	public JMenuItemLanguageViewActionListener getMenuItemLanguageActionListener() {
		return MenuItemLanguageActionListener;
	}

	public void setMenuItemLanguageActionListener(JMenuItemLanguageViewActionListener menuItemLanguageActionListener) {
		MenuItemLanguageActionListener = menuItemLanguageActionListener;
	}

	public JButtonNewViewActionListener getButtonNewActionListener() {
		return ButtonNewActionListener;
	}

	public void setButtonNewActionListener(JButtonNewViewActionListener buttonNewActionListener) {
		ButtonNewActionListener = buttonNewActionListener;
	}

	public JButtonSaveViewActionListener getButtonSaveActionListener() {
		return ButtonSaveActionListener;
	}

	public void setButtonSaveActionListener(JButtonSaveViewActionListener buttonSaveActionListener) {
		ButtonSaveActionListener = buttonSaveActionListener;
	}

	public JButtonSaveAsViewActionListener getButtonSaveAsActionListener() {
		return ButtonSaveAsActionListener;
	}

	public void setButtonSaveAsActionListener(JButtonSaveAsViewActionListener buttonSaveAsActionListener) {
		ButtonSaveAsActionListener = buttonSaveAsActionListener;
	}

	public JButtonLoadViewActionListener getButtonLoadActionListener() {
		return ButtonLoadActionListener;
	}

	public void setButtonLoadActionListener(JButtonLoadViewActionListener buttonLoadActionListener) {
		ButtonLoadActionListener = buttonLoadActionListener;
	}

	public JButtonPlayViewActionListener getButtonPlayActionListener() {
		return ButtonPlayActionListener;
	}

	public void setButtonPlayActionListener(JButtonPlayViewActionListener buttonPlayActionListener) {
		ButtonPlayActionListener = buttonPlayActionListener;
	}

	public JButtonPauseViewActionListener getButtonPauseActionListener() {
		return ButtonPauseActionListener;
	}

	public void setButtonPauseActionListener(JButtonPauseViewActionListener buttonPauseActionListener) {
		ButtonPauseActionListener = buttonPauseActionListener;
	}

	public JButtonStopViewActionListener getButtonStopActionListener() {
		return ButtonStopActionListener;
	}

	public void setButtonStopActionListener(JButtonStopViewActionListener buttonStopActionListener) {
		ButtonStopActionListener = buttonStopActionListener;
	}

	public JButtonStepIntoViewActionListener getButtonStepIntoActionListener() {
		return ButtonStepIntoActionListener;
	}

	public void setButtonStepIntoActionListener(JButtonStepIntoViewActionListener buttonStepIntoActionListener) {
		ButtonStepIntoActionListener = buttonStepIntoActionListener;
	}

	public JSourceViewDocumentListener getSourceDocumentListener() {
		return SourceDocumentListener;
	}

	public void setSourceDocumentListener(JSourceViewDocumentListener sourceDocumentListener) {
		SourceDocumentListener = sourceDocumentListener;
	}

	public JButton getButtonNew() {
		return ButtonNew;
	}

	public void setButtonNew(JButton buttonNew) {
		ButtonNew = buttonNew;
	}

	public JButton getButtonSave() {
		return ButtonSave;
	}

	public void setButtonSave(JButton buttonSave) {
		ButtonSave = buttonSave;
	}

	public JButton getButtonLoad() {
		return ButtonLoad;
	}

	public void setButtonLoad(JButton buttonLoad) {
		ButtonLoad = buttonLoad;
	}

	public JButton getButtonPlay() {
		return ButtonPlay;
	}

	public void setButtonPlay(JButton buttonPlay) {
		ButtonPlay = buttonPlay;
	}

	public JButton getButtonPause() {
		return ButtonPause;
	}

	public void setButtonPause(JButton buttonPause) {
		ButtonPause = buttonPause;
	}

	public JButton getButtonStop() {
		return ButtonStop;
	}

	public void setButtonStop(JButton buttonStop) {
		ButtonStop = buttonStop;
	}

	public JButton getButtonStepInto() {
		return ButtonStepInto;
	}

	public void setButtonStepInto(JButton buttonStepInto) {
		ButtonStepInto = buttonStepInto;
	}

	public JTextArea getTextAreaSource() {
		return TextAreaSource;
	}

	public void setTextAreaSource(JTextArea textAreaSource) {
		TextAreaSource = textAreaSource;
	}
	
	public JTextArea getTextAreaConsole() {
		return TextAreaConsole;
	}

	public void setTextAreaConsole(JTextArea textAreaConsole) {
		TextAreaConsole = textAreaConsole;
	}

	public JTabbedPane getMainMiddleTabbedPane() {
		return MainMiddleTabbedPane;
	}

	public void setMainMiddleTabbedPane(JTabbedPane mainMiddleTabbedPane) {
		MainMiddleTabbedPane = mainMiddleTabbedPane;
	}

	public JTabbedPane getMainDownTabbedPane() {
		return MainDownTabbedPane;
	}

	public void setMainDownTabbedPane(JTabbedPane mainDownTabbedPane) {
		MainDownTabbedPane = mainDownTabbedPane;
	}

	public JTabbedPane getMainRightTabbedPane() {
		return MainRightTabbedPane;
	}

	public void setMainRightTabbedPane(JTabbedPane mainRightTabbedPane) {
		MainRightTabbedPane = mainRightTabbedPane;
	}

	public static Dimension getMainframedimension() {
		return MainFrameDimension;
	}

	public static Dimension getMainframeminimumdimension() {
		return MainFrameMinimumDimension;
	}

	public static Dimension getMainleftpaneldimension() {
		return MainLeftPanelDimension;
	}

	public static Dimension getMainmiddlepaneldimension() {
		return MainMiddlePanelDimension;
	}

	public static Dimension getMainrightpaneldimension() {
		return MainRightPanelDimension;
	}

	public static Dimension getMainuppaneldimension() {
		return MainUpPanelDimension;
	}

	public static Dimension getMaindownpaneldimension() {
		return MainDownPanelDimension;
	}

	public static Dimension getMainmiddledownpaneldimension() {
		return MainMiddleDownPanelDimension;
	}

	public static Dimension getMainmiddleuppaneldimension() {
		return MainMiddleUpPanelDimension;
	}

	public static boolean isDebug() {
		return DEBUG;
	}

	public static boolean isAnsiConsole() {
		return ANSI_CONSOLE;
	}

	public static Dimension getButtonSize() {
		return BUTTON_SIZE;
	}

	public void setGUITranslations(List<ITranslation> gUITranslations) {
		GUITranslations = gUITranslations;
	}

	public JButtonCompileViewActionListener getButtonCompileActionListener() {
		return ButtonCompileActionListener;
	}

	public void setButtonCompileActionListener(JButtonCompileViewActionListener buttonCompileActionListener) {
		ButtonCompileActionListener = buttonCompileActionListener;
	}

	public JButton getButtonCompile() {
		return ButtonCompile;
	}

	public void setButtonCompile(JButton buttonCompile) {
		ButtonCompile = buttonCompile;
	}

	public Icon getIconPlay() {
		return IconPlay;
	}

	public void setIconPlay(Icon iconPlay) {
		IconPlay = iconPlay;
	}

	public Icon getIconPause() {
		return IconPause;
	}

	public void setIconPause(Icon iconPause) {
		IconPause = iconPause;
	}

	public Icon getIconStop() {
		return IconStop;
	}

	public void setIconStop(Icon iconStop) {
		IconStop = iconStop;
	}

	public Icon getIconStepInto() {
		return IconStepInto;
	}

	public void setIconStepInto(Icon iconStepInto) {
		IconStepInto = iconStepInto;
	}

	public Icon getIconCompile() {
		return IconCompile;
	}

	public void setIconCompile(Icon iconCompile) {
		IconCompile = iconCompile;
	}

	public Icon getIconNew() {
		return IconNew;
	}

	public void setIconNew(Icon iconNew) {
		IconNew = iconNew;
	}

	public Icon getIconSave() {
		return IconSave;
	}

	public void setIconSave(Icon iconSave) {
		IconSave = iconSave;
	}

	public Icon getIconLoad() {
		return IconLoad;
	}

	public void setIconLoad(Icon iconLoad) {
		IconLoad = iconLoad;
	}

	public JScrollPane getScrollPaneTextAreaSource() {
		return ScrollPaneTextAreaSource;
	}

	public void setScrollPaneTextAreaSource(JScrollPane scrollPaneTextAreaSource) {
		ScrollPaneTextAreaSource = scrollPaneTextAreaSource;
	}

	public JScrollPane getScrollPaneTextAreaConsole() {
		return ScrollPaneTextAreaConsole;
	}

	public void setScrollPaneTextAreaConsole(JScrollPane scrollPaneTextAreaConsole) {
		ScrollPaneTextAreaConsole = scrollPaneTextAreaConsole;
	}

	public JList<String> getListRegister() {
		return ListRegister;
	}

	public void setListRegister(JList<String> listRegister) {
		ListRegister = listRegister;
	}

	public JList<String> getListMemory() {
		return ListMemory;
	}

	public void setListMemory(JList<String> listMemory) {
		ListMemory = listMemory;
	}

	public JList<String> getListStack() {
		return ListStack;
	}

	public void setListStack(JList<String> listStack) {
		ListStack = listStack;
	}

	public JList<String> getListRuntimeDebug() {
		return ListRuntimeDebug;
	}

	public void setListRuntimeDebug(JList<String> listRuntimeDebug) {
		ListRuntimeDebug = listRuntimeDebug;
	}

	public JCheckBox getCheckBoxDarkTheme() {
		return CheckBoxDarkTheme;
	}

	public void setCheckBoxDarkTheme(JCheckBox checkBoxDarkTheme) {
		CheckBoxDarkTheme = checkBoxDarkTheme;
	}

	public static Color getDarkTheme() {
		return DARK_THEME_BACKGROUND;
	}

	public static Color getNormalTheme() {
		return NORMAL_THEME_BACKGROUND;
	}

	public JCheckBoxDarkThemeActionListener getCheckBoxDarkThemeActionListener() {
		return CheckBoxDarkThemeActionListener;
	}

	public void setCheckBoxDarkThemeActionListener(JCheckBoxDarkThemeActionListener checkBoxDarkThemeActionListener) {
		CheckBoxDarkThemeActionListener = checkBoxDarkThemeActionListener;
	}

	public JComboBoxLookAndFeelActionListener getComboBoxLookAndFeelActionListener() {
		return ComboBoxLookAndFeelActionListener;
	}

	public void setComboBoxLookAndFeelActionListener(JComboBoxLookAndFeelActionListener comboBoxLookAndFeelActionListener) {
		ComboBoxLookAndFeelActionListener = comboBoxLookAndFeelActionListener;
	}

	public JComboBox<String> getComboBoxLookAndFeel() {
		return ComboBoxLookAndFeel;
	}

	public void setComboBoxLookAndFeel(JComboBox<String> comboBoxLookAndFeel) {
		ComboBoxLookAndFeel = comboBoxLookAndFeel;
	}

	public static Color getDarkThemeBackground() {
		return DARK_THEME_BACKGROUND;
	}

	public static Color getDarkThemeForeground() {
		return DARK_THEME_FOREGROUND;
	}

	public static Color getNormalThemeBackground() {
		return NORMAL_THEME_BACKGROUND;
	}

	public static Color getNormalThemeForeground() {
		return NORMAL_THEME_FOREGROUND;
	}
	
	
	
	
	
}

