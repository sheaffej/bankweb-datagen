package com.wackycode.datagen.apps.website;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;

import com.wackycode.datagen.FileLineReader;

public class Website {
	private HashMap<Integer, WebPage> pages;
	private ArrayList<WebPage> terminalPages;
	
	// Constructor
	public Website() {
		pages = new HashMap<Integer, WebPage>();
		terminalPages = new ArrayList<WebPage>();
	} 
	

	// Properties
	public HashMap<Integer, WebPage> getPages() {return pages;}

	public ArrayList<WebPage> getTerminalPages() {return terminalPages;}
	
	
	
	// Methods
	public void addPage(int pageId, WebPage page) {
		pages.put(pageId, page);
	} // addPage
	
	public void loadPages (File file) {
		FileLineReader reader = new FileLineReader(file);
		
//		String[] url_segments = new String[10];
		WebPage[] url_segments = new WebPage[10];
		String line;
		String pagename = null;
		

		// Process a line in the file
		int pageid = 0;	// The line number
		while ((line = reader.readLine()) != null) {
			pageid++;
//			System.out.println("Line [" + line + "]");
			String[] fields = line.split("\\t");
//			System.out.println(Arrays.toString(fields));
			
			// Determine the page depth
//			lastDepth = depth;
			int depth = 0;
			for (int i = 0; i < fields.length; i++) {
				pagename = fields[i]; 				
				
				// Find the first non-zero field
				if (pagename.length() > 0) {
					depth = i;
					break;
				}	
			}

			// Create the page, but set URL to null for right now
			WebPage p = new WebPage(pageid, pagename, null);
			
			// Save the page path
//			url_segments[depth] = new String(pagename);
			url_segments[depth] = p;
			for (int i = 0; i < depth; i++) {
				p.getPagePath().add(url_segments[i]);
			}
			
			// Build the URL
			String url = "";
			for (int i = 0; i<= depth; i++) {
				String seg = url_segments[i].getName().toLowerCase();
				try {
					seg = URLEncoder.encode(seg, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url += "/" + seg;
			}
			
			// Set the URL
			p.setUrl(url);
			
			// Add the page to the site
			pages.put(pageid, p);

			// Add parent's link to this page
			if (depth > 0) {
				url_segments[depth-1].addLink(pageid);
			}
			
			// If there are any links on the page
			if (fields.length > depth + 1) {
				String[] links = fields[depth+1].split(",");
				for (int i = 0; i < links.length; i++) {
					p.addLink(Integer.parseInt(links[i]));
				}
			}
			
			// If the page is terminal
			if (fields.length > depth + 2) {
				if (fields[depth+2].equals("*")) {
					p.setTerminal(true);
				}

				int weight = 1;
				// If the page has a terminal weight
				if (fields.length > depth + 3) {
					weight = Integer.parseInt(fields[depth+3]);
				}
				
				// Add page to terminal_pages ArrayList, times the weight
				// i.e if weight is 1, add once.  If 5, add 5 times
				for (int i = 0; i < weight; i++) {
					p.setWeight(weight);
					terminalPages.add(p);
				}
			}
			
		}
		reader.close();
		
	} // loadPages

	
	public void printSite() {
		for (int id : pages.keySet()) {
			WebPage p = pages.get(id);
			p.printPage();
//			System.out.println("PageID: " + id);
//			System.out.println("Name: " + p.getName());
//			System.out.println("URL: " + p.getUrl());
//			ArrayList<Integer> links = p.getLinks();
//			for(int toPage : links) {
//				System.out.println("   Link to page: " + toPage);
//			}
//			if (p.isTerminal()) {
//				System.out.println("Terminal [" + p.getWeight() + "]");
//			}
//			System.out.println("=================");
		}
		
		System.out.println("Terminal Pages:");
		for(WebPage p : terminalPages) {
			System.out.println("  " + p.getName());
		}
		
	} // printSite
	
}