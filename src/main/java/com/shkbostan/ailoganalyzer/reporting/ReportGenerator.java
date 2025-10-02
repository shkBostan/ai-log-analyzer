package com.shkbostan.ailoganalyzer.reporting;

import com.shkbostan.ailoganalyzer.analyzer.LogAnalyzer;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

public class ReportGenerator {

    public void generate(LogAnalyzer analyzer) {
        System.out.println("📊 Error Frequency Report:");
        analyzer.getErrorFrequency().forEach((error, count) ->
                System.out.println(error + " -> " + count + " times"));
    }
}
