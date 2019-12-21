package com.forte.demo.service.power.count;

import com.forte.demo.bean.power.count.Count;
import org.springframework.stereotype.Service;

public interface CountService {
    void newDay();
    void increase(Count count);
}
