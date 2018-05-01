package com.wackycode.datagen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.wackycode.datagen.apps.website.WebAction;
import com.wackycode.datagen.apps.website.WebPage;
import com.wackycode.datagen.apps.website.Website;
import com.wackycode.datagen.client.BankUserProfile;
import com.wackycode.datagen.client.User;
import com.wackycode.datagen.client.WebClient;

public class DataGenerator {

	private static String dataDir = "/Users/jsheaffer/working/datagen";
	
	// Input files
	private static String pagesInFile = dataDir + "/pages.txt"; 
	private static String usersInFile = dataDir + "/users.txt";
	
	// Output files
	private static String userOutFile = dataDir + "/users.out";
	private static String bankProfileOutFile = dataDir + "/bankprofile.out";
	private static String actionsOutFile = dataDir + "/actions";
	private static int numFileRecords = 100000;  // # of records to put in each data file
	
	private static int startYear = 2013;
	private static int startMonth = 5;
	private static int startDay = 11;
	private static int startHour = 9;
	private static int startMinute = 0;
	
	public static void main(String[] args) {
		int numUsers = 50000;
		int numClients = 50000000;
		int maxClientThinkSecs =300;
		
		Random rand = Utilities.rand;
		PrintWriter out = null;
		
		
		Website site = new Website();
		User[] userList = null;
		
		// Load the Website
		System.out.println("Loading the Website...");
		File file = new File(pagesInFile); 
		site.loadPages(file);
//		site.printSite();

		// Record the terminal pages
		ArrayList<WebPage> terminalPages = site.getTerminalPages();
		
		// Load the User list
		System.out.println("Loading the User list...");
		userList = User.loadUsers(new File(usersInFile), numUsers);
		
		// Write out the User list we used
		System.out.println("Saving the user list...");
		out = openPrintWriter(new File(userOutFile));
		for(User u : userList) {
			out.println(u.getRecordString());
		}
		out.close();
		
		
		// Generate Banking Profiles
		System.out.println("Generating Banking Profiles...");
		ArrayList<BankUserProfile> bankProfiles = new ArrayList<BankUserProfile>();
		for (User u : userList) {
			bankProfiles.add(BankUserProfile.generate(u.Id));
		}
		
		// Write out the Bank Profiles
		System.out.println("Saving the Banking Profiles...");
		out = openPrintWriter(new File(bankProfileOutFile));
		for(BankUserProfile p : bankProfiles) {
			out.println(p.getRecordString());
		}
		out.close();

			
		// Determine an initial time stamp
		Calendar cal = Calendar.getInstance();
		cal.set(startYear, startMonth, startDay, startHour, startMinute);

		Date ts = cal.getTime();

		// Prepare the actions log
		out = openPrintWriter(new File(actionsOutFile + ".0.out"));

		
		
		// ------------------------
		// The main generation loop
		// ------------------------
		System.out.println("Generating Events...");
		for (int i = 0; i < numClients; i++) {
			// Break up in numFileRecords
			if (i > 0 && i % numFileRecords == 0) {
				out.close();
				String newFileName = actionsOutFile + "." + i/numFileRecords + ".out";
//				System.out.println("New file = " + newFileName);
				out = openPrintWriter(new File(newFileName));				
			}
			
			// Generate a time stamp update
			ts = Utilities.nextDate(ts, maxClientThinkSecs);
			
			// Pick a User
			int userId = rand.nextInt(userList.length-1);
			
			// Pick a terminal page
			WebPage terminalPage = terminalPages.get(rand.nextInt(terminalPages.size()));

			
			//
			// Tweak the results :-)
			// Markets=9, Stocks=10, LearningCenter=16
			//
			
			// Reduce Stocks & Markets -> Has Advisor
			if (terminalPage.getUrl().contains("research/stocks") ||
				terminalPage.getUrl().contains("research/markets")) {
				BankUserProfile p = bankProfiles.get(userId); 
				if (p.HasAdvisor) {
					if (rand.nextInt() % 4 > 1) {
						continue;
					}
				}
			}

			// Reduce Stocks -> No Advisor & No Trader
			if (terminalPage.getUrl().contains("research/stocks")) {
				BankUserProfile p = bankProfiles.get(userId); 
				if (!p.HasAdvisor && !p.HasActiveTrader) {
					if (rand.nextInt() % 4 > 1) {
						//Convert to the Learning Center
						terminalPage = terminalPages.get(16);
					}
				}				
			}

			// Reduce Stocks -> No Advisor & Active Trader
//			if (terminalPage.getUrl().contains("research/stocks")) {
//				BankUserProfile p = bankProfiles.get(userId); 
//				if (!p.HasAdvisor && p.HasActiveTrader) {
//					if (rand.nextInt() % 4 > -1) {
//						//Convert to the Learning Center
//						terminalPage = terminalPages.get(9);
//					}
//				}				
//			}
			
			
			// Create a client
			WebClient client = new WebClient(userList[userId], terminalPage);
			client.setClientTime(ts);
			
			// Perform the client's actions
			WebAction action = client.nextAction(maxClientThinkSecs);
			while (action != null) {
				// Record the action
				out.println(action.getRecordString());
				
				// Get next action
				action = client.nextAction(maxClientThinkSecs);
				
				// If the next action is null, then we go onto the next client
			}
			
			if (i % 100000 == 0) {
				System.out.println("Generated " + i + " events. Timestamp: " + ts.toString());
			}
						
		} // main generation for-loop
		
		System.out.print("Finishing...");
		out.close();
		System.out.println("Done.");
		
		
					
	} // main
	
	
	private static PrintWriter openPrintWriter(File file) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			System.err.println("Could not open output file " + file.getPath());
			e.printStackTrace();
			System.exit(1);
		}		
		return writer;
	} // openPrintWriter
	

}
