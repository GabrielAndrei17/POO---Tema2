package com.company;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;



public class Main {

    public static void main(String[] args) {
        //Citirea cuvintelor

        Gson jsn = new Gson();
        Dictionary dict = new Dictionary();

        File directoryPath = new File(".");
        File filesList[] = directoryPath.listFiles(); //toate fisierele
        Word[] auxW = null;
        for(File file : filesList)
            try {
                if (Pattern.matches(".*json", file.getName())) {
                    BufferedReader br = new BufferedReader(new FileReader(file.getName()));
                    String line = br.readLine();
                    String fileString = line;
                    while ((line = br.readLine()) != null) {
                        fileString = fileString + line;
                    }

                    auxW = jsn.fromJson(fileString, Word[].class);

                    String[] lang = file.getName().split("_");  //limba din care sunt cuvintele

                    for (Word aux : auxW)
                        if (aux != null)
                            dict.addWord(aux, lang[0]);
                }
            }catch (IOException e){
                System.err.println(e.toString());
                return;
            }
        //Testarea metodelor

        Word word = new Word("mare", "big", "noun", "mare", "mari", "ro");
        System.out.println("S-a adaugat cuvantul " + word.word + " : " + dict.addWord(word, "ro"));

        Word word2 = new Word("mare", "big", "noun", "mare", "mari", "ro");
        System.out.println("S-a adaugat cuvantul " + word2.word + " : " + dict.addWord(word2, "ro"));


        System.out.println("Stergere cuvant 'elle' : " + dict.removeWord("elle", "fr"));
        System.out.println("Stergere cuvant 'merge' : " + dict.removeWord("merge", "ro"));

        Definition def = new Definition("Test", "Test", 19999, "Test");
        System.out.println("Adaugarea noii definitii : " + dict.addDefinitionForWord("jeu", "fr", def));
        System.out.println("Adaugarea aceasi definitii : " + dict.addDefinitionForWord("jeu", "fr", def));

        System.out.println("Stergerea definitiei : " + dict.removeDefinition("jeu", "fr", def));

        System.out.println("Traducerea cuvantului 'chat' in romana : " + dict.translateWord("chat", "fr", "ro"));
        System.out.println("Traducerea propozitiei 'pisica mare' in franceza : " + dict.translateSentence(
                "pisică mare", "ro", "fr"));
        System.out.println("Traducerea propozitiei 'grand chat' in romana : " + dict.translateSentence(
                "grand chat", "fr", "ro"));

        System.out.println("Mai multe traduceri a propozitiei 'grand chat' in romana : " + dict.translateSentences(
                "grand chat", "fr", "ro"));
        System.out.println("Mai multe traduceri a propozitiei 'pisica mare' in franceza : " + dict.translateSentences(
                "pisică mare", "ro", "fr"));

        //System.out.println(dict);

        System.out.println("Definitiile cuvantului 'câine' ordonate crescator :\n" + dict.getDefinitionsForWord("câine", "ro"));

        //Exportarea cuvintelor

        try {
            dict.exportDictionary("ro");
        }catch (IOException e){
            System.out.println(e.toString());
        }

    }

}
