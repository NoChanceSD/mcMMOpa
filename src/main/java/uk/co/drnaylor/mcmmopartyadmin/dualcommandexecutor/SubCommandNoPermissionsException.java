package uk.co.drnaylor.mcmmopartyadmin.dualcommandexecutor;

import java.util.List;

public class SubCommandNoPermissionsException extends Exception {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<String> permissions;
	private String errorMessage;
	
	public SubCommandNoPermissionsException(String errorMessage, List<String> permissions) {
		this.errorMessage = errorMessage;
		this.permissions = permissions;
	}
    
    public List<String> getRequiredPermissions() {
        return permissions;
    }
    
    public String getErrorMessage() {
    	return errorMessage;
    }
    
}
