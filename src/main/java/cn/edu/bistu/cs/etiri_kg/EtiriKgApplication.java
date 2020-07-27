
package cn.edu.bistu.cs.etiri_kg;


import cn.edu.bistu.cs.etiri_kg.config.SysConfig;
import cn.edu.bistu.cs.etiri_kg.config.KwDic;
import cn.edu.bistu.cs.etiri_kg.processing.PaperProcess;
import cn.edu.bistu.cs.etiri_kg.utils.KeywordTagger;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

@SpringBootApplication
public class EtiriKgApplication implements CommandLineRunner{

    private final SysConfig sysConfig;



    private final PaperProcess paperProcess;

    private final ConfigurableApplicationContext context;

    private final KeywordTagger tagger;

    private static final Logger log = LoggerFactory.getLogger(EtiriKgApplication.class);

    public EtiriKgApplication(@Autowired SysConfig sysConfig,
                              @Autowired PaperProcess paperProcess,
                              @Autowired ConfigurableApplicationContext context,
                              @Autowired KeywordTagger tagger) {
        this.tagger=tagger;
        this.sysConfig = sysConfig;
        this.paperProcess = paperProcess;
        this.context = context;

    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EtiriKgApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        switch(sysConfig.getTask()){
            case PAPER_PROCESS:
                log.info("开始文献数据预处理");
                paperProcess.process();
                //TODO 数据持久化
                context.close();
                break;
            case DEBUG:
                log.info("运行调试任务");
                debug();
                context.close();
                break;
        }
    }

    private void debug(){
        String text = "双极晶体管工艺光刻工艺是半导体制造中光刻工艺最为重要的工艺步骤之一。主要作用是将掩膜板上的图形复制到硅片上，为下一步进行刻蚀或者离子注入工序做好准备";
        System.out.println(tagger.getKeywords(text));

    }
}
