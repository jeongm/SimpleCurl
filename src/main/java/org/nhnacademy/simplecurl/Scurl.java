package org.nhnacademy.simplecurl;

import org.apache.commons.cli.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Scurl {
    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("v","verbose, 요청, 응답 헤더를 출력합니다.");
        options.addOption(Option.builder("H").hasArg().argName("line").desc("임의의 헤더를 서버로 전송합니다.").build());
        options.addOption(Option.builder("d").hasArg().argName("data").desc("POST, PUT 등에 데이타를 전송합니다.").build());
        options.addOption(Option.builder("X").hasArg().argName("Command").desc("사용할 method 를 지정합니다. 지정되지 않은 경우 기본값은 GET 입니다.").build());
        options.addOption(Option.builder("L").desc("서버의 응딥이 30x 계열이면 다음 응답을 따라 갑니다.").build());
        options.addOption(Option.builder("F").hasArg().argName("name=content").desc("multipart/form-data 를 구성하여 전송합니다. content 부분에 @filename 을 사용할 수 있습니다.").build());

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options,args);

            URL url = new URL(cmd.getArgList().get(cmd.getArgList().size()-1));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(cmd.hasOption("v")) {
                JSONObject responseObject = new JSONObject();
                Map<String, List<String>> connectionHeaderFields = connection.getHeaderFields();

                System.out.println(connectionHeaderFields);


            }else if(cmd.getArgList().get(0) == cmd.getArgList().get(cmd.getArgList().size()-1)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while((line = reader.readLine()) != null) { // response 출력
                    System.out.println(line);
                }
            }else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("scurl",options);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
