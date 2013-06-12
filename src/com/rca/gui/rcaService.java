package com.rca.gui;

import com.crawljax.core.CrawljaxController;
import com.crawljax.core.configuration.CrawlSpecification;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.ProxyConfiguration;
import com.crawljax.plugins.aji.JSModifyProxyPlugin;
import com.crawljax.plugins.aji.executiontracer.AstInstrumenter;
import com.crawljax.plugins.aji.executiontracer.JSExecutionTracer;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import com.crawljax.plugins.webscarabwrapper.WebScarabWrapper;
import com.rca.RCAPlugin;

public class rcaService extends Thread {
	
	private String URL;

	public void run() {
		System.out.println("rcaService thread starts");
		long startTime = System.nanoTime();
		long endTime;
		
		// Init crawljax
		rcaGui.setOutputConsole("Initial Crawljax");
		
		// Update progress bar
		rcaGui.setProgressBar(10);
		
		CrawljaxConfiguration config = new CrawljaxConfiguration();
		CrawlSpecification crawler = new CrawlSpecification(URL);
		crawler.setMaximumStates(5); //TaskFreak: 4, Tudu: 4, Others: 2
		crawler.setDepth(3); //TaskFreak: 3
		
		config.setCrawlSpecification(crawler);
		config.addPlugin(new CrawlOverview());
		
		ProxyConfiguration prox = new ProxyConfiguration();
		WebScarabWrapper web = new WebScarabWrapper();
		JSModifyProxyPlugin modifier = new JSModifyProxyPlugin(new AstInstrumenter());
		modifier.excludeDefaults();
		modifier.setInstrumentAsyncs(false);
		web.addPlugin(modifier);
		JSExecutionTracer tracer = new JSExecutionTracer("daikon.assertions");
		tracer.setOutputFolder("/new_jsassertions");
		
		modifier.setJsSourceOutputFolder("js_files4");
		
		//Set up AutoFLox
		rcaGui.setOutputConsole("Setup AutoFlox");
		RCAPlugin rcap = new RCAPlugin("/new_jsassertions/executiontrace", "js_files4");
		rcap.setOutputFolder("autoflox_output");

		config.addPlugin(tracer);
		config.addPlugin(web);
		config.setProxyConfiguration(prox);
		config.addPlugin(rcap);
		
		try {
			CrawljaxController crawljax = new CrawljaxController(config);
			rcaGui.setOutputConsole("AutoFlox starts");
			crawljax.run();
			
			endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			System.out.println("TOTAL TIME (ns): " + duration);
			rcaGui.setOutputConsole("Test Finishes");
			rcaGui.setOutputConsole("Total Time (ns): " + duration + "\n\n");
			rcaGui.testFinishFlag = true;
			
			// Update progress bar
			rcaGui.setProgressBar(100);
			
			rcaGui.redrawInputUrl();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	rcaService(String URL) {
		this.URL = URL;
	}
}
