package cn.lin.luo.tian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
/ foolish
/**
public class AddParties {
    private static final String GET_PARTY_BY_MEI_URL = "http://nj4mcldvapp003.markit.partners:5083/dcl/MarkitClear.getPartyListByPartyMeiNamePrefix";
    private static final String ADD_PARTY_URL        = "http://nj4mcldvapp003.markit.partners:5083/dcl/MarkitClear.saveParty";
    
    public static void main(String[] args) {
        System.out.println(isPartyExist("CH1L122575"));
//        CSVParser.getPartyMeis().stream().filter(mei -> !isPartyExist(mei))
//                .forEach(AddParties::addParty);
    }
    
    private static boolean isPartyExist(String partyMei) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("keyword", partyMei));
        formparams.add(new BasicNameValuePair("authenticationToken",
                "markittest_support@ihsmarkit.com"));
        JSONObject jsonObj = postRequest(formparams, GET_PARTY_BY_MEI_URL);
        JSONArray data = (JSONArray) jsonObj.get("data");
        if (null != data && data.size() > 0) {
            for (Object object : data) {
                JSONObject party = (JSONObject) object;
                if (partyMei.equals(party.get("partyMei"))) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static JSONObject postRequest(List<NameValuePair> formparams,
            String url) {
        
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        
        HttpClient httpClient = new DefaultHttpClient();
        
        HttpResponse response = null;
        try {
            response = httpClient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        HttpEntity httpEntity = response.getEntity();
        
        if (httpEntity != null) {
            try {
                String result = EntityUtils.toString(httpEntity);
                System.out.println(result);
                if (null != result) {
                    return JSON.parseObject(result);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    private static void addParty(String partyMei) {
        System.out.println("Add party : " + partyMei);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams
                .add(new BasicNameValuePair("party.globalAccountId", "58042"));
        formparams.add(new BasicNameValuePair("party.partyMei", partyMei));
        formparams.add(new BasicNameValuePair("party.partyLongName", partyMei));
        formparams.add(
                new BasicNameValuePair("party.partyAddress.country", "AF"));
        formparams
                .add(new BasicNameValuePair("party.partyShortName", partyMei));
        formparams.add(new BasicNameValuePair("action", "add"));
        formparams.add(new BasicNameValuePair("party.markitClearMember", "0"));
        formparams.add(new BasicNameValuePair("party.status", "Enabled"));
        formparams.add(new BasicNameValuePair("authenticationToken",
                "markittest_support@ihsmarkit.com"));
        JSONObject postRequest = postRequest(formparams, ADD_PARTY_URL);
    }
    
}
