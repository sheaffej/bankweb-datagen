package com.wackycode.datagen.client;

import java.util.Random;

public class BankUserProfile {
	public int UserId;	
	public boolean HasBanking;
	public boolean HasLifeInsurance;
	public boolean HasInvestments;
	public boolean HasAdvisor;
	public boolean HasActiveTrader;

	public static Random rand = new Random();
	
	
	public static BankUserProfile generate(int userId) {
		BankUserProfile p = new BankUserProfile();
		p.UserId = userId;
		
		// Banking Customer?
//		p.HasBanking = rand.nextBoolean();
		p.HasLifeInsurance = false;
		if (rand.nextInt() % 3 >= 1) {
			p.HasLifeInsurance = true;
		}

		
		// Life Insurance
		//p.HasLifeInsurance = rand.nextBoolean();
		p.HasLifeInsurance = false;
		if (rand.nextInt() % 6 == 0) {
			p.HasLifeInsurance = true;
		}
		
		// Investments
		p.HasInvestments = rand.nextBoolean();
		
		if (p.HasInvestments) {
			// Recalc HasBanking
			p.HasBanking = rand.nextBoolean();

			// Advisor
			p.HasAdvisor = rand.nextBoolean();
			
			// Active Trader
			p.HasActiveTrader = rand.nextBoolean();
		}
		
		return p;
	} // generate
	
	public String getRecordString() {
		String delim = "\t";
		String record = "";
		
		record += 
			UserId + delim
			+ HasBanking + delim
			+ HasLifeInsurance + delim
			+ HasInvestments + delim
			+ HasAdvisor + delim
			+ HasActiveTrader;
		
		return record;
	} // getRecordString
}
