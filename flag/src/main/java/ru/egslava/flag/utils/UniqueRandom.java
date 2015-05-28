package ru.egslava.flag.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mak on 17.05.2015.
 */
public class UniqueRandom {
    private ArrayList<Integer> data = new ArrayList<>();
    private Random rand = new Random();
    public UniqueRandom(int min, int max){
        data.clear();
        for(int i = min; i < max; i++){
            data.add(i);
        }
    }

    public int next(){
        if(data.size() == 0)
            return 0;
        int r = rand.nextInt(data.size());
        int res = data.get(r);
        data.remove(r);
        return res;
    }
}
