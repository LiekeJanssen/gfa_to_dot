package gfa;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author ljanssen
 */
public class GFA {

    public static void main(String[] args) throws IOException {
        //System.out.println(args[0]);
        
        
        int k = Integer.parseInt(args[0]);      //give kmer size as user input
        int dd = k-1;                           //dd is k-1 (overlap)
        String d = Integer.toString(dd);
        
        BufferedReader br = new BufferedReader(new FileReader(args[1]));    //file in userinput
        String line = null;
        line = br.readLine();                       //read file and split on tabs, put in array
        String[] cols1 = line.split("\t");
        String recordType = cols1[0];
        String version = cols1[1];

        
        if (recordType.equals("H")){
            
            if (version.equals("VN:Z:1.0")){                //if version is this go on
                System.out.println("digraph adj {"+"\n"+"graph [k="+k+"]"+"\n"+"edge [d=-"+d+"]");      //create header
                while ((line = br.readLine()) !=null) {
                    String cols[] = line.split("\t");                   //when line is not empty split file on tabs and store in String cols

                    recordType = cols[0];
                    if (recordType.equals("S")) {                       //if Type is S go on

                        String id = cols[1];

                        String lengthwith = cols[3];
                        String length = lengthwith.replaceAll("[^0-9]", "");
                        String kmerwith = cols[4];
                        String kmer = kmerwith.replaceAll("[^0-9]", "");            //extract only numbers from string



                        System.out.println(('"'+id+"+"+'"')+" "+"[l="+length+" "+"C="+kmer+"]"+"\n"
                                +('"'+id+"-"+'"')+" "+"[l="+length+" "+"C="+kmer+"]");                      //hardcode file format

                    }
                    else if (recordType.equals("L")) {                                      //when in first column is an L go on
                        String id2 = cols[1];
                        String orientation = cols[2];
                        String start = cols[3];
                        String orientation2 = cols[4];
                        String overlapwith = cols[5];
                        String overlap = overlapwith.replaceAll("[^0-9]", "");


                        if(overlap.equals(d)){
                            System.out.println(('"'+id2+orientation+'"')+" "+"-> "+('"'+start+orientation2+'"')+" "+"\n"
                            +'"'+start+"-"+'"'+" -> "+'"'+id2+"-"+'"');
                        }
                        else {
                            System.out.println(('"'+id2+orientation+'"')+" "+"-> "+('"'+start+orientation2+'"')+" "+"[d=-"+overlap+"]"+"\n"
                            +'"'+start+"-"+'"'+" -> "+'"'+id2+"-"+'"'+"[d=-"+overlap+"]");                                                          //hardcode file
                        }
                    }
                }
                System.out.println("}");
            }
            else{                                                                                                               //do the same for version 2 with slight differences
                if (version.equals("VN:Z:2.0")){
                    System.out.println("digraph adj {"+"\n"+"graph [k="+k+"]"+"\n"+"edge [d=-"+d+"]");
                    while ((line = br.readLine()) !=null) {
                        String cols[] = line.split("\t");
                        recordType = cols[0];

                        if (recordType.equals("S")){
                            String id1 = cols[1];
                            String length2 = cols[2];
                            String kmerwith2 = cols[4];
                            String kmer2 = kmerwith2.replaceAll("[^0-9]", "");

                            System.out.println('"'+id1+"+"+'"'+" "+"[l="+length2+" "+"C="+kmer2+"]"+"\n"
                                               +'"'+id1+"-"+'"'+" "+"[l="+length2+" "+"C="+kmer2+"]");


                        }
                        else if (recordType.equals("E")){
                            String id2 = cols[2].replaceAll("[^0-9]", "");
                            String id2orientation = cols[2].replaceAll("[^+-]", "");
                            
                            
                            String id2orientationswitched = "";
                            if (id2orientation.equals("+")){
                                id2orientationswitched = "-";
                            }
                            else {
                                id2orientationswitched = "+";
                            }
                            
                            
                            String id3 = cols[3].replaceAll("[^0-9]", "");
                            String id3orientation = cols[3].replaceAll("[^+-]", "");
                            
                            String id3orientationswitched = "";
                            if (id3orientation.equals("+")){
                                id3orientationswitched = "-";
                            }
                            else{
                                id3orientationswitched = "+";
                            }
                            
                            String overlapwith2 = cols[8];
                            String overlap2 = overlapwith2.replaceAll("[^0-9]", "");

                            if (overlap2.equals(d)){
                                System.out.println('"'+id2+id2orientation+'"'+" -> "+'"'+id3+id3orientation+'"'+"\n"
                                +'"'+id3+id3orientationswitched+'"'+" ->"+" "+'"'+id2+id2orientationswitched+'"');
                            }
                            else{
                                System.out.println('"'+id2+id2orientation+'"'+" -> "+'"'+id3+id3orientation+'"'+" [d=-"+overlap2+"]"+"\n"
                                +'"'+id3+id3orientationswitched+'"'+" -> "+'"'+id2+id2orientationswitched+'"'+" [d=-"+overlap2+"]");
                            }

                        }
                    }
                }
                System.out.println("}");
            }
        }
        else {
            System.out.println("Wrong document, please choose another one");            //If wrong document give notice
        }
    }
    
}
