package org.nhnacademy.simplecurl;

import org.apache.commons.cli.*;

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
        CommandLine cmd = parser.parse();



    }
}
