package DAO.Util;

public enum Shift {
	Morning, Evening, Night;
	
	String value;
	
	Shift(String value) {
		this.value = value;
	}

	Shift() {
	}  
}
