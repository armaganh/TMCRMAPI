package com.tm.crm.enums;

public enum RoleEnum {
	
	ROLE_ADMIN			(10, "ROLE_ADMIN", 			"ADMIN"),
	ROLE_BOX_OFFICE 	(20, "ROLE_USER", 	"USER");
	
	private int code;
	private String label;
	private String description;

	private RoleEnum(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    } 
	
	public int getCode() {
        return code;
    }
 
    public String getLabel() {
        return label;
    }

	public String getDescription() {
		return description;
	}

	public static RoleEnum getRoleById(int id) {
	    for(RoleEnum role : values()) {
	        if(role.code == id) 
	        	return role;
	    }
	    return null;
	 }
	
	public static RoleEnum getRoleByName(String name) {
	    for(RoleEnum role : values()) {
	        if(role.name().equals(name)) 
	        	return role;
	    }
	    return null;
	 }
	
}
