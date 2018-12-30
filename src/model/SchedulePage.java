package model;

public class SchedulePage {
    private int pageNr;
    private int size;
    private int totalHits;
    private int totalPages;
    private String nextPageURL;
    private String previousPageURL;

    public SchedulePage(){

    }

    public int getPageNr() {
        return pageNr;
    }

    public void setPageNr(int pageNr) {
        this.pageNr = pageNr;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }

    public String getPreviousPageURL() {
        return previousPageURL;
    }

    public void setPreviousPageURL(String previousPageURL) {
        this.previousPageURL = previousPageURL;
    }
}
