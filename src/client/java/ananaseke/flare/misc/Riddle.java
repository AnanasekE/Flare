package ananaseke.flare.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Riddle {
    Map<String, String> npcsAndStatements = new HashMap<>();

    public Map<String, String> getNpcsAndStatements() {
        return npcsAndStatements;
    }

    public void add(String full) throws RiddleNotAddedException {
        String npc;
        String statement;
        Pattern pattern = Pattern.compile("[(A-Za-z)]: (./)./");
        Matcher matcher = pattern.matcher(full);
        if (matcher.find()) {
            npc = matcher.group(1);
            statement = matcher.group(2);
            npcsAndStatements.put(npc, statement);
        } else {
            throw new RiddleNotAddedException();
        }
    }
}
