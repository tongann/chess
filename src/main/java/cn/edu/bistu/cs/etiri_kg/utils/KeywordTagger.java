package cn.edu.bistu.cs.etiri_kg.utils;

import cn.edu.bistu.cs.etiri_kg.config.KwDic;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 负责实现关键词比对和匹配
 */
@Component
public class KeywordTagger {

    private final AhoCorasickDoubleArrayTrie<String> tagger;

    public KeywordTagger(@Autowired KwDic kwDic){
        this.tagger = new AhoCorasickDoubleArrayTrie<>();
        this.tagger.build(kwDic.getKeywords());
    }

    public List<Map<String, String>> getKeywords(String text){
        //TODO 佟安完成
        if(StringUtil.isEmpty(text)){
            //
            return Collections.emptyList();
        }else{
            //
            List<Map<String, String>> results = new ArrayList<>();
            List<AhoCorasickDoubleArrayTrie.Hit<String>> hits =tagger.parseText(text);
            for(AhoCorasickDoubleArrayTrie.Hit<String> hit: hits){
                //TODO add results
                Map<String, String> key_tag = new HashMap<>();
                int keyword_begin = hit.begin;
                int keyword_end = hit.end;
                String keyword_map = text.substring(keyword_begin,keyword_end);
                key_tag.put(keyword_map,hit.value);
                results.add(key_tag);
            }
            return results;
        }
    }
}