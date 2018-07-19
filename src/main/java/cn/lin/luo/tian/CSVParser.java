package cn.lin.luo.tian;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.csvreader.CsvReader;

public class CSVParser {
    
    private static Set<String> partyMeis = new HashSet<>();
    private static String csvFilePath = CSVParser.class.getClassLoader().getResource("Opening_Balance_Agent.csv").getPath();
    
    public static Set<String> getPartyMeis() {
        if(partyMeis.size() == 0) {
            extractMeiFromCsv(csvFilePath);
        }
        return partyMeis;
    }
    
    public static void extractMeiFromCsv(String csvFilePath) {
        try {
            // 用来保存数据
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
            // 跳过表头 如果需要表头的话，这句可以忽略
            reader.readHeaders();
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                partyMeis.add(reader.getValues()[6]);
            }
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
