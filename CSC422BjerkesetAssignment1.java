//Elisha Bjerkeset
//Assignment 1 Part 2
//07/05/2025

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSC422BjerkesetAssignment1 {
    public static void main(String[] args) {
        //Setting up variables
        Boolean wantToPlay = true;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pet> petData = new ArrayList<Pet>();
        System.out.println("Pet Database Program.");

        //Reading information from file
        String filePath = "pets.txt";
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] petInfo = line.split(" ");
                petData.add(new Pet(petInfo[0], Integer.valueOf(petInfo[1])));
                System.out.println("Pet added.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        //Main loop for selcting input
        while(wantToPlay) {
            System.out.println("\nWhat would you like to do?\r\n" + //
                                "1) View all pets\r\n" + //
                                "2) Add more pets\r\n" + //
                                "3) Update an existing pet\r\n" + //
                                "4) Remove an existing pet\r\n" + //
                                "5) Search pets by name\r\n" + //
                                "6) Search pets by age\r\n" + //
                                "7) Exit program\nYour choice: ");
            Integer userInput = scanner.nextInt();
            scanner.nextLine();
            System.out.println("");

            //Main switch statement for user input
            switch(userInput) {
                //Calls the printTable function
                case 1:
                    printTable(petData);
                    break;

                //For adding pets to arraylist
                case 2:
                    Boolean wantToAdd = true;
                    Integer petAddedCount = 0;

                    //While loop to allow for adding of multiple pets
                    while(wantToAdd) {
                            //Getting user input
                            System.out.print("add pet (name, age): ");
                            String petInput = scanner.nextLine();
                            String[] petInfo = petInput.split(" ");

                            //If the user wants to exit
                            if("done".equals(petInfo[0])) {
                                switch(petAddedCount) {
                                    case 0:
                                        System.out.println("no pets added.");
                                        break;
                                    case 1:
                                        System.out.println("1 pet added.");
                                        break;
                                    default:
                                        System.out.println(petAddedCount + " pets added.");
                                        break;
                                }
                                wantToAdd = false;
                            }

                            //Error checking before adding the input to the database
                            else {
                                if(petData.size() == 5) {
                                    System.out.println("Error: Database is full.");
                                }
                                else if(petInfo.length != 2) {
                                    System.out.println("Error: " + petInput + "is not a valid input.");
                                }
                                else if(Integer.valueOf(petInfo[1]) > 20 || Integer.valueOf(petInfo[1]) <= 0) {
                                    System.out.println("Error: " + Integer.valueOf(petInfo[1]) + " is not a valid age.");
                                }
                                else {
                                    petData.add(new Pet(petInfo[0], Integer.valueOf(petInfo[1])));
                                    petAddedCount += 1;
                                }
                            } 
                    }
                    break;

                    //Logic for selecting an index and then changing the arraylist's information
                    case 3:
                        printTable(petData);
                        System.out.print("Enter the pet ID you can to update:");
                        Integer updateID = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new name and new age: ");
                        String updatedPet = scanner.nextLine();
                        String[] petInfo = updatedPet.split(" ");
                        System.out.println(petData.get(updateID).getName() + " " + petData.get(updateID).getAge() + " changed to " + updatedPet);
                        petData.get(updateID).setName(petInfo[0]);
                        petData.get(updateID).setAge(Integer.valueOf(petInfo[1]));
                    break;

                    //Removing selected index with error handling
                    case 4:
                        printTable(petData);
                        System.out.print("Enter the pet ID to remove: ");
                        Integer deleteNum = scanner.nextInt();
                        scanner.nextLine();
                        if(deleteNum >= 0 && deleteNum <= petData.size()) {
                            System.out.println(petData.get(deleteNum).getName() + " " + petData.get(deleteNum).getAge() + " is removed. ");
                            petData.remove((int) deleteNum);
                        }
                        else {
                            System.out.println("Error: ID -1 does not exist.");
                        } 
                    break;

                    //Searching through array list by comparing the names to user input
                    case 5:
                        System.out.print("Enter a name to search: ");
                        ArrayList<Pet> petNameSearch = new ArrayList<Pet>();
                        String nameSearch = scanner.nextLine();
                        for(int i = 0; i < petData.size(); i++) {
                            if(nameSearch.toLowerCase().equals(petData.get(i).getName().toLowerCase())) {
                                petNameSearch.add(petData.get(i));
                            }
                        }
                        printTable(petNameSearch);
                    break;

                    //Comparing arraylist values with user input
                    case 6:
                        System.out.print("Enter an age to search: ");
                        ArrayList<Pet> petAgeSearch = new ArrayList<Pet>();
                        Integer ageSearch = scanner.nextInt();
                        scanner.nextLine();
                        for(int i = 0; i < petData.size(); i++) {
                            if(ageSearch == petData.get(i).getAge()) {
                                petAgeSearch.add(petData.get(i));
                            }
                        }
                        printTable(petAgeSearch);
                    break;
                    case 7:
                        System.out.println("Goodbye!");
                        wantToPlay = false;
            }
        }

        //Trying to write the pet object's info to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pets.txt"))) {
            for(Pet pet : petData) {
                writer.write(pet.getName() + " " + pet.getAge());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        System.out.println("Successfully wrote to file.");
        scanner.close();
    }

    //Function for printing th table with printf
    public static void printTable(ArrayList<Pet> pets) {
        System.out.println("+----------------------+");
        System.out.println("| ID | NAME      | AGE |");
        System.out.println("+----------------------+");
        for(int i = 0; i < pets.size(); i++) {
            System.out.printf("| %2d | %-9s | %3d |%n", i, pets.get(i).getName(), pets.get(i).getAge());
        }
        System.out.println("+----------------------+");
        System.out.println(pets.size() + " rows in set.");
    }

    //Object for storing information
    public static class Pet {
        private String name;
        private Integer age;

        public Pet(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
