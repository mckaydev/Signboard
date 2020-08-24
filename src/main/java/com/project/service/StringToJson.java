package com.project.service;

import org.springframework.stereotype.Service;

@Service
public class StringToJson {
    public StringBuffer stringToJson(String temp) {
//        String temp1 = temp.substring(1, temp.length() - 1);
        StringBuffer sf = new StringBuffer(temp);

        for (int i = 0; i < sf.length(); i++) {
            if (sf.charAt(i) == '{') {
                sf.insert(i + 1, '"');
                i++;
            } else if (sf.charAt(i) == '=') {
                if (sf.charAt(i + 1) == '{') {
                    sf.setCharAt(i, ':');
                    sf.insert(i, '"');
                    i++;
                }
            } else if (sf.charAt(i) == ',' && sf.charAt(i + 1) == ' ') {
                if (sf.charAt(i - 1) == '}') {
                    sf.insert(i + 2, '"');
                    i += 2;
                } else {
                    sf.insert(i, '"');
                    sf.insert(i + 3, '"');
                    i += 3;
                }
            } else if (sf.charAt(i) == '}' && sf.charAt(i - 1) != '}') {
                sf.insert(i, '"');
                i++;
            }
        }

        for (int j = 0; j < sf.length(); j++) {
            if (sf.charAt(j) == '"') {
                if (sf.charAt(j + 1) != 'h' || sf.charAt(j + 2) != 't'
                        || sf.charAt(j + 3) != 't' || sf.charAt(j + 4) != 'p') {
                    int k = 0;
                    for (k = j + 1; sf.charAt(k) != '"' && k < sf.length() - 1; k++) {
                        if (sf.charAt(k) == '=') {
                            sf.setCharAt(k, ':');
                            sf.insert(k, '"');
                            sf.insert(k + 2, '"');
                            break;
                        }
                    }
                    j = k;
                } else {
                    int l = j + 1;
                    while (sf.charAt(l) != '"') {
                        l++;
                    }
                    j = l;
                }
            }
        }

        return sf;
    }
}
