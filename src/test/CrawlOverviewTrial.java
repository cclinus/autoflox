package test;

import com.crawljax.core.configuration.*;
import com.crawljax.core.CrawljaxController;
import com.crawljax.plugins.aji.JSModifyProxyPlugin;
import com.crawljax.plugins.aji.executiontracer.AstInstrumenter;
import com.crawljax.plugins.aji.executiontracer.JSExecutionTracer;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import com.crawljax.plugins.webscarabwrapper.WebScarabWrapper;

import com.rca.*;

import java.util.*;

public class CrawlOverviewTrial {

	//private static final String URL = "http://192.168.0.106:8080/tudu/welcome.action";
	//private static final String URL = "http://weather.aol.com";
	//private static final String URL = "http://pixarcompendium.110mb.com/";
	//private static final String URL = "http://192.168.0.109/taskfreak/login.php";
	//private static final String URL = "http://localhost:8888/index.php/sample_page.html";
	//private static final String URL = "http://www.weather.com/tv";
	//private static final String URL = "http://www.tumblr.com/why-tumblr";
	private static final String URL = "http://192.168.1.5/test/index.html";
	//private static final String URL = "http://frolinsfilms.110mb.com/sample_page.html"; //"http://rootcauseanalyzer.comuf.com/sample_page.html";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		long endTime;
		
		CrawljaxConfiguration config = new CrawljaxConfiguration();
		CrawlSpecification crawler = new CrawlSpecification(URL);
		crawler.setMaximumStates(5); //TaskFreak: 4, Tudu: 4, Others: 2
		crawler.setDepth(3); //TaskFreak: 3
		//crawler.clickDefaultElements();
		//crawler.click("DIV"); //Tumblr
		InputSpecification input = new InputSpecification(); //TaskFreak, Tudu
		//input.field("username").setValue("focarizajr"); //TaskFreak
		//input.field("password").setValue("legend"); //TaskFreak
		input.field("j_username").setValue("focarizajr"); //Tudu
		input.field("j_password").setValue("legend"); //Tudu
		//crawler.click("a").withAttribute("href", "javascript:freak_edit(1)"); //TaskFreak
		//crawler.click("a").withAttribute("href", "javascript:freak_edit(2)"); //TaskFreak
		//crawler.click("input").withAttribute("value", "Back to list"); //TaskFreak
		//crawler.click("input").withAttribute("name", "login"); //TaskFreak
		//crawler.click("input").withAttribute("value", "Cancel");
		crawler.click("input").withAttribute("value", "Log In"); //Tudu
		crawler.setInputSpecification(input); //TaskFreak, Tudu
		crawler.click("A").withAttribute("href", "javascript:showAddTodo()"); //Tudu
		crawler.click("A").withAttribute("href", "javascript:showAddTodoList()"); //Tudu
		crawler.click("A").withAttribute("href", "javascript:renderNextDays()"); //Tudu
		
		config.setCrawlSpecification(crawler);
		config.addPlugin(new CrawlOverview());
		
		ProxyConfiguration prox = new ProxyConfiguration();
		
		//"Exclude lists" for websites
		//Tumblr
		List<String> tumblrExclude = new ArrayList<String>();
		tumblrExclude.add(".*why-tumblrscript[03567]");
		
		//Weather
		List<String> weatherExclude = new ArrayList<String>();
		//weatherExclude.add(".*script[0-9]*");
		weatherExclude.add(".*a21.*");
		weatherExclude.add(".*20110607.*");
		weatherExclude.add(".*config.*js.*");
		weatherExclude.add(".*yui.*");
		
		//TaskFreak
		List<String> taskFreakExclude = new ArrayList<String>();
		taskFreakExclude.add(".*xajax.*");
		//taskFreakExclude.add(".*script[0-9]*");
		taskFreakExclude.add(".*calendar.*");
		//taskFreakExclude.add(".*freak.*js.*");
		
		//AOL
		List <String> aolExclude = new ArrayList<String>();
		aolExclude.add(".*aol.*");
		aolExclude.add(".*adsonar.*");
		aolExclude.add(".*cp.*js.*");
		aolExclude.add(".*doubleclick.*");
		aolExclude.add(".*2mdn.*");
		aolExclude.add(".*atwola.*");
		aolExclude.add(".*tacoda.*");
		
		//Tudu
		List<String> tuduExclude = new ArrayList<String>();
		//tuduExclude.add(".*js.*");
		//tuduExclude.add(".*script.*");
		tuduExclude.add(".*dwr.*");
		
		List<String> excludeList = tumblrExclude;
		
		WebScarabWrapper web = new WebScarabWrapper();
		JSModifyProxyPlugin modifier = new JSModifyProxyPlugin(new AstInstrumenter(), excludeList);
		modifier.excludeDefaults();
		modifier.setInstrumentAsyncs(false);
		web.addPlugin(modifier);
		JSExecutionTracer tracer = new JSExecutionTracer("daikon.assertions");
		tracer.setOutputFolder("/new_jsassertions");
		
		modifier.setJsSourceOutputFolder("js_files4");
		
		//Set up AutoFLox
		RCAPlugin rcap = new RCAPlugin("/new_jsassertions/executiontrace", "js_files4");
		rcap.setOutputFolder("autoflox_output");

		config.addPlugin(tracer);
		config.addPlugin(web);
		config.setProxyConfiguration(prox);
		
		config.addPlugin(rcap);
		
		//crawler.click("a").withText("FAQ");
		//crawler.setClickOnce(true);
		//crawler.click("a").withAttribute("href", "#");
		//crawler.dontClick("a").underXPath("//DIV[contains(@class, 'footer')]");
		//crawler.dontClick("a").underXPath("//DIV[@id='header_bar_bg']");
		
		try {
			CrawljaxController crawljax = new CrawljaxController(config);
			crawljax.run();
			
			endTime = System.nanoTime();
			long duration = endTime - startTime;
			
			System.out.println("TOTAL TIME (ns): " + duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}