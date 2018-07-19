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
            // ������������
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // ����CSV������ ����:CsvReader(�ļ�·�����ָ����������ʽ);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
            // ������ͷ �����Ҫ��ͷ�Ļ��������Ժ���
            reader.readHeaders();
            // ���ж������ͷ������
            while (reader.readRecord()) {
                partyMeis.add(reader.getValues()[6]);
            }
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
