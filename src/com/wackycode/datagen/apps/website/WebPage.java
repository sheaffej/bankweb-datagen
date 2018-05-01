package com.wackycode.datagen.apps.website;

import java.util.ArrayList;

public class WebPage {
	private int id;
	private String name;
	private String url;
	private ArrayList<Integer> links;
	private ArrayList<WebPage> pagePath;
	private boolean isTerminal = false;
	private int weight = 1;

	// Constructor
	public WebPage(int id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.links = new ArrayList<Integer>();
		this.pagePath = new ArrayList<WebPage>();
	} // constructor
	
	// Properties
	public int getId() {return id;}
	
	public String getName() {return name;}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	
	public ArrayList<Integer> getLinks() {return links;}
	
	public ArrayList<WebPage> getPagePath() {return pagePath;}
	
	public boolean isTerminal() {return isTerminal;}
	public void setTerminal(boolean isTerminal) {this.isTerminal = isTerminal;}
	
	public int getWeight() {return weight;}
	public void setWeight(int weight) {this.weight = weight;}
	
	public void addLink(int pageId) {links.add(pageId);}
	
	public void printPage() {
		System.out.println("PageID: " + id);
		System.out.println("Name: " + name);
		System.out.println("URL: " + url);
		
		// Print pagePath
		System.out.print("Path: ");
		for (WebPage p : pagePath) {
			System.out.print(p.name + "-->");
		}
		System.out.println(name);
		for(int toPage : links) {
			System.out.println("   Link to page: " + toPage);
		}
		if (isTerminal()) {
			System.out.println("Terminal [" + weight + "]");
		}
		System.out.println("=================");

	} // printPage

}
