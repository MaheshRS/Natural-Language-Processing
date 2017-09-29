package HMM;

import java.util.HashMap;

/**
 * Created by Mahesh on 15/4/17.
 */

/**
 * THIS DISPLAY FORMATTER IS WORK OF DHRUVA PENDHARKAR.
 * USING THIS OPEN-SOURCE CODE FOR DISPLAYING DATA.
 * SLIGHT MODIFICATION HAVE BEEN MADE TO THE DISPLAY TO MODIFY IT.
 */

public class Record {
    HashMap<String, String> valueMap;

    public static Record CreateRecord(){
        return new Record();
    }

    private Record(){
        this.valueMap = new HashMap<>();
    }

    public void put(String columnName, String value){
        if(columnName.length() == 0) return;
        if(value == null) return;

        this.valueMap.put(columnName, value);
    }

    public String get(String column) {
        String literal = this.valueMap.get(column);
        return literal.toString();
    }
}
