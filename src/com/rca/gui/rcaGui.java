package com.rca.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class rcaGui {

	private static Display display;
	protected Shell shell;
	private TabFolder heroTabFolder;
	private TabItem consoleTab;
	private TabItem jsTab;
	private TabItem traceTab;
	private TabItem settingTab;
	private ScrolledComposite outputComposite;
	private static Label outputLablel;
	private static Text urlInput;
	private static ProgressBar progressBar;
	private static String URL = null;

	// Global signals to update GUI
	public static String outputConsoleString = "";
	public static boolean testFinishFlag = false;
	private Button clickBtnCheckbox;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			rcaGui window = new rcaGui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1. Init rca 2. Run a new thread of rca service 3. Update status on GUI
	 */
	public void runRca() throws InterruptedException {
		(new rcaService(URL)).start();
	}

	public static void setOutputConsole(String message) {
		outputConsoleString += message + "\n";
		// Update gui asyn
		display.asyncExec(new Runnable() {
			public void run() {				
				outputLablel.setText(outputConsoleString);
			}
		});
	}
	
	/* 
	 * Redraw the input bar
	 */
	public static void redrawInputUrl(){
		display.asyncExec(new Runnable() {
			public void run() {
				urlInput.setVisible(true);
				progressBar.setVisible(false);
				progressBar.setSelection(0);
			}
		});
	}
	
	public static void setProgressBar(final int currentProgress){
		display.asyncExec(new Runnable() {
			public void run() {
				progressBar.setSelection(currentProgress);
			}
		});
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("AutoFlox");

		urlInput = new Text(shell, SWT.BORDER);
		urlInput.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		urlInput.setText("http://");
		urlInput.setBounds(10, 523, 587, 28);
		
				progressBar = new ProgressBar(shell, SWT.NONE);
				progressBar.setLocation(10, 523);
				progressBar.setSize(587, 28);
				progressBar.setSelection(0);

		Button runBtn = new Button(shell, SWT.NONE);
		runBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				// runBtn is clicked
				URL = urlInput.getText();
				progressBar.setVisible(false);
				urlInput.setVisible(false);
				progressBar.setVisible(true);
				
				// Start rca
				try {
					runRca();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		runBtn.setBounds(618, 524, 156, 27);
		runBtn.setText("Go");

		Button clickACheckbox = new Button(shell, SWT.CHECK);
		clickACheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		clickACheckbox.setBounds(603, 32, 156, 16);
		clickACheckbox.setText("Click on Tag <a>");
		
		Button clickDivCheckbox = new Button(shell, SWT.CHECK);
		clickDivCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		clickDivCheckbox.setBounds(603, 56, 156, 16);
		clickDivCheckbox.setText("Click on Tag <div>");
		
		clickBtnCheckbox = new Button(shell, SWT.CHECK);
		clickBtnCheckbox.setBounds(603, 78, 156, 16);
		clickBtnCheckbox.setText("Click on Tag <button>");

		heroTabFolder = new TabFolder(shell, SWT.NONE);
		heroTabFolder.setBounds(10, 10, 587, 507);

		consoleTab = new TabItem(heroTabFolder, SWT.NONE);
		consoleTab.setText("Output Console");

		outputComposite = new ScrolledComposite(heroTabFolder, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		consoleTab.setControl(outputComposite);
		outputComposite.setExpandHorizontal(true);
		outputComposite.setExpandVertical(true);

		outputLablel = new Label(outputComposite, SWT.NONE);
		outputLablel.setText("");
		outputComposite.setContent(outputLablel);
		outputComposite.setMinSize(outputLablel.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));

		jsTab = new TabItem(heroTabFolder, SWT.NONE);
		jsTab.setText("Javascript Code");

		traceTab = new TabItem(heroTabFolder, SWT.NONE);
		traceTab.setText("Execution Trace");

		settingTab = new TabItem(heroTabFolder, SWT.NONE);
		settingTab.setText("RunnerClass Setting");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(664, 125, 73, 21);
		
		Label lblMaxStates = new Label(shell, SWT.NONE);
		lblMaxStates.setBounds(603, 128, 55, 15);
		lblMaxStates.setText("Max States:");
		
		Label lblDepth = new Label(shell, SWT.NONE);
		lblDepth.setBounds(603, 155, 55, 15);
		lblDepth.setText("Depth");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(664, 152, 73, 21);

	}
}
