package com.back.step14.repository;

import com.back.step14.WiseSaying;

public class WiseSayingDatabase {
    FileManager fileManager;
    WiseSayingList wiseSayingList;
    private final int PAGE_SIZE = 5;

    public WiseSayingDatabase() {
        wiseSayingList = new WiseSayingList();
        fileManager = new FileManager();
    }

    public void close(){
        int lastId = wiseSayingList.getLastId();
        fileManager.saveLastId(lastId);
    }

    public int insertWiseSaying(WiseSaying wiseSaying){
        wiseSayingList.add(wiseSaying);
        fileManager.saveWiseSayingAsJson(wiseSaying);
        return wiseSaying.getId();
    }

    public WiseSayingView getWiseSayingList(int page){
        WiseSayingList.WiseSayingIterator iterator = wiseSayingList.pagedIterator(page);
        int total_count = wiseSayingList.getSize();
        WiseSayingView view = new WiseSayingView(iterator, page,(total_count-1)/5+1);
        return view;
    }

    public void deleteWiseSaying(int id){
        wiseSayingList.removeWiseSayingById(id);
        fileManager.deleteWiseSayingJson(id);
    }

    public void updateWiseSaying(WiseSaying wiseSaying){
        WiseSaying db_wise_saying = wiseSayingList.getWiseSayingById(wiseSaying.getId());
        db_wise_saying.setData(wiseSaying);
        fileManager.saveWiseSayingAsJson(db_wise_saying);
    }

    public void buildWiseSayingList(){
        fileManager.saveWiseSayingsAsOneJson(wiseSayingList.iterator());
    }

    public void loadWiseSayingList(){
        wiseSayingList.clear();
        fileManager.loadJsonsAsWiseSayings(wiseSayingList);
    }

    public WiseSaying getWiseSaying(int id){
        return wiseSayingList.getWiseSayingById(id);
    }

    public WiseSayingView searchWiseSayingWithContent(String keyword, int page){
        WiseSayingList view = new WiseSayingList();
        WiseSayingList.WiseSayingIterator iterator = wiseSayingList.iterator();
        int start_index = (page-1)*PAGE_SIZE;
        int count = 0;
        while(iterator.hasNext()){
            WiseSaying wiseSaying = iterator.next();
            boolean found = wiseSaying.getContent().contains(keyword);
            if (found){
                if (start_index <= count && count < start_index + wiseSayingList.PAGE_SIZE){}
                view.add(wiseSaying);
                count++;
            }

        }
        int max_page = (view.getSize()-1)/ PAGE_SIZE + 1;
        return new WiseSayingView(view.iterator(),page ,max_page);
    }

    public WiseSayingView searchWiseSayingWithAuthor(String author, int page){
        WiseSayingList view = new WiseSayingList();
        WiseSayingList.WiseSayingIterator iterator = wiseSayingList.iterator();
        int start_index = (page-1)*PAGE_SIZE;
        int count = 0;
        while(iterator.hasNext()){
            WiseSaying wiseSaying = iterator.next();
            boolean found = wiseSaying.getAuthor().contains(author);
            if (found){
                if (start_index <= count && count < start_index + wiseSayingList.PAGE_SIZE){
                    view.add(wiseSaying);
                }
                count++;
            }

        }
        int max_page = (view.getSize()-1)/ PAGE_SIZE + 1;
        return new WiseSayingView(view.iterator(), page, max_page);
    }

}
