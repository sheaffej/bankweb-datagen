package com.wackycode.datagen.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.wackycode.datagen.Utilities;
import com.wackycode.datagen.apps.website.WebAction;
import com.wackycode.datagen.apps.website.WebPage;

public class WebClient extends Client {

	private User user;
	private WebPage terminalPage;
	private ArrayList<WebPage> clickPath;
	private int maxPages;
	
	private int minPatience = 2;
	private int maxPatience = 10;
	
	private Random rand = Utilities.rand;

	private String sessionId;
	private Date clientTime;
	private int currentClickPathPos;
	
	
	// Constructor
	public WebClient(User user, WebPage terminalPage) {
		this.user = user;
		
		// Generate session id
		sessionId = Long.toString(Math.abs(rand.nextLong())) + Long.toString(Math.abs(rand.nextLong()));
//		System.out.println("Session ID: [" + sessionId + "]");
		
		// Set the terminal page & click path
		this.terminalPage = terminalPage;
		clickPath = new ArrayList<WebPage>();
		for (WebPage p : terminalPage.getPagePath()) {
			clickPath.add(p);
		}
		clickPath.add(terminalPage);
		currentClickPathPos = -1;	// Before 1st page (index 0) in clickPath
		
		// Determine patience
		maxPages = minPatience + rand.nextInt(maxPatience-minPatience);
	}

	
	// Properties
	public User getUser() {return user;}
	
	public String getSessionId() {return sessionId;}

	public WebPage getTerminalPage() {return terminalPage;}
	
	public ArrayList<WebPage> getClickPath() {return clickPath;}
	
	public Date getClientTime() {return clientTime;}
	public void setClientTime(Date clientTime) {this.clientTime = clientTime;}
	
	
	// Methods
	public WebAction nextAction(int maxNextSecs) {
		// Increment to the next page in the path
		currentClickPathPos++;
		
		// If the user has already arrived at their terminal page
		if (currentClickPathPos >= clickPath.size()) {
			return null;
		}

		// If the user has lost patience (abandoned visit)
		if (currentClickPathPos+1 >= maxPages) {
			return null;
		}
		
		WebAction action = new WebAction();
		action.User = user; 
		action.SessionId = sessionId;
		
		// Update clientTime
		clientTime = Utilities.nextDate(clientTime, maxNextSecs);
		action.Time = clientTime;
		
		// Get the next page
		action.Page = clickPath.get(currentClickPathPos);
		
		return action;
	} // nextAction
	
}
