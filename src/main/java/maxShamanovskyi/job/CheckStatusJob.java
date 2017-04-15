package maxShamanovskyi.job;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;


public class CheckStatusJob implements Job {
    private String url;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        HttpGet request = new HttpGet(url);
        try(CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(request)) {
            String message = new Date(System.currentTimeMillis()) + " - " + url + " - " + response.getStatusLine().getStatusCode() + "\n";
            writeToFile(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String message) throws IOException {
        Path path = Paths.get("src/main/resources/monitoring.txt");
        Files.write(path, message.getBytes(), StandardOpenOption.APPEND);
    }

}
