package ananaseke.flare.misc;

import ananaseke.flare.Config;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFilter {
    private final Predicate<Config> condition;
    private final Set<String> regexPatterns;

    public MessageFilter(Predicate<Config> condition, Set<String> regexPatterns) {
        this.condition = condition;
        this.regexPatterns = regexPatterns;
    }

    public boolean shouldHide(String message, Config config) {
        return condition.test(config) && matchesAny(message);
    }

    private boolean matchesAny(String message) {
        for (String patternString : regexPatterns) {
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                return true;
            }
        }
        return false;  // No matches found
    }
}
