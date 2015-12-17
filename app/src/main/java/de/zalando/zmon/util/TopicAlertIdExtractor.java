package de.zalando.zmon.util;

public class TopicAlertIdExtractor {

    public static String extractId(String topic) {
        String[] split = topic.split("-");
        return split[split.length - 1];
    }
}
