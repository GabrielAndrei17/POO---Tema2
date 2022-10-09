package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Definition implements Comparable{
    String dict;
    String dictType;
    int year;

    List<String> text;

    public Definition(String dict, String dictType, int year, String text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = new ArrayList<String>();
        this.text.add(text);
    }

    @Override
    public String toString() {
        return "\n\t\t{\n\t\t\t\"dict\": \"" + dict +
                "\",\n\t\t\t\"dictType\": \"" + dictType +
                "\",\n\t\t\t\"year\": \"" + year +
                "\",\n\t\t\t\"text\": " + text +
                "\n\t\t}";
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Definition def) {
            return this.year - def.year;
        }
        return 0;
    }
}

public class Word{
    String word;
    String word_en;

    String type;
    List<String> singular;
    List<String> plural;

    ArrayList<Definition> definitions;

    String lang;

    public Word(String word){
        this.word = word;
    }
    public Word(String word, String word_en, String type, String singular, String plural, String lang) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = new ArrayList<String>();
        this.singular.add(singular);
        this.plural = new ArrayList<String>();
        this.plural.add(plural);
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "\t{\n\t\"word\": \"" + word + "\",\n\t\"word_en\": \"" +
                word_en + "\",\n\t\"type\": \"" +
                type + "\",\n\t\"singular\": " +
                singular + ",\n\t\"plural\": " +
                plural + ",\n\t\"definitions\": " +
                definitions + "\n\t}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return word.equals(word1.word) && lang.equals(word1.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word_en, lang);
    }

    public int mapCode() { //mapcodul folosit ca valoare in hashMap
        return Objects.hash(word, lang);
    }

    public boolean addDefinition(Definition def){
        definitions.add(def);
        return true;
    }
    public boolean deleteDefinition(Definition def){
        definitions.remove(def);
        return true;
    }

    public String getSinonim(int index){

        if(definitions == null)
            return null;
        Definition def = definitions.get(0);

        if(!def.dictType.equals("synonyms"))
            return null;

        if(index >= def.text.size())
            return null;

        return def.text.get(index);
    }
}
