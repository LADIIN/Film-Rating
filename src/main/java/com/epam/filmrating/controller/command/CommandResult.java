package com.epam.filmrating.controller.command;

/**
 * Class: CommandResult
 * @author Vladislav Darkovich
 */
public class CommandResult {
    /**
     * Page path.
     */
    private final String page;
    /**
     * Is redirect.
     */
    private final boolean isRedirect;

    /**
     * CommandResult constructor.
     * @param page
     * @param isRedirect
     */
    public CommandResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    /**
     * Returns CommandResult which will be forwarded.
     * @param page
     * @return CommandResult
     */
    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }

    /**
     * Returns CommandResult which will be redirected.
     * @param page
     * @return CommandResult
     */
    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    /**
     * Allows getting page of CommandResult.
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * Allows getting isRedirect of CommandResult.
     * @return isRedirect
     */
    public boolean isRedirect() {
        return isRedirect;
    }
}
