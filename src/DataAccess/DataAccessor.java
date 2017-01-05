package DataAccess;

/* Created by AMXPC on 2017/1/5. */

import org.apache.commons.collections.functors.WhileClosure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DataAccessor {
    private ArrayList<String> OracleCardidArray;
    private ArrayList<String> OraclePwdArray;
    private ArrayList<String> TimesTenCardidArray;
    private ArrayList<String> TimesTenPwdArray;
    private static DataAccessor dataAccessor;

    private DataAccessor() {
        initArrays();
    }

    public static void init() {
        dataAccessor = new DataAccessor();
    }

    private void initArrays() {
        OracleCardidArray = new ArrayList<>();
        OraclePwdArray = new ArrayList<>();
        TimesTenCardidArray = new ArrayList<>();
        TimesTenPwdArray = new ArrayList<>();

        System.out.println("Ready To Open Files");
        File file1 = new File("Data/oracardid.txt");
        File file2 = new File("Data/orapwd.txt");
        File file3 = new File("Data/ttcardid.txt");
        File file4 = new File("Data/ttpwd.txt");
        System.out.println("Open Files Finished");

        try {
            System.out.println("Ready To Read Files To Buffer");
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            BufferedReader br3 = new BufferedReader(new FileReader(file3));
            BufferedReader br4 = new BufferedReader(new FileReader(file4));
            System.out.println("Read Files To Buffer Finished");

            System.out.println("Ready To Read Files To Arrays");
            String tmp;
            while ((tmp = br1.readLine()) != null) {
                OracleCardidArray.add(tmp);
            }
            System.out.println("Oracle Cardid Read");
            while ((tmp = br2.readLine()) != null) {
                OraclePwdArray.add(tmp);
            }
            System.out.println("Oracle Password Read");
            while ((tmp = br3.readLine()) != null) {
                TimesTenCardidArray.add(tmp);
            }
            System.out.println("TimesTen Cardid Read");
            while ((tmp = br4.readLine()) != null) {
                TimesTenPwdArray.add(tmp);
            }
            System.out.println("TimesTen Password Read");
            System.out.println("Read Files To Arrays Finished");

        } catch (Exception e) {
            System.out.println("Read File Failed");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getOracleCardidArray() {
        return OracleCardidArray;
    }

    public ArrayList<String> getOraclePwdArray() {
        return OraclePwdArray;
    }

    public ArrayList<String> getTimesTenCardidArray() {
        return TimesTenCardidArray;
    }

    public ArrayList<String> getTimesTenPwdArray() {
        return TimesTenPwdArray;
    }

    public static DataAccessor getDataAccessor() {
        return dataAccessor;
    }
}
