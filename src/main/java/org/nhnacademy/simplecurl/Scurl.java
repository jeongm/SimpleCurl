package org.nhnacademy.simplecurl;

import org.apache.commons.cli.*;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Scurl {

    public static void main(String[] args) {
        JSONObject responseObject = new JSONObject();
        JSONObject requestObject = new JSONObject();

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

            connection.setDoOutput(true);
            connection.setDoInput(true);

            String method = "GET";
            requestObject.put("",method+" / HTTP/1.1");
            requestObject.put("HOST",url.getHost());
            requestObject.put("User-Agent","scurl/1.0");
            requestObject.put("Accept", "*/*");

            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("scurl",options);
            } else {
                if (cmd.hasOption("H")) {
                    String line = cmd.getOptionValue("H");
                    String[] lines = line.split(":");
                    connection.setRequestProperty(lines[0],lines[1]);
                }
                if (cmd.hasOption("d")) {
                    String data = cmd.getOptionValue("d");
                    responseObject.put("data",data);
                }
                if (cmd.hasOption("X")) { // GET 요청 http://httpbin.org/getmethod 명을 명시적으로 지정
                    method = cmd.getOptionValue("X");

                    connection.setRequestMethod(method);
                    connection.connect();
                    requestObject.put("",connection.getRequestMethod()+" / HTTP/1.1");
                }
                if (cmd.hasOption("L")) { // ...
                }
                if (cmd.hasOption("H")) {
                    requestObject.put("X-Custom-Header",cmd.getOptionValue("H"));
                }
                if(cmd.hasOption("F")) {
                    String file = cmd.getOptionValue("F").substring(cmd.getOptionValue("F").lastIndexOf("@")+1);
                    connection.setRequestProperty("multipart/form-data",file);
                    connection.connect();
                }
                if(cmd.hasOption("v")) {
                    Map<String, List<String>> responseHeaderFields = connection.getHeaderFields();
                    for(Map.Entry<String, List<String>> entry: responseHeaderFields.entrySet()) {
                        String key = entry.getKey();
                        if(entry.getKey() == null) {
                            key = "";
                        }
                        Object value = entry.getValue();
                        responseObject.put(key,value);
                    }

                    //요청헤더
                    System.out.println(requestObject.toString(4));
                    //응답헤더
                    System.out.println(responseObject.toString(4));

                }
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

                StringBuilder writer = new StringBuilder();
                String line;
                while((line = input.readLine()) != null) { // response 출력
                    writer.append(line);
                    writer.append('\n');
                }

                JSONObject body = new JSONObject(writer.toString());
                output.write(body.toString(4));
                output.newLine();
                output.flush();

            }
        } catch (IOException | ParseException e) {
            Thread.currentThread().interrupt();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("scurl: try scurl '-h' for more information");
        }


    }
}
