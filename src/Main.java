/**
 * Databases HW 1
 * Asher Mangel
 * 
 *  1: Read data and display on screen ordered by product
 * 
 * 2: Design methods to query CSV file tables:
 *  2.1: Count and display the number of Amandas
 *  2.2: Calculate and display the average transaction amount
 *  2.3: Create a new CSV that changes all intances of "United States" to USA. Print the amount of changes made.
 */


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        File file1 = new File("data\\cs340_hw01_salesData01.csv");
        sortByProduct(file1);
        System.out.println("There are " + countNames(file1, "amanda") + " customers named Amanda");
        System.out.println("The average transaction amount is " + averageAmount(file1));
        convertText(file1, "United States", "USA");
    }

    public static void sortByProduct(File file) throws FileNotFoundException, IOException {
        int column = 2;
        int products = 3;

        FileWriter writer = new FileWriter("data\\salesDataByProductNum.csv");
        Scanner headerScanner = new Scanner(file);
        writer.append(headerScanner.nextLine());
        headerScanner.close();
        
        for (int i = 0; i <= products; i++) {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            scanner.nextLine();
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                
                String productString = values[column-1];
                String productNumChar = String.valueOf(productString.charAt(7));
                int productNum = Integer.parseInt(productNumChar);
                if (productNum == i){
                    writer.append("\n"+line);
                }
            }
            scanner.close();
        }
        writer.close();
        

        Scanner newFileScanner = new Scanner(new File("data\\salesDataByProductNum.csv"));
        newFileScanner.useDelimiter(",");
        
        while(newFileScanner.hasNextLine()){
            System.out.println(newFileScanner.nextLine()+"\t");
        }
        newFileScanner.close();
    }

    public static int countNames(File file, String name) throws FileNotFoundException{
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");

        int occurences = 0;

        while(scanner.hasNext())
        {
            if(scanner.next().toLowerCase().contains(name.toLowerCase())){
                occurences++;
            }
        }
        scanner.close();
        return occurences;
    }
    
    public static float averageAmount(File file) throws FileNotFoundException{
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        int column = 3;

        int transactions = 0;
        float totalSpent = 0;

        scanner.nextLine();

        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            String[] values = line.split(",");
            
            if (values[column-1]!=""){
                transactions++;
                totalSpent+=Float.parseFloat(values[column-1]);
            }
        }
        scanner.close();
        return totalSpent/transactions;
    }
    
    public static void convertText(File file, String initial, String edit) throws FileNotFoundException, IOException {
        FileWriter writer = new FileWriter("data\\edited.csv");
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        
        int changes = 0;
        
        while (scanner.hasNext()) {
            String next = scanner.next();
            if(next.equals(initial)) {
                writer.append(edit+",");
                changes ++;
            } else {
                writer.append(next+",");
            }
        }
        scanner.close();
        writer.close();
        
        System.out.println("Changes made: " + changes);
    }
}