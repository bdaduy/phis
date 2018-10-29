package edu.db.tool.deid.utils;

/**
 *
 * @author Duy Bui
 */
public class MatchMethod {

    public boolean matchContentOnly = false;

    public static MatchMethod instance(boolean matchContentOnly) {
        MatchMethod method = new MatchMethod();
        method.matchContentOnly = matchContentOnly;
        return method;
    }

}
