package com.lts.job.core.failstore.berkeleydb;

import com.lts.job.core.cluster.Config;
import com.lts.job.core.cluster.NodeType;
import com.lts.job.core.constant.Constants;
import com.lts.job.core.domain.Job;
import com.lts.job.core.domain.KVPair;
import com.lts.job.core.failstore.FailStore;
import com.lts.job.core.failstore.FailStoreException;
import com.lts.job.core.util.CollectionUtils;
import com.lts.job.core.util.JSONUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Robert HG (254963746@qq.com) on 5/26/15.
 */
public class BerkeleydbFailStoreTest {

    FailStore failStore;

    private String key = "x2x3423412x";

    @Before
    public void setup() throws FailStoreException {
        Config config = new Config();
        config.setNodeGroup("berkeleydb_test");
        config.setNodeType(NodeType.JOB_CLIENT);
        config.setFailStorePath(Constants.USER_HOME);
        failStore = new BerkeleydbFailStore(config);
        failStore.open();
    }

    @Test
    public void put() throws FailStoreException {
        Job job = new Job();
        job.setTaskId("2131232");
        failStore.put(key, job);
        failStore.close();
        System.out.println("这里debug测试多线程");
    }

    @Test
    public void fetchTop() throws FailStoreException {
        List<KVPair<String, Job>> kvPairs = failStore.fetchTop(5, Job.class);
        if (CollectionUtils.isNotEmpty(kvPairs)) {
            for (KVPair<String, Job> kvPair : kvPairs) {
                System.out.println(JSONUtils.toJSONString(kvPair));
            }
        }
    }

//    @Test
//    public void del() throws FailStoreException {
//        failStore.delete(key);
//    }

}