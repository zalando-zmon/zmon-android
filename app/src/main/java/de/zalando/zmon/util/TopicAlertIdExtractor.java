package de.zalando.zmon.util;

public class TopicAlertIdExtractor {

    public static int extractId(String topic) {
        String[] split = topic.split("-");
        return Integer.valueOf(split[split.length-1]);
    }
}
