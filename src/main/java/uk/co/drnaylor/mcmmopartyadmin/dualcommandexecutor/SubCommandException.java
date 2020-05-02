package uk.co.drnaylor.mcmmopartyadmin.dualcommandexecutor;

@SuppressWarnings("serial")
public class SubCommandException extends Exception {
    
	private final DualSubCommandInterface subCommand;
	
	@SuppressWarnings("unused")
	public SubCommandException(String errorMessage, DualSubCommandInterface subCommand) {
		this.subCommand = subCommand;
	}
    
    public DualSubCommandInterface getSubCommand() {
        return subCommand;
    }
    
}
