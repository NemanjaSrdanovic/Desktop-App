package model;

import composite.AccountCompositeCollection;
import composite.CategoryCompositeCollection;
import composite.TransactionCompositeCollection;
import database.DriverClass;
import enums.AccountType;
import enums.CategoryType;
import factory.AccountFactory;
import factory.CategoryFactory;
import observer.Observer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;



public class InsertDataClass {

    DriverClass database = new DriverClass();


    public void insertFakeData(){

        String[] userName={"Jelena" , "Nemanja" , "Veljko", "Marija"};
        String[] password={"Samsung" , "Huawei" , "Apple" , "Lenovo", "Microsoft", "Acer", "HP", "Sony", "LG", "Asus" };
        int ArtNr=0;

//Data Account
        String[] accountName={"Erste" , "BankAustria" , "SparKonto", "MyAccount", "ErsteBank", "MyAccount2"};
        int[] accountBalance={1000, 50, 3000, 200, 1000, 5};
        AccountType[] accountType={AccountType.BANK, AccountType.CARD, AccountType.CARD, AccountType.CASH, AccountType.STOCK, AccountType.BANK};
        int[] overdraft={100, 500, 0, 200, 0, 10};
        String[] username={"testUser1", "testUser2"};

// Data Category
        String[] categoryName1={"Education" , "Food", "Shopping", "Billa","Transportation", "Spar"};
        String[] categoryName2={"Salary" , "Guthaben" , "Job Bonus", "Nobel Prise", "Dividend", "Extra Geld"};
        CategoryType[] categoryType1={CategoryType.EXPENSE};
        CategoryType[] categoryType2={CategoryType.INCOME};

// Data Transaction
        String[] transactionDescription={"SalaryAdded" , "Guthaben2" , "Test Transaction", "balance"};
        int[] transactionAmount={10,50,100,30,100,20};

        //insert User
        try {
            database.insertUserTable("admin", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //insert Acount
        for(int i=0; i<5; i++) {
            try {
                database.insertAccountTable(accountName[i], accountBalance[i], accountType[i], overdraft[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //insert Category
        for(int i=0; i<5; i++) {
            try {
                database.insertCategoryTable(categoryType1[0], categoryName1[i]);
                database.insertCategoryTable(categoryType2[0], categoryName2[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //insertTransaction

        int counter = 0;
        int transactionId = 0;
        for(int month=0; month<3; month++) {
            for(int day = 0; day < 3; day++)
                try {

                    if(counter >= 4) counter = 0;

                    String year = "2019";
                    String dateString =   Integer.toString(day) + "-" +  Integer.toString(month) +"-"+year;
                    SimpleDateFormat sm = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = sm.parse(dateString);
                    final java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    AccountFactory account = new AccountFactory();
                    CategoryFactory category = new CategoryFactory();

                    Account accountFinished = account.makeAccount(accountType[counter], accountName[counter], accountBalance[counter]);
                    Category categoryFinished1 = category.makeCategory(categoryType1[0], categoryName1[counter]);



                    Transaction transaction = new Transaction(transactionId, accountFinished, sqlDate, transactionAmount[counter], categoryFinished1, transactionDescription[counter]);

                    transactionId++;
                    counter++;

                    database.insertTransactionTable(transaction);
                    database.updateAccountTable(transaction,accountFinished);
                    database.updateCategoryTable(transaction,accountFinished);


                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
        }
        int counter2 = 0;
        int transactionId2 = 9;
        for(int month=0; month<3; month++) {
            for(int day = 0; day < 2; day++)
                try {

                    if(counter2 >= 4) counter2 = 0;

                    String year = "2019";
                    String dateString =   Integer.toString(day) + "-" +  Integer.toString(month) +"-"+year;
                    SimpleDateFormat sm = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = sm.parse(dateString);
                    final java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    AccountFactory account = new AccountFactory();
                    CategoryFactory category = new CategoryFactory();

                    Account accountFinished = account.makeAccount(accountType[counter2], accountName[counter2], accountBalance[counter2]);
                    Category categoryFinished2 = category.makeCategory(categoryType2[0], categoryName2[counter2]);



                    Transaction transaction2 = new Transaction(transactionId2, accountFinished, sqlDate, transactionAmount[counter2], categoryFinished2, transactionDescription[counter2]);

                    transactionId2++;
                    counter2++;

                    database.insertTransactionTable(transaction2);
                    database.updateAccountTable(transaction2,accountFinished);
                    database.updateCategoryTable(transaction2,accountFinished);

                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
        }

        database.closeDbConnection();

    }

}