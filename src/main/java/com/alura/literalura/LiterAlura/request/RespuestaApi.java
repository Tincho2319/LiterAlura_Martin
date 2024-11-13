package com.alura.literalura.LiterAlura.request;

import com.alura.literalura.LiterAlura.modelo.LibrosDTO;

import java.util.List;

public class RespuestaApi {
    private int count;
    private String next;
    private String previous;
    private List<LibrosDTO> results;

    // Getters y setters

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<LibrosDTO> getResults() {
        return results;
    }

    public void setResults(List<LibrosDTO> results) {
        this.results = results;
    }
}
