package com.back.step14.repository;


import com.back.step14.WiseSaying;
import com.back.step14.WiseSayingParser;

public class JsonManager extends WiseSayingParser {


    JsonManager() {}

    /**
     * makes one WiseSaying to json String
     * @param wiseSaying
     * @return
     */
    public String WiseSaying2json(WiseSaying wiseSaying) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"id\": ");
        json.append(wiseSaying.getId());
        json.append(",\n");
        json.append("  \"content\": \"");
        json.append(wiseSaying.getContent());
        json.append("\",\n");
        json.append("  \"author\": \"");
        json.append(wiseSaying.getAuthor());
        json.append("\"\n}");
        return json.toString();
    }

    /**
     * makes many WiseSayings to json string
     * @param iterator
     * @return
     */
    public String wiseSaying2json(WiseSayingList.WiseSayingIterator iterator){
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        while(iterator.hasNext()){
            // makes indent spaces
            String content_json = WiseSaying2json(iterator.next());
            String[] lines = content_json.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                json.append("  ");
                json.append(line);
                // last one need "," before "\n"
                if (i < lines.length - 1) {
                    json.append("\n");
                }
            }
            if (iterator.hasNext()) {
                json.append(",\n");
            }
        }
        json.append("\n]");
        return json.toString();
    }

    private String getStringValue(String key){
        findString();
        if (!readString().equals(key)){
            return null;
        }
        findChar(':');
        findString();
        String value = readString();
        return value;
    }

    private int getIntValue(String key){
        findString();
        if (!readString().equals(key)){
            return 0;
        }
        findChar(':');
        findNumber();
        int value = readNumber();
        return value;
    }

    /**
     * following the format, get information from json string<br>
     * if the string didn't followed the format, then this will return null<br>
     *
     * format is : <br>
     * {<br>
     * &nbsp&nbsp "id" : number,<br>
     * &nbsp&nbsp "content" : "string",<br>
     * &nbsp&nbsp "author" : "string"<br>
     * }<br>
     * @return
     */
    public WiseSaying readWiseSaying(){
        findChar('{');
        int id = getIntValue("id");
        findChar(',');
        String content_str = getStringValue("content");
        findChar(',');
        String author_str = getStringValue("author");
        findChar('}');
        WiseSaying wiseSaying = new WiseSaying(content_str, author_str);
        wiseSaying.setID(id);
        return wiseSaying;
    }

    /**
     * get one wiseSaying from json string
     * @param str
     * @return
     */
    public WiseSaying json2wiseSaying(String str){
        setString(str);
        return readWiseSaying();
    }

    /**
     * I made this but didn't use....<br>
     * format is :<br>
     * [<br>
     * &nbsp&nbsp {<br>
     * &nbsp&nbsp&nbsp&nbsp "id" : number,<br>
     * &nbsp&nbsp&nbsp&nbsp "content" : "string",<br>
     * &nbsp&nbsp&nbsp&nbsp "author" : "string"<br>
     * &nbsp&nbsp },<br>
     * &nbsp&nbsp ... <br>
     * ]<br>
     * @param str total json text
     * @param array will save into
     */
    public void json2wiseSayings(String str, WiseSayingList array){
        setString(str);
        findChar('[');
        findChar('{');
        do {
            WiseSaying wiseSaying = readWiseSaying();
            array.add(wiseSaying);
        } while (findChar(','));
        findChar(']');
    }
}
