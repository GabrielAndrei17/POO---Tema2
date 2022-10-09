package com.company;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Dictionary{
    //Clasa Dictionary cu metodele specifice structurii hashMap

    Map<Integer, Word> wordMap;


    public Dictionary() {
        this.wordMap = new HashMap<Integer, Word>();
    }

    public boolean addWord(Word word, String language){

        word.lang = language;
        if(wordMap.containsKey(word.mapCode()))
            return false;

        wordMap.put(word.mapCode(), word);
        return true;
    }
    public boolean removeWord(String word, String language){
        Word wordToBeDeleted = new Word(word);
        wordToBeDeleted.lang = language;

        return wordMap.remove(wordToBeDeleted.mapCode()) != null;
    }

    boolean addDefinitionForWord(String word, String language, Definition definition){
        Word value = wordMap.get(Objects.hash(word, language));

        if(value != null) {

                if(!value.addDefinition(definition))
                    return false;

                return true;
        }
        return false;
    }
    public boolean removeDefinition(String word, String language, Definition dictionary){
        Word value = wordMap.get(Objects.hash(word, language));

        if(value != null) {
                if(!value.deleteDefinition(dictionary))
                    return false;

                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String st = "[\n";
        for (Map.Entry<Integer, Word> entry : wordMap.entrySet()) {
            st += entry.getValue().toString();
        }
        st += "\n]";

        return st;
    }

    public String translateWord(String word, String fromLanguage, String toLanguage) {
        Word value = wordMap.get(Objects.hash(word, fromLanguage)); //cuvantul ce trebuie tradus
        if (value == null)
            return null;

        for (Map.Entry<Integer, Word> entry : wordMap.entrySet()) {
            if (entry.getValue().word_en.equals(value.word_en) && entry.getValue().lang.equals(toLanguage)) {
                return entry.getValue().word;
            }
        }
        return null;
    }

    public ArrayList<Definition> getDefinitionsForWord(String word, String language){
        Word value = wordMap.get(Objects.hash(word, language));
        if (value == null)
            return null;

        ArrayList<Definition> list = (ArrayList<Definition>) value.definitions.clone();
        Collections.sort(list);

        return list;
    }

    String translateSentence(String sentence, String fromLanguage, String toLanguage){
        String result = "";
        String[] words = sentence.split(" ");

        for(String word : words){
            result += translateWord(word, fromLanguage, toLanguage) + " ";
        }

        return result;
    }
    ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage){
        ArrayList<String> sentences = new ArrayList<>();
        String[] words = sentence.split(" ");

        for(int i = 0; i<=2; i++){
            String result = "";
            for(String word : words){
                Word translatedWord = (Word)wordMap.get(Objects.hash(translateWord(word, fromLanguage, toLanguage), toLanguage));
                String sinonom = translatedWord.getSinonim(i);
                if(sinonom == null)
                    sinonom = translatedWord.word;

                result += sinonom + " ";
            }
            sentences.add(result);
        }

        return sentences;
    }

    public void exportDictionary(String language) throws IOException {
        String fileName = language + "_new_dict.json";
        Gson jsn = new Gson();
        int first = 1;

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("[\n");

        for (Map.Entry<Integer, Word> entry : wordMap.entrySet())
            if(entry.getValue().lang.equals(language)){
                if(first != 1)
                    writer.write(", ");
                else
                    first = 0;

                writer.write(jsn.toJson(entry.getValue()));
            }
        writer.write("\n]");
        writer.close();
    }

}
