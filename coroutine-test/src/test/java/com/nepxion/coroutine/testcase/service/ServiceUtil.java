package com.nepxion.coroutine.testcase.service;

/**
 * <p>Title: Nepxion Coroutine</p>
 * <p>Description: Nepxion Coroutine For Distribution</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.coroutine.data.entity.CoroutineEntry;
import com.nepxion.coroutine.data.entity.CoroutineList;

public class ServiceUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceUtil.class);
    private static final boolean LOG_PRINT = false;

    public static String doThen(String value, String service) {
        value += " -> " + service + getSuffix(value);

        if (LOG_PRINT) {
            LOG.info("串行返回结果：{}", value);
        }

        return value;
    }

    public static String doWhen(String value, String service) {
        value += " -> " + service + getSuffix(value);

        if (LOG_PRINT) {
            LOG.info("并行返回结果：{}", value);
        }

        return value;
    }

    public static String doMerge(CoroutineList<Object> value, String service) {
        StringBuilder bulder = new StringBuilder();

        if (LOG_PRINT) {
            LOG.info("并行汇聚结果：------------------------------");
        }

        List<CoroutineEntry<Object>> list = value.getCoroutineEntryList();
        int i = 0;
        for (CoroutineEntry<Object> entry : list) {
            Object result = entry.getResult();

            if (LOG_PRINT) {
                LOG.info("{}", entry);
            }

            if (i == list.size() - 1) {
                bulder.append(result + " -> " + service + getSuffix(result.toString()));
            } else {
                bulder.append(result + " -> " + service + getSuffix(result.toString()) + " , ");
            }
            i++;
        }

        if (LOG_PRINT) {
            LOG.info("-------------------------------------------");
        }

        return "(" + bulder.toString() + ")";
    }

    public static String doMerge(List<Map<String, Object>> value, String service) {
        StringBuilder bulder = new StringBuilder();

        if (LOG_PRINT) {
            LOG.info("并行汇聚结果：------------------------------");
        }
        for (int i = 0; i < value.size(); i++) {
            Map<String, Object> entry = value.get(i);
            Object result = entry.get("result");

            if (LOG_PRINT) {
                LOG.info("{}", entry);
            }

            if (i == value.size() - 1) {
                bulder.append(result + " -> " + service + getSuffix(result.toString()));
            } else {
                bulder.append(result + " -> " + service + getSuffix(result.toString()) + " , ");
            }
        }
        if (LOG_PRINT) {
            LOG.info("-------------------------------------------");
        }

        return "(" + bulder.toString() + ")";
    }

    public static String getSuffix(String value) {
        return value.substring(value.lastIndexOf("["), value.length());
    }
}