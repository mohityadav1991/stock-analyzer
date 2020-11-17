package com.scaler.mohit.stock.analyzer.util;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
public class AnalyzerUtils {
    public static File getFileFromResources(String fileName) throws URISyntaxException {
        return Paths.get(
                Thread.currentThread().getContextClassLoader().getResource(fileName).toURI()).toFile();
    }
}
