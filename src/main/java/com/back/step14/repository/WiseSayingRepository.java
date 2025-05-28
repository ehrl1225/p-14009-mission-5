package com.back.step14.repository;

import com.back.step14.WiseSaying;

public class WiseSayingRepository {
    WiseSayingDatabase database;

    public WiseSayingRepository() {
        database = new WiseSayingDatabase();
        database.loadWiseSayingList();
    }

    public void close(){
        database.close();
    }

    public int insertWiseSaying(WiseSaying wiseSaying) {
        return database.insertWiseSaying(wiseSaying);
    }

    public WiseSayingView showWiseSaying(int page) {
        return database.getWiseSayingList(page);
    }

    public void deleteWiseSaying(int id) {
        database.deleteWiseSaying(id);
    }

    public void updateWiseSaying(WiseSaying wiseSaying) {
        database.updateWiseSaying(wiseSaying);
    }

    public void buildWiseSayingList() {
        database.buildWiseSayingList();
    }

    public WiseSaying getWiseSaying(int id) {
        return database.getWiseSaying(id);
    }

    public boolean hasWiseSaying(int id) {
        return database.getWiseSaying(id) != null;
    }

    public WiseSayingView searchWiseSayingList(String keywordType, String keyword, int page) {
        return switch (keywordType){
            case "content" -> database.searchWiseSayingWithContent(keyword, page);
            case "author" -> database.searchWiseSayingWithAuthor(keyword, page);
            default -> null;
        };
    }

}
