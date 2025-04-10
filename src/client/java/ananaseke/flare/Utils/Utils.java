package ananaseke.flare.Utils;

import net.minecraft.client.MinecraftClient;
import org.intellij.lang.annotations.RegExp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static class TestRegexResult {
        public TestRegexResult(boolean isMatching, List<String> groups) {
            this.isMatching = isMatching;
            this.groups = groups;
        }

        public boolean isMatching;
        public List<String> groups;
    }

    public static String removeColorTags(String string) {
        return string.replaceAll("(§[0-9a-fklmnor])", "");
    }

    public static TestRegexResult testRegex(String message, @RegExp String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        boolean found = matcher.find();
        if (found) {
            List<String> groups = new ArrayList<>();
            int groupCount = matcher.groupCount();
            for (int i = 0; i < groupCount; i++) {
                String group = matcher.group(i);
                groups.add(group);
            }
            return new TestRegexResult(true, groups);
        }
        return new TestRegexResult(false, null);
    }

    public static boolean testScreenTitle(String title, MinecraftClient client) {
        if (client.currentScreen == null) return false;
        return client.currentScreen.getTitle().getString().equals(title);
    }
}
